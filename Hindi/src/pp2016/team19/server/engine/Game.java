package pp2016.team19.server.engine;

import pp2016.team19.shared.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <h1>This represents a game</h1>
 * 
 * Runs game, holds game data, loads levels, executes character actions
 * 
 * @author Tobias Schrader
 *
 */
public class Game extends TimerTask implements Serializable {

	private static final long serialVersionUID = 358470863151883429L;
	LinkedBlockingQueue<Message> messagesFromServer = new LinkedBlockingQueue<Message>();
	//save game information
	Tile[][] gameMap;
	private int gameSize;
	transient LinkedList<Monster> Monsters = new LinkedList<Monster>();
	LinkedList<Monster> WaitingMonsters = new LinkedList<Monster>();
	int levelNumber = 1;
	//the corresponding server engine
	transient ServerEngine engine;
	//a few useful attributes
	boolean starter;
	Player player;
	boolean playerAttacked = false;
	long lastSent;
	int cooldown = 500;
	boolean nextSend;
	boolean monsterMoving = false;
	boolean gameEnded = false;
	long startTime;
	int scoreTime = -1;
	LinkedList<Node> playerPath = new LinkedList<Node>();

	/**
	 * Constructor initializes player and engine, builds first level
	 * @param engine
	 * @param player
	 * @param gameSize
	 * @author Tobias Schrader, 5637252
	 */
	public Game(ServerEngine engine, Player player, int gameSize) {
		this.player = player;
		this.gameSize = gameSize;
		this.engine = engine;
		newLevel(levelNumber);
		player.setGame(this);
		starter=true;
	}

	/**
	 * Starts a thread to run the game. Processes messages, keeps monsters moving and player auto-moving.
	 * @author Tobias Schrader, 5637252
	 */
	public void run() {
		if (starter == true) { //sets starting time, sends first level
			System.out.println("Game executed");
			startTime = System.currentTimeMillis();
			starter = false;
			Message level = (MessLevelAnswer) new MessLevelAnswer(gameMap, Monsters, 2, 1);
			Message sendPlayer = (MessPlayerAnswer) new MessPlayerAnswer(player, 2, 5, player.getXPos(),
					player.getYPos());
			try {
				engine.messagesToClient.put(level);
				engine.messagesToClient.put(sendPlayer);
			} catch (InterruptedException e) {
			}
		}
		while (!gameEnded) { //repeated until game is stopped
			Message updateMonster;
			Message updatePlayer;
			//process messages
			Message message = this.messagesFromServer.poll();
			if (message != null) {
				this.distributor(message);
			}
			nextSend = ((System.currentTimeMillis() - lastSent) >= cooldown);
			if (nextSend) { //waits to move monsters until they cooled down to reduce traffic

				for (Monster monster : Monsters) { // has monsters move or attack the player
					monsterMoving = (monsterMoving || monster.playerInRange(monster.getXPos(),monster.getYPos()));
					if (monster.attackPlayer(player.hasKey())) { //attacks player
						monster.setJustAttacked(true);
						playerAttacked = true;
					} else {
						monster.setJustAttacked(false);
						monster.move(); //moves monster
					}
				}
				if (monsterMoving) { //only sends an update if a monster is actually acting to reduce traffic
					updateMonster = (MessUpdateMonsterAnswer) new MessUpdateMonsterAnswer(Monsters, 2, 3);
					monsterMoving = false;
					try {
						engine.messagesToClient.put(updateMonster);
					} catch (InterruptedException e) {
					}
					if (playerAttacked) { //processes attack to the player
						if (player.getHealth() <= 0) {
							endGame(false);
						} else {
							updatePlayer = (MessPlayerAnswer) new MessPlayerAnswer(player, 2, 5, player.getXPos(),
									player.getYPos());
							try {
								engine.messagesToClient.put(updatePlayer); //player update
							} catch (InterruptedException e) {
							}
							playerAttacked = false;
						}
					}
					lastSent = System.currentTimeMillis();
				}
			}
			//executes movement of the player scheduled by mouse click
			while (!playerPath.isEmpty()) {
				Message autoMoveAnswer;
				boolean pathIsMonsterFree;
				pathIsMonsterFree = player.changeDir(playerPath);
				if(pathIsMonsterFree) { //if monster is in the way, aborts path and attacks monster
				autoMoveAnswer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						true);
				try {
					engine.messagesToClient.put(autoMoveAnswer);
				} catch (InterruptedException e) {
				}
				} else {
					playerPath.clear();
					playerAttack();
				}
			}
		}
	}

	/**
	 * analyzes game request messages forwarded from the engine. Determines action depending on subtype.
	 * 
	 * @param message
	 * @author Tobias Schrader, 5637252
	 */
	public void distributor(Message message) {
		switch (message.getSubType()) {
		case 0:
			this.playerMove(message);
			break;
		case 2:
			this.playerAttack();
			break;
		case 4:
			this.collectItem();
			break;
		case 6:
			this.usePotion();
		case 8:
			this.openDoor();
			break;
		case 10:
			this.aStarMove(message);
			break;
		case 12:
			this.cheat(message);
		}
	}
