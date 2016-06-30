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
public class Game extends TimerTask {

	private static final long serialVersionUID = 358470863151883429L;
	LinkedBlockingQueue<Message> messagesFromServer;
	Tile[][] gameMap;
	private int gameSize;
	LinkedList<Monster> Monsters = new LinkedList<Monster>();
	int levelNumber = 1;
	ServerEngine engine;
	boolean tester = true; // Testing
	Player player;
	int potions;

	public Game(ServerEngine engine, Player player, int gameSize, LinkedBlockingQueue<Message> messagesFromServer) {
		this.player = player;
		this.gameSize = gameSize;
		this.messagesFromServer = messagesFromServer;
		this.engine = engine;
		gameMap = Labyrinth.generate(gameSize, gameSize);
		Monsters = createMonsters(gameMap);
		player.setPos(1, gameSize - 2);
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
		// for(Monster monster: Monsters) {
		// boolean attack = monster.attackPlayer(player.hasKey());
		// if (!attack) {
		// monster.move();
		// }
		//
		// }
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
			System.out.println("playerMove executed");
			break;
		case 2:
			this.playerAttack(message);
			break;
		case 4:
			this.collectItem(message);
		case 6:
			this.usePotion(message);
		default:
			break;
		case 8: // OpenDoorRequest
			this.openDoor();
		case 37: // Testing
			this.messageTester(message);
			System.out.println("In game");
			break;
		}

	}

	private void openDoor() {
		if (player.hasKey()) {
			levelNumber++;
			this.newLevel(levelNumber);
			Message answer = (MessOpenDoorAnswer) new MessOpenDoorAnswer(true, 1, 9);
			Message newLevel = (MessLevelAnswer) new MessLevelAnswer(gameMap, Monsters, 2, 1);
			try {
				engine.messagesToClient.put(answer);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				engine.messagesToClient.put(newLevel);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Message answer = (MessOpenDoorAnswer) new MessOpenDoorAnswer(false, 1, 9);
			try {
				engine.messagesToClient.put(answer);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void usePotion(Message message) { // How does it work?
		potions = player.getNumberOfPotions();
		if (potions > 0) {
			player.usePotion();
			// Answer
		}
	}

	private void collectItem(Message message) {
		Message answer;
		if (gameMap[player.getXPos()][player.getYPos()].containsPotion()) {
			player.takePotion();
			answer = (MessCollectItemAnswer) new MessCollectItemAnswer(1, 1, 5);
		} else if (gameMap[player.getXPos()][player.getYPos()].containsKey()) {
			player.takeKey();
			answer = (MessCollectItemAnswer) new MessCollectItemAnswer(0, 1, 5);
		} else {
			answer = (MessCollectItemAnswer) new MessCollectItemAnswer(-1, 1, 5);
		}
		try {
			engine.messagesToClient.put(answer);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void playerAttack(Message message) {
		if (player.monsterToAttack() != null) {

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
		gameMap = Labyrinth.generate(gameSize, gameSize);
		Monsters = createMonsters(gameMap);

	}

	private LinkedList<Monster> createMonsters(Tile[][] gameMap2) {
		LinkedList<Monster> Monsters = new LinkedList<Monster>();
		for (int i = 0; i < gameMap2.length; i++) {
			for (int j = 0; j < gameMap2.length; j++) {
				if(gameMap[i][j].containsMonster()){
					Monsters.add(new Monster(i,j,this,1));
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
				Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),
						player.getYPos(), 1, 1, true);
				MessMoveCharacterAnswer tester = (MessMoveCharacterAnswer) answer;
				System.out.println("Player position test:" + tester.getX());
				System.out.println("Move executed");
				try {
					engine.messagesToClient.put(answer);
					System.out.println("Answer sent");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),
						player.getYPos(), 1, 1, false);
				System.out.println("UP Move not allowed");
				try {
					engine.messagesToClient.put(answer);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case 1: // MoveDown
			if (player.getYPos() < 16 - 1 && gameMap[player.getXPos()][player.getYPos() - 1].isWalkable()) {
				player.moveUp();
				System.out.println("UP:" + player.getXPos() + " " + player.getYPos());
				Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),
						player.getYPos(), 1, 1, true);
				try {
					engine.messagesToClient.put(answer);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),
						player.getYPos(), 1, 1, false);
				System.out.println("DOWN Move not allowed");
				try {
					engine.messagesToClient.put(answer);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case 2: // MoveLeft
			if (player.getXPos() > 0 && gameMap[player.getXPos() - 1][player.getYPos()].isWalkable()) {
				player.moveLeft();
				System.out.println("LEFT:" + player.getXPos() + " " + player.getYPos());
				Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),
						player.getYPos(), 1, 1, true);
				try {
					engine.messagesToClient.put(answer);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),
						player.getYPos(), 1, 1, false);
				System.out.println("LEFT Move not allowed");
				try {
					engine.messagesToClient.put(answer);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case 3: // MoveRight
			if (player.getXPos() < 16 - 1 && gameMap[player.getXPos() + 1][player.getYPos()].isWalkable()) {
				player.moveRight();
				System.out.println("RIGHT:" + player.getXPos() + " " + player.getYPos());
				Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),
						player.getYPos(), 1, 1, true);
				try {
					engine.messagesToClient.put(answer);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),
						player.getYPos(), 1, 1, false);
				System.out.println("RIGHT Move not allowed");
				try {
					engine.messagesToClient.put(answer);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

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
