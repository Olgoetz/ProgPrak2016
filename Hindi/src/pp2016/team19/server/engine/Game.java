package pp2016.team19.server.engine;

import pp2016.team19.shared.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JFrame;

import pp2016.team19.server.map.*;

/**
 * Runs game, holds game data, loads levels
 * 
 * @author Tobias Schrader
 *
 */
public class Game extends TimerTask implements Serializable {

	private static final long serialVersionUID = 358470863151883429L;
	LinkedBlockingQueue<Message> messagesFromServer = new LinkedBlockingQueue<Message>();
	Tile[][] gameMap;
	private int gameSize;
	transient LinkedList<Monster> Monsters = new LinkedList<Monster>();
	LinkedList<Monster> WaitingMonsters = new LinkedList<Monster>();
	int levelNumber = 1;
	transient ServerEngine engine;
	boolean tester = true; // Testing
	Player player;
	boolean playerAttacked = false;
	Labyrinth TestLabyrinth = new Labyrinth();
	Message updateMonster;
	Message updatePlayer;
	long lastSent;
	int cooldown = 500;
	boolean nextSend;
	boolean monsterMoving = false;
	boolean gameEnded = false;
	long startTime;
	int scoreTime = 0;
	LinkedList<Node> playerPath = new LinkedList<Node>();
	Message autoMoveAnswer;

	public Game(ServerEngine engine, Player player, int gameSize) {
		this.player = player;
		this.gameSize = gameSize;
		this.engine = engine;
		newLevel(levelNumber);
		player.setGame(this);
	}

