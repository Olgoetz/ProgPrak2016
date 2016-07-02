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
	LinkedBlockingQueue<Message> messagesFromServer;
	Tile[][] gameMap;
	private int gameSize;
	transient LinkedList<Monster> Monsters = new LinkedList<Monster>();
	int levelNumber = 1;
	transient ServerEngine engine;
	boolean tester = true; // Testing
	Player player;
	boolean playerAttacked = false;
	Labyrinth TestLabyrinth = new Labyrinth();
	Message updateMonster;

	public Game(ServerEngine engine, Player player, int gameSize, LinkedBlockingQueue<Message> messagesFromServer) {
		this.player = player;
		this.gameSize = gameSize;
		this.messagesFromServer = messagesFromServer;
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
		Message message = this.messagesFromServer.poll();
		if (message != null) {
			System.out.println("Message received in game");
			System.out.println(message.toString());
			this.distributor(message);
		}
		for (Monster monster : Monsters) {
			if (monster.attackPlayer(player.hasKey())) {
			} else {
				monster.move();
			}
			updateMonster = (MessUpdateMonsterAnswer) new MessUpdateMonsterAnswer(Monsters, 2, 3);
			try {
				engine.messagesToClient.put(updateMonster);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			System.out.println("METHOD Distributor: playerMove executed");
			break;
		case 2:
			this.playerAttack(message);
			break;
		case 4:
			this.collectItem(message);
			break;
		case 6:
			this.usePotion(message);
		default:
			break;
		case 8: // OpenDoorRequest
			this.openDoor();
		case 37: // Testing
			this.messageTester(message);
			break;
		}

	}

	private void openDoor() {
		if (gameMap[player.getXPos()][player.getYPos()].isExit() && player.hasKey()) {
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
		System.out.println("METHOD Game.usePotion");
		if (player.getNumberOfPotions() > 0) {
			player.usePotion();
			answer = (MessUsePotionAnswer) new MessUsePotionAnswer(player, true, 1, 7);
		} else {
			System.out.println("METHOD game.usePotion: No Potion");
			answer = (MessUsePotionAnswer) new MessUsePotionAnswer(player, false, 1, 7);;
		}
		try {
			engine.messagesToClient.put(answer);
			System.out.println("METHOD Game.collectItem:" + answer.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void collectItem(Message message) {
		Message answer;
		System.out.println("METHOD Game.collectItem: executed");
		if (gameMap[player.getXPos()][player.getYPos()].containsPotion()) {
			player.takePotion();
			gameMap[player.getXPos()][player.getYPos()].setContainsPotion(false);
			answer = (MessCollectItemAnswer) new MessCollectItemAnswer(1, 1, 5);
		} else if (gameMap[player.getXPos()][player.getYPos()].containsKey()) {
			player.takeKey();
			gameMap[player.getXPos()][player.getYPos()].setContainsKey(false);
			answer = (MessCollectItemAnswer) new MessCollectItemAnswer(0, 1, 5);
		} else {
			answer = (MessCollectItemAnswer) new MessCollectItemAnswer(-1, 1, 5);
		}
		try {
			engine.messagesToClient.put(answer);
			System.out.println("METHOD Game.collectItem:" + answer.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void playerAttack(Message message) {
		if (player.monsterToAttack() != null) {
			player.monsterToAttack().changeHealth(-8);
		}
		//answer
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
		gameMap = Labyrinth.generate(gameSize, levelNumber);
		TestLabyrinth.setGameMap(gameMap);
		
		Monsters.clear();
		createMonsters(gameMap);
		player.setPos(1, gameSize - 2);
		player.removeKey();

	}

	private LinkedList<Monster> createMonsters(Tile[][] gameMap2) {
		for (int i = 0; i < gameMap2.length; i++) {
			for (int j = 0; j < gameMap2.length; j++) {
				if (gameMap[i][j].containsMonster()) {
					Monsters.add(new Monster(i, j, this, 1));
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
		player.xPos = message.getX();
		player.yPos = message.getY();
		System.out.println("METHOD ServerEngine.playerMove: " + player.toString());
		System.out.println("METHOD ServerEngine.playerMove: " + message.getDirection());

		switch (message.getDirection()) {
		case 0: // MoveUp
			if (player.getYPos() > 0 && gameMap[player.getXPos()][player.getYPos() + 1].isWalkable()) {
				player.moveDown();
				System.out.println("DOWN:" + player.getXPos() + " " + player.getYPos());
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						true);
				MessMoveCharacterAnswer tester = (MessMoveCharacterAnswer) answer;
				System.out.println("Player position test:" + tester.getX());
				System.out.println("Move executed");
			} else {
				answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1,
						false);
				System.out.println("UP Move not allowed");
			}
			break;
		case 1: // MoveDown
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
			answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(), player.getYPos(), 1, 1, false);
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
}
