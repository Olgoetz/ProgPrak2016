ppackage pp2016.team19.server.engine;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import pp2016.team19.shared.*;
import sharedMessages.Message;

public class ServerEngine implements Runnable {
	private LinkedBlockingQueue<Message> messagesFromClient;
	private LinkedBlockingQueue<Message> messagesToClient;

	//private LinkedBlockingQueue<Message> messagesFromGame; direkt zum Client
	private ExecutorService threadPool;
	//private LinkedList<Player> players;
	private String userName;
	private String password;
	private Player player;
	Game game1;

	public void run() {
		while (true) {
			try {
				Message message = this.messagesFromClient.poll(10,
						TimeUnit.MILLISECONDS);
				if (message != null) {
					this.distributor(message);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void distributor(Message message) {
		switch(message.type) {
		case 0:
			switch(message.subType) {
			case 0: 
				this.ConnectionRequest(message);
				break;
			case 2: 
				this.signUpRequest(message);
				break;
			case 4: 
				this.signInRequest(message);
				break;
			case 6: 
				this.signOutRequest(message);
				break;
			case 8: 
				this.signOffRequest(message);
				break;
			default:
				break;
			}
		case 1:
				this.sendToGame(message);
				break;	
		default:
			break;
			}
		}
	}
	//Sends Player Commands to Game
	private void sendToGame(Message message) {
		try {
			game1.messagesFromServer.put(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void signInRequest(Message message) {
		if(message.userName==this.userName && message.password==this.password) {
			game1=new Game(player, 30);
			game1.run();
		}
	}
}
	