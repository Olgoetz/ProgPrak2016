package pp2016.team19.server.engine;

import pp2016.team19.shared.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import pp2016.team19.server.map.*;
/**
 * Runs game, holds game data, loads levels
 * @author Tobias Schrader
 *
 */
public class Game extends TimerTask {
	LinkedBlockingQueue<Message> messagesFromServer;
	Tile[][] gameMap;
	private int gameSize;
	Vector<Monster> Monsters = new Vector();
	int levelNumber=1;
	private Timer tick = new Timer();
	ServerEngine engine;
	boolean tester = true; //Testing
	Player player;
	int potions;
	public Game(ServerEngine engine, Player player, int gameSize, LinkedBlockingQueue<Message> messagesFromServer) {
		this.player = player;
		this.gameSize = gameSize;
		this.messagesFromServer = messagesFromServer;
		this.engine = engine;
		//gameMap = Labyrinth.generate(gameSize);
	}
	/**
	 * Sets Clock for Game Engine, processes messages
	 */
	public void run() {
		if (tester==true) {
			System.out.println("Game executed");
			tester=false;
			}
		Message message = this.messagesFromServer.poll();
		if (message != null) {
			System.out.println("Message received in game");
			this.distributor(message);
		}
		for(Monster monster: Monsters) {
			//Move Monster
		}
	}
	/**
	 * Determines action depending on subtype
	 * @param message
	 */
	public void distributor(Message message) {
		switch(message.getSubType()) {
		case 0:
			this.playerMove(message);
			System.out.println("Player moved");
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
		case 8: //OpenDoorRequest
			this.openDoor();
		case 37: //Testing
			this.messageTester(message);
			break;
		}

		}

	private void openDoor() {
		if (player.hasKey()) {
			levelNumber++;
			this.newLevel(levelNumber);
			//Message answer = (MessLevelAnswer) new MessLevelAnswer(gameMap,2,1); //Datatype
		}
		
	}
	private void usePotion(Message message) { //How does it work?
		potions = player.getNumberOfPotions();
		if (potions>0) {
			player.usePotion();
			//doSomething, doesn't seem to work yet
		}
	}
	private void collectItem(Message message) {
		if (gameMap[player.getXPos()][player.getYPos()].containsPotion()) {
			//player.takePotion(); //How do I access the object?
			//Message answer = (MessTakePotionAnswer) new MessTakePotionAnswer(1,5); Doesn't exist yet
			//Don't forget to send answer
		} else if (gameMap[player.getXPos()][player.getYPos()].containsKey()) {
			player.takeKey();
			//Message answer = (MessTakePotionAnswer) new MessTakePotionAnswer(1,5); Doesn't exist yet
			//Don't forget to send answer
		}
	}
	private void playerAttack(Message message) {
		//player.monsterToAttack(); What does it return?
		//What about attacking multiple monsters at once?
	}
	private void messageTester(Message message) { //Testing
		System.out.println(message.toString());
		Message answer = (TestMessage) new TestMessage(1,18,"Answering Test");
		try {
			engine.messagesToClient.put(answer);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Loads new level
	 * @param levelNumber
	 */
	public void newLevel(int levelNumber) {
		gameMap = Labyrinth.generate(gameSize,gameSize);
		//Monsters = Labyrinth.placeMonsters(gameMap, levelNumber); Needs Input
	}
	/**
	 * Executes player movement command
	 * @param message
	 */
	private void playerMove(Message pmessage) {
		MessMoveCharacterRequest message = (MessMoveCharacterRequest) pmessage;
		switch(message.getDirection()) {
		case 0: //MoveUp
		if (gameMap[player.getXPos()][player.getYPos()+1].isWalkable()) {
				player.setPos(player.getXPos(),player.getYPos()+1);
				Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),player.getYPos(),1,1,true);
				System.out.println("Move executed");
				try {
					engine.messagesToClient.put(answer);
					System.out.println("Answer sent");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			} else {
				Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),player.getYPos(),1,1,false);
				try {
					engine.messagesToClient.put(answer);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}  
			case 1: //MoveDown
				if (gameMap[player.getXPos()][player.getYPos()-1].isWalkable()) {
					player.setPos(player.getXPos(),player.getYPos()-1);
					Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),player.getYPos(),1,1,true);
					try {
						engine.messagesToClient.put(answer);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),player.getYPos(),1,1,false);
					try {
						engine.messagesToClient.put(answer);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				case 2: //MoveLeft
					if (gameMap[player.getXPos()-1][player.getYPos()].isWalkable()) {
						player.setPos(player.getXPos()-1,player.getYPos());
						Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),player.getYPos(),1,1,true);
						try {
							engine.messagesToClient.put(answer);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),player.getYPos(),1,1,false);
						try {
							engine.messagesToClient.put(answer);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					case 3: //MoveRight
						if (gameMap[player.getXPos()+1][player.getYPos()].isWalkable()) {
							player.setPos(player.getXPos()+1,player.getYPos());
							Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),player.getYPos(),1,1,true);
							try {
								engine.messagesToClient.put(answer);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							Message answer = (MessMoveCharacterAnswer) new MessMoveCharacterAnswer(player.getXPos(),player.getYPos(),1,1,false);
							try {
								engine.messagesToClient.put(answer);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			}
				
		}
	}

	//Getters

	public Player getPlayer() {
		return player;
	}
}