	/**
	 * Sets Clock for Game Engine, processes messages
	 */
	public void run() {
		if (tester == true) {
			System.out.println("Game executed");
			startTime = System.currentTimeMillis();
			tester = false;
			System.out.println(player.toString());
			Message level = (MessLevelAnswer) new MessLevelAnswer(gameMap, Monsters, 2, 1);
			Message sendPlayer = (MessPlayerAnswer) new MessPlayerAnswer(player, 2, 5, player.getXPos(),
					player.getYPos());
			try {
				engine.messagesToClient.put(level);
				engine.messagesToClient.put(sendPlayer);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while (!gameEnded) {
			Message message = this.messagesFromServer.poll();
			if (message != null) {
				//System.out.println("Message received in game");
				//System.out.println(message.toString());
				this.distributor(message);
			}
			nextSend = ((System.currentTimeMillis() - lastSent) >= cooldown);
			if (nextSend) {

				for (Monster monster : Monsters) {
					monsterMoving = (monsterMoving || monster.playerInRange());
					if (monster.attackPlayer(player.hasKey())) {
						monster.setJustAttacked(true);
						playerAttacked = true;
					} else {
						monster.setJustAttacked(false);
						monster.move();
					}
				}
				if (monsterMoving) {
					updateMonster = (MessUpdateMonsterAnswer) new MessUpdateMonsterAnswer(Monsters, 2, 3);
					monsterMoving = false;
					try {
						engine.messagesToClient.put(updateMonster);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (playerAttacked) {
						if (player.getHealth() <= 0 && !player.isCheater()) {
							endGame(false);
						} else {
							updatePlayer = (MessPlayerAnswer) new MessPlayerAnswer(player, 2, 5, player.getXPos(),
									player.getYPos());
							try {
								engine.messagesToClient.put(updatePlayer);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							playerAttacked = false;
						}
					}
					lastSent = System.currentTimeMillis();
				}
			}
			while (!playerPath.isEmpty()) {
				player.changeDir(playerPath);
				autoMoveAnswer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						true);
				try {
					engine.messagesToClient.put(autoMoveAnswer);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Determines action depending on subtype
	 * 
	 * @param message
	 */
	public void distributor(Message message) {
		switch (message.getSubType()) {
		case 0:
			this.playerMove(message);
			//System.out.println("METHOD Distributor: playerMove executed");
			break;
		case 2:
			this.playerAttack(message);
			break;
		case 4:
			this.collectItem(message);
			break;
		case 6:
			this.usePotion(message);
		case 8: // OpenDoorRequest
			this.openDoor();
			break;
		case 10:
			this.aStarMove(message);
		case 37: // Testing
			this.messageTester(message);
			break;
		}

	}

	public void endGame(boolean playerWon) {
		gameEnded = true;
		System.out.println("Game Ended");
		if (playerWon) {
			scoreTime = (int) (System.currentTimeMillis() - startTime / 1000);
			engine.highscores.addPlayerScore(player.getName(),scoreTime);
		}
		Message answer = (MessEndGameAnswer) new MessEndGameAnswer(playerWon, scoreTime, 2, 7);
		Message highScore = (MessHighscoreAnswer) new MessHighscoreAnswer(engine.highscores.getHighScore(),2,9);
		try {
			engine.messagesToClient.put(answer);
			engine.messagesToClient.put(highScore);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void aStarMove(Message pmessage) {
		MessAstarRequest message = (MessAstarRequest) pmessage;
		playerPath = player.moveToPos(message.getMouseX(), message.getMouseY());
		Message answer=null;
		if (playerPath==null) {
			answer = (MessAstarAnswer) new MessAstarAnswer(player,false,1,11);
		} else {
			answer = (MessAstarAnswer) new MessAstarAnswer(player,true,1,11);
		}
		try {
			engine.messagesToClient.put(answer);
		} catch (InterruptedException e) {
		}	
	}

	private void openDoor() {
		if (gameMap[player.getXPos()][player.getYPos()].isExit() && player.hasKey()) {
			if (levelNumber >= 5) {
				endGame(true);
			} else {
				levelNumber++;
				this.newLevel(levelNumber);
				Message answer = (MessOpenDoorAnswer) new MessOpenDoorAnswer(true, 1, 9);
				Message newLevel = (MessLevelAnswer) new MessLevelAnswer(gameMap, Monsters, 2, 1);
				Message playerUpdate = (MessPlayerAnswer) new MessPlayerAnswer(player, 2, 5, player.getXPos(),
						player.getYPos());
				System.out.println("METHOD Game.openDoor: Door opened");
				try {
					engine.messagesToClient.put(answer);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					engine.messagesToClient.put(newLevel);
					engine.messagesToClient.put(playerUpdate);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("METHOD Game.openDoor: Door didn't open");
			Message answer = (MessOpenDoorAnswer) new MessOpenDoorAnswer(false, 1, 9);
			try {
				engine.messagesToClient.put(answer);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void usePotion(Message message) {
		Message answer;
		//System.out.println("METHOD Game.usePotion");
		if (player.getNumberOfPotions() > 0) {
			player.usePotion();
			answer = (MessUsePotionAnswer) new MessUsePotionAnswer(player, true, 1, 7);
		} else {
			System.out.println("METHOD game.usePotion: No Potion");
			answer = (MessUsePotionAnswer) new MessUsePotionAnswer(player, false, 1, 7);
			;
		}
		try {
			engine.messagesToClient.put(answer);
			System.out.println("METHOD Game.usePotion:" + answer.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void collectItem(Message message) {
		Message updateMonsters = null;
		Message answer;
		//System.out.println("METHOD Game.collectItem: executed");
		if (gameMap[player.getXPos()][player.getYPos()].containsPotion()) {
			player.takePotion();
			gameMap[player.getXPos()][player.getYPos()].setContainsPotion(false);
			answer = (MessCollectItemAnswer) new MessCollectItemAnswer(1, 1, 5);
		} else if (gameMap[player.getXPos()][player.getYPos()].containsKey()) {
			Monsters.addAll(WaitingMonsters);
			player.takeKey();
			gameMap[player.getXPos()][player.getYPos()].setContainsKey(false);
			answer = (MessCollectItemAnswer) new MessCollectItemAnswer(0, 1, 5);
			updateMonsters = (MessUpdateMonsterAnswer) new MessUpdateMonsterAnswer(Monsters, 2, 3);
		} else {
			answer = (MessCollectItemAnswer) new MessCollectItemAnswer(-1, 1, 5);
		}
		try {
			engine.messagesToClient.put(answer);
			System.out.println("METHOD Game.collectItem:" + answer.toString());
			if (updateMonsters != null) {
				engine.messagesToClient.put(updateMonsters);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void playerAttack(Message message) {
		System.out.println("METHOD game.playerAttack");
		Message answer;
		Message gameMapUpdate = null;
		Monster monster = player.monsterToAttack();
		if (monster != null) {
			System.out.println("METHOD game.playerAttack: Monster attacked");
			monster.changeHealth(-8);
			if (monster.getHealth() <= 0) {
				Monsters.remove(monster);
				System.out.println("METHOD game.playerAttack: Monster Killed");
				if (Monsters.isEmpty() && !player.hasKey()) {
					gameMap[monster.getXPos()][monster.getYPos()].setContainsKey(true);
				} else {
					if (Math.random()>0.4) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void messageTester(Message message) { // Testing
		System.out.println(message.toString());
		Message answer = (TestMessage) new TestMessage(1, 18, "Answering Test");
		try {
			engine.messagesToClient.put(answer);
			System.out.println("Message sent back");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Loads new level
	 * 
	 * @param levelNumber
	 */
	public void newLevel(int levelNumber) {
		System.out.println("METHOD Game.newLevel");
		gameMap = Labyrinth.generate(gameSize, levelNumber*2);
		// TestLabyrinth.setGameMap(gameMap);

		Monsters.clear();
		if (Monsters.isEmpty()) {
			//System.out.println("METHOD Game.newLevel: Monsterlist cleared");
		}
		createMonsters(gameMap, levelNumber * 2);
		player.setPos(1, gameSize - 2);
		player.removeKey(player.isCheater());

	}

	private LinkedList<Monster> createMonsters(Tile[][] gameMap2, int monsterNumber) {
		WaitingMonsters.clear();
		int k = 0;
		for (int i = 0; i < gameMap2.length; i++) {
			for (int j = 0; j < gameMap2.length; j++) {
				if (gameMap[i][j].containsMonster()) {
					if (k % 2 == 0) {
						Monsters.add(new Monster(i, j, this, 0));
					} else {
						WaitingMonsters.add(new Monster(i, j, this, 1));
					}
					k++;
				}
			}
		}
		return Monsters;
	}

	/**
	 * Executes player movement command
	 * 
	 * @param message
	 */
	private void playerMove(Message pmessage) {
		Message answer;
		MessMoveCharacterRequest message = (MessMoveCharacterRequest) pmessage;
//		player.xPos = message.getX();
//		player.yPos = message.getY();

		switch (message.getDirection()) {
		case 0: // MoveDown
			if (player.getYPos() > 0 && gameMap[player.getXPos()][player.getYPos() + 1].isWalkable()) {
				player.moveDown();
				System.out.println("DOWN:" + player.getXPos() + " " + player.getYPos());
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						true);
				MessMoveCharacterAnswer tester = (MessMoveCharacterAnswer) answer;
				//System.out.println("Move executed");
			} else {
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						false);
				System.out.println("UP Move not allowed");
			}
			break;
		case 1: // MoveUp
			if (player.getYPos() < 16 - 1 && gameMap[player.getXPos()][player.getYPos() - 1].isWalkable()) {
				player.moveUp();
				System.out.println("UP:" + player.getXPos() + " " + player.getYPos());
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						true);
			} else {
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						false);
				System.out.println("DOWN Move not allowed");
			}
			break;
		case 2: // MoveLeft
			if (player.getXPos() > 0 && gameMap[player.getXPos() - 1][player.getYPos()].isWalkable()) {
				player.moveLeft();
				System.out.println("LEFT:" + player.getXPos() + " " + player.getYPos());
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						true);
			} else {
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						false);
				System.out.println("LEFT Move not allowed");
			}
			break;
		case 3: // MoveRight
			if (player.getXPos() < 16 - 1 && gameMap[player.getXPos() + 1][player.getYPos()].isWalkable()) {
				player.moveRight();
				System.out.println("RIGHT:" + player.getXPos() + " " + player.getYPos());
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						true);
			} else {
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						false);
				System.out.println("RIGHT Move not allowed");
			}
			break;
		default:
			answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
					false);
			break;
		}
		try {
			engine.messagesToClient.put(answer);
			System.out.println("METHOD Game.movePlayer: PlayerPos = " + player.getXPos() + ", " + player.getYPos());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Getters

	public Player getPlayer() {
		return player;
	}

	public Tile[][] getGameMap() {
		return gameMap;
	}

	public int getGameSize() {
		return gameSize;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public LinkedList<Monster> getMonsters() {

		return Monsters;
	}
	public void stopGame() {
		gameEnded = false;
	}
	public void restartGame() {
		gameEnded = true;
	}
}
