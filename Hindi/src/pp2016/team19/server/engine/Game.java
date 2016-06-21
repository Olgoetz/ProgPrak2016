package pp2016.team19.server.engine;

import pp2016.team19.shared.*;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import pp2016.team19.server.map.*;
/**
 * Runs game, holds game data, loads levels
 * @author Tobias Schrader
 *
 */
public class Game implements Runnable {
	LinkedBlockingQueue<Message> messagesFromServer;
	LinkedBlockingQueue<Message> messagesToEngine = new LinkedBlockingQueue<Message>();
	Tile[][] gameMap;
	private int gameSize;
	Vector<Monster> Monsters = new Vector();
	int levelNumber=1;
	private Timer tick;
	ServerEngine engine;
	boolean stop = true;
	Player player;
	public Game(ServerEngine engine, Player player, int gameSize, LinkedBlockingQueue<Message> messagesFromServer) {
		this.player = player;
		this.gameSize = gameSize;
		this.messagesFromServer = messagesFromServer;
		this.engine = engine;
	}
	/**
	 * Sets Clock for Game Engine, processes messages
	 */
	public void run() {
		System.out.println("game executed");
		this.tick.scheduleAtFixedRate(new GameEngine(engine, this, player, messagesToEngine), 0, 50);
		System.out.println("before while");
		while (stop) {
			System.out.println("while");
			Message message = this.messagesFromServer.poll();
			System.out.println("Message received in game");
			if (message != null) {
				this.distributor(message);
			}
		}
	}
	/**
	 * Determines action depending on subtype
	 * @param message
	 */
	public void distributor(Message message) {
		switch(message.getSubType()) {
		case 14: //OpenDoorRequest
			if (player.hasKey()) {
			levelNumber++;
			newLevel(levelNumber);
			}
			break;
		default: //Player Command
			try {
				this.messagesToEngine.put(message);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
//Getters

	public Player getPlayer() {
		return player;
	}
}