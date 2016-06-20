package pp2016.team19.server.engine;

import pp2016.team19.shared.*;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import pp2016.team19.server.map.*;

public class Game implements Runnable {
	LinkedBlockingQueue<Message> messagesFromServer;
	LinkedBlockingQueue<Message> messagesToEngine;
	Tile[][] gameMap;
	Player player;
	private int gameSize;
	Vector<Monster> Monsters = new Vector();
	int levelNumber=1;
	private Timer tick;
	//Konstruktor
	public Game(Player player, int gameSize, LinkedBlockingQueue<Message> messagesFromServer) {
		this.player = player;
		this.gameSize = gameSize;
		this.messagesFromServer = messagesFromServer;
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
	public void newLevel(int levelNumber) {
		gameMap = Labyrinth.generateLabyrinth(gameSize);
		//Monsters = Labyrinth.placeMonsters(gameMap, levelNumber); Needs Input
	}
//Getters

	public Player getPlayer() {
		return player;
	}
}