/**
 * This method executes a one-step move. It checks whether the destination tile is walkable,
 * then moves there or attacks the monster if the tile is occupied by one.
 * Sends answer with update of map and monsters.
 * 
 * @param pmessage, message that contains direction
 * @author Tobias Schrader, 5637252
 */
	private void playerMove(Message pmessage) {
		Message answer = null;
		MessMoveCharacterRequest message = (MessMoveCharacterRequest) pmessage;
		switch (message.getDirection()) { //cases are directions, 0=down, 1=up, 2=left, 3=right
		case 0: // MoveDown
			if (player.getYPos() > 0 && gameMap[player.getXPos()][player.getYPos() + 1].isWalkable()) { //check walkability
				if (!gameMap[player.getXPos()][player.getYPos() + 1].containsMonster()) { //check if monster is there
				player.moveDown(); //move
				System.out.println("DOWN:" + player.getXPos() + " " + player.getYPos());
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						true);
				} else {
					playerAttack(); //attack
				}
			} else {
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						false);
				System.out.println("UP Move not allowed");
			}
			break;
		case 1: // MoveUp
			if (player.getYPos() < 16 - 1 && gameMap[player.getXPos()][player.getYPos() - 1].isWalkable()) {
				if (!gameMap[player.getXPos()][player.getYPos() - 1].containsMonster()) {
				player.moveUp();
				System.out.println("UP:" + player.getXPos() + " " + player.getYPos());
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						true);
				} else {
					playerAttack();
				}
			} else {
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						false);
				System.out.println("DOWN Move not allowed");
			}
			break;
		case 2: // MoveLeft
			if (player.getXPos() > 0 && gameMap[player.getXPos() - 1][player.getYPos()].isWalkable()) {
				if (!gameMap[player.getXPos()-1][player.getYPos()].containsMonster()) {
				player.moveLeft();
				System.out.println("LEFT:" + player.getXPos() + " " + player.getYPos());
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						true);
				} else {
					playerAttack();
				}
			} else {
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						false);
				System.out.println("LEFT Move not allowed");
			}
			break;
		case 3: // MoveRight
			if (player.getXPos() < 16 - 1 && gameMap[player.getXPos() + 1][player.getYPos()].isWalkable()) {
				if (!gameMap[player.getXPos()+1][player.getYPos()].containsMonster()) {
				player.moveRight();
				System.out.println("RIGHT:" + player.getXPos() + " " + player.getYPos());
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						true);
				} else {
					playerAttack();
				}
			} else {
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						false);
				System.out.println("RIGHT Move not allowed");
			}
			break;
		}
		try {
			if (answer!=null)
			engine.messagesToClient.put(answer);
			System.out.println("METHOD Game.movePlayer: PlayerPos = " + player.getXPos() + ", " + player.getYPos());
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * This method searches for monsters in range and attacks one if one is found.
	 * 
	 * @author Tobias Schrader, 5637252
	 */
	private void playerAttack() {
		Message answer;
		Message gameMapUpdate = null;
		Monster monster = player.monsterToAttack(); //search for monster
		if (monster != null) {
			System.out.println("METHOD game.playerAttack: Monster attacked");
			monster.changeHealth(-player.getDamage());
			if (monster.getHealth() <= 0) { //if monster is killed, remove it and drop key or potion
				Monsters.remove(monster);
				System.out.println("METHOD game.playerAttack: Monster Killed");
				gameMap[monster.getXPos()][monster.getYPos()].setContainsMonster(false);
				if (Monsters.isEmpty() && !player.hasKey()) { //last monster drops key
					gameMap[monster.getXPos()][monster.getYPos()].setContainsKey(true);
				} else {
					if (Math.random()>0.5) { //monsters have a chance to drop a potion
						gameMap[monster.getXPos()][monster.getYPos()].setContainsPotion(true);
					}
				}
				gameMapUpdate = (MessLevelAnswer) new MessLevelAnswer(gameMap, Monsters, 2, 1);
			}
			answer = (MessAttackAnswer) new MessAttackAnswer(Monsters, true, 1, 3);
		} else {
			System.out.println("METHOD game.playerAttack: No Monster in range");
			answer = (MessAttackAnswer) new MessAttackAnswer(Monsters, false, 1, 3);
		}
		try {
			engine.messagesToClient.put(answer);
			if (gameMapUpdate != null) {
				engine.messagesToClient.put(gameMapUpdate);
			}
		} catch (InterruptedException e) {
		}
	}
	/**
	 * This method collects any item the player stands on.
	 * 
	 * @author Tobias Schrader, 5637252
	 */
	private void collectItem() {
		Message updateMonsters = null;
		Message answer;
		if (gameMap[player.getXPos()][player.getYPos()].containsPotion()) {
			player.takePotion(); //take potion if one is there
			gameMap[player.getXPos()][player.getYPos()].setContainsPotion(false); //remove potion from map
			answer = (MessCollectItemAnswer) new MessCollectItemAnswer(1, 1, 5);
		} else if (gameMap[player.getXPos()][player.getYPos()].containsKey()) { //take key if one is there
			//spawn new monster from waiting list
			Monsters.addAll(WaitingMonsters);
			for (Monster monster: Monsters) {
				gameMap[monster.getXPos()][monster.getYPos()].setContainsMonster(true);
			}
			player.takeKey();
			gameMap[player.getXPos()][player.getYPos()].setContainsKey(false);
			answer = (MessCollectItemAnswer) new MessCollectItemAnswer(0, 1, 5);
			updateMonsters = (MessUpdateMonsterAnswer) new MessUpdateMonsterAnswer(Monsters, 2, 3);
		} else {
			answer = (MessCollectItemAnswer) new MessCollectItemAnswer(-1, 1, 5);
		}
		try {
			engine.messagesToClient.put(answer);
			if (updateMonsters != null) {
				engine.messagesToClient.put(updateMonsters);
			}
		} catch (InterruptedException e) {

		}
	}
	
	/**
	 * This method lets the player apply a potion. Answer updates player.
	 * 
	 * @author Tobias Schrader, 5637252
	 */
	private void usePotion() {
		Message answer;
		if (player.getNumberOfPotions() > 0) { //check if player has a potion
			player.usePotion(); //heal player
			answer = (MessUsePotionAnswer) new MessUsePotionAnswer(player, true, 1, 7);
		} else {
			System.out.println("METHOD game.usePotion: No Potion");
			answer = (MessUsePotionAnswer) new MessUsePotionAnswer(player, false, 1, 7);
			;
		}
		try {
			engine.messagesToClient.put(answer);
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * This method is used to open the door and load a new level.
	 * 
	 * @author Tobias Schrader, 5637252
	 */
	private void openDoor() {
		if (gameMap[player.getXPos()][player.getYPos()].isExit() && player.hasKey()) { //checks if player stands on exit and has key
			if (levelNumber >= 5) {
				endGame(true); //opening the door in level 5 wins the game
			} else {
				levelNumber++;
				this.newLevel(levelNumber); //load new level
				System.out.println("METHOD Game.openDoor: Door opened");
			}
		} else {
			System.out.println("METHOD Game.openDoor: Door didn't open");
			Message answer = (MessOpenDoorAnswer) new MessOpenDoorAnswer(false, 1, 9);
			try {
				engine.messagesToClient.put(answer);
			} catch (InterruptedException e) {
			}
		}
	}
	
	/**
	 * Loads new level and sends updates
	 * 
	 * @param levelNumber - the number of the current level
	 * @author Tobias Schrader, 5637252
	 */
	public void newLevel(int levelNumber) {
		System.out.println("METHOD Game.newLevel");
		gameMap = Labyrinth.generate(gameSize, levelNumber*2); //generate a new map with number of monsters dependent on level
		Monsters.clear(); //delete any monsters from old level
		createMonsters(gameMap, levelNumber * 2); //create monsters
		//reset player
		player.setPos(1, gameSize - 2);
		player.removeKey();
		Message answer = (MessOpenDoorAnswer) new MessOpenDoorAnswer(true, 1, 9);
		Message newLevel = (MessLevelAnswer) new MessLevelAnswer(gameMap, Monsters, 2, 1);
		Message playerUpdate = (MessPlayerAnswer) new MessPlayerAnswer(player, 2, 5, player.getXPos(),
				player.getYPos());
		try {
			engine.messagesToClient.put(answer);
			engine.messagesToClient.put(newLevel);
			engine.messagesToClient.put(playerUpdate);
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * This method reads the monster from map and creates them as objects
	 * @param gamemap
	 * @param monsterNumber
	 * @return A LinkedList containing the monster objects
	 * 
	 * @author Tobias Schrader, 5637252
	 */
	private LinkedList<Monster> createMonsters(Tile[][] gamemap, int monsterNumber) {
		WaitingMonsters.clear();
		int k = 0;
		for (int i = 0; i < gamemap.length; i++) {
			for (int j = 0; j < gamemap.length; j++) {
				if (gameMap[i][j].containsMonster()) {
					if (k % 2 == 0) { //half the monsters are spawned on begin
						Monsters.add(new Monster(i, j, this, 0));
					} else {
						WaitingMonsters.add(new Monster(i, j, this, 1)); //these are spawned when the player takes the key
						gameMap[i][j].setContainsMonster(false);
					}
					k++;
				}
			}
		}
		return Monsters;
	}
	
	/**
	 * This method executes a move to a certain position
	 * 
	 * @param pmessage, contains destination
	 * @author Tobias Schrader, 5637252
	 */
	private void aStarMove(Message pmessage) {
		MessAstarRequest message = (MessAstarRequest) pmessage;
		playerPath = player.moveToPos(message.getMouseX(), message.getMouseY());
	}
	
	/**
	 * This method allows for the use of cheats. Certain codes trigger certain actions
	 * @param pmessage, contains the cheat code
	 * @author Tobias Schrader, 5637252
	 */
	private void cheat(Message pmessage) {
		MessCheatRequest message = (MessCheatRequest) pmessage;
		if (message.getCheat().equals("godmode")) { //this cheat toggles player invulnerability
			player.characterShield(!player.hasShield());
		} else if (message.getCheat().equals("thekeytolearningjava")) { //this cheat gives the player the key
			player.takeKey();
			Message answer = (MessPlayerAnswer) new MessPlayerAnswer(player, 2, 5, player.getXPos(), player.getYPos());
			try {
				engine.messagesToClient.put(answer);
			} catch (InterruptedException e) {
			}
		} else if (message.getCheat().equals("superdebugger")) { //toggles player damage between high and standard
			if(player.getDamage()==100)
				player.setDamage(8);
				else
			player.setDamage(100);
		} else if (message.getCheat().equals("thislevelisboring")) { //player advances to next level
			levelNumber++;
			newLevel(levelNumber);
		}
	}
	/**
	 * This method stops the game after it ended
	 * @param playerWon - if the player won or lost
	 * @author Tobias Schrader, 5637252
	 */
	public void endGame(boolean playerWon) {
		gameEnded = true; //stops main loop in run() method
		System.out.println("Game Ended");
		if (playerWon) { //time is added to highscore if player won
			scoreTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
			engine.highscores.addPlayerScore(player.getName(), scoreTime); //update highscore
		}
		Message answer = (MessEndGameAnswer) new MessEndGameAnswer(playerWon, scoreTime, 2, 7); //inform client about end
		Message highScore = (MessHighscoreAnswer) new MessHighscoreAnswer(engine.highscores.getHighScore(),2,9); //send highscore update
		try {
			engine.messagesToClient.put(answer);
			engine.messagesToClient.put(highScore);
		} catch (InterruptedException e) {
		}
	}

/**
 * This method simply stops the game by cutting the while-loop in the run() method
 * @author Tobias Schrader, 5637252
 */
	public void stopGame() {
		gameEnded = true;
	}
	
	// Getters to access game information

	/**
	 * @return the player the game is assigned to
	 * @author Tobias Schrader, 5637252
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * @return the current map as it is saved in the game
	 * @author Tobias Schrader, 5637252
	 */
	public Tile[][] getGameMap() {
		return gameMap;
	}
/**
 * @returns the size of the game map
 * @author Tobias Schrader, 5637252
 */
	public int getGameSize() {
		return gameSize;
	}

	/**
	 * @return the number of the current level
	 * @author Tobias Schrader, 5637252
	 */
	public int getLevelNumber() {
		return levelNumber;
	}
/**
 * 
 * @return a list containing the monster objects in the current level
 * @author Tobias Schrader, 5637252
 */
	public LinkedList<Monster> getMonsters() {

		return Monsters;
	}
}
