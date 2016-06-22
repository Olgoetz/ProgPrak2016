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
	public Game(ServerEngine engine, Player player, int gameSize, LinkedBlockingQueue<Message> messagesFromServer) {
		this.player = player;
		this.gameSize = gameSize;
		this.messagesFromServer = messagesFromServer;
		this.engine = engine;
		gameMap = Labyrinth.generateLabyrinth(gameSize);
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
		default:
			break;
		case 14: //OpenDoorRequest
			if (player.hasKey()) {
			levelNumber++;
			newLevel(levelNumber);
			}
		case 37: //Testing
			this.messageTester(message);
			break;
		}
			
			
		
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
		gameMap = Labyrinth.generateLabyrinth(gameSize);
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
		if (gameMap[player.getXPos()][player.getYPos()+1].isFloor()) {
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
				if (gameMap[player.getXPos()][player.getYPos()-1].isFloor()) {
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
					if (gameMap[player.getXPos()-1][player.getYPos()].isFloor()) {
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
						if (gameMap[player.getXPos()+1][player.getYPos()].isFloor()) {
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