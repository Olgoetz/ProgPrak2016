package pp2016.team19.server.engine;

import pp2016.team19.shared.*;
import sharedMessages.Message;

import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import pp2016.team19.server.map.*;

public class Game implements Runnable {
	LinkedBlockingQueue<Message> messagesFromServer;
	private Tile[][] gameMap;
	private Player player;
	private int gameSize;
	private Vector Monsters = new Vector();
	int levelNumber=1;
	private GameEngine engine = new GameEngine();
	private Timer tick;
	//Konstruktor
	public Game(Player player, int gameSize) {
		this.player = player;
		this.gameSize = gameSize;
	}
	//Test
	public void run() {
		this.tick.scheduleAtFixedRate(engine, 0, 50);
		while (true) {
			try {
				Message message = this.messagesFromServer.poll(10,
						TimeUnit.MILLISECONDS);
				if (message != null) {
					this.distributor(message);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void distributor(Message message) {
		switch(message.subType) {
		case 14: //OpenDoorRequest
			if (player.HasKey) {
			levelNumber++;
			setLevel(levelNumber);
			}
			break;
		default: //Player Command
			engine.messagesFromGame.put(message);
		}
			
			
		
		}
	public void setLevel(int levelNumber) {
		gameMap = Labyrinth.generateLabyrinth(gameSize);
		Monsters = Labyrinth.placeMonsters(gameMap, levelNumber);
	}
}
