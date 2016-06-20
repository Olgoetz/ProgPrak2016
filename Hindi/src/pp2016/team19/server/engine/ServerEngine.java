package pp2016.team19.server.engine;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import pp2016.team19.shared.*;

public class ServerEngine implements Runnable {
	private LinkedBlockingQueue<Message> messagesFromClient;
	private LinkedBlockingQueue<Message> messagesToClient;
	private LinkedBlockingQueue<Message> messagesToGame;
	
	private ExecutorService threadPool;
	//private LinkedList<Player> players;
	private String userName;
	private String password;
	private Player player;
	private Game game1;
	public ServerEngine(ExecutorService serverThreadPool,
			LinkedBlockingQueue<Message> messagesFromClient,
			LinkedBlockingQueue<Message> messagesToClient) {
		this.threadPool = serverThreadPool;
		this.messagesFromClient = messagesFromClient;
		this.messagesToClient = messagesToClient;
	}

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
	}
	
	public void distributor(Message message) {
		switch(message.getType()) {
		case 0:
			switch(message.getSubType()) {
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
			messagesToGame = new LinkedBlockingQueue<Message>();
			game1=new Game(player, 30, messagesToGame);
			game1.run();
			this.messagesToClient.addElement(MessSignInAnswer(true,type,subtype));
		} else {
			this.messagesToClient.addElement(MessSignInAnswer(false,type,subtype));
		}
		
	}
	private void signOffRequest(Message message) {
		// TODO Auto-generated method stub
		
	}

	private void signOutRequest(Message message) {
		// TODO Auto-generated method stub
		
	}

	private void signUpRequest(Message message) {
		// TODO Auto-generated method stub
		
	}

	private void ConnectionRequest(Message message) {
		// TODO Auto-generated method stub
		
	}
}
	