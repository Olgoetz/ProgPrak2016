package pp2016.team19.server.engine;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import pp2016.team19.server.comm.NetworkHandlerS;
import pp2016.team19.shared.*;
/**
 * Server Engine, distributes messages, administrates players, and starts games
 * @author Tobias Schrader
 *
 */
public class ServerEngine implements Runnable {
	LinkedBlockingQueue<Message> messagesToClient;
	Vector<LinkedBlockingQueue<Message>> messagesToGames;
	
	private ExecutorService threadPool;
	//private LinkedList<Player> players;
	private String userName;
	private String password;
	private Vector<Player> players;
	private Vector<Game> games;
	NetworkHandlerS network = new NetworkHandlerS();
	/**
	 * Constructor sets Message Queues for communication
	 * @param serverThreadPool
	 * @param messagesFromClient
	 * @param messagesToClient
	 */
	public ServerEngine(ExecutorService serverThreadPool,
			LinkedBlockingQueue<Message> messagesToClient) {
		this.threadPool = serverThreadPool;
		this.messagesToClient = messagesToClient;
	}
/**
 * Keeps processing Messages
 */
	public void run() {
		while (true) {
			
				Message message = network.getMessageFromClient();
				if (message != null) {
					this.distributor(message);
				}
				network.sendMessageToClient(this.messagesToClient.poll());
			}
		}
	/**
	 * Determines action depending on type and subtype
	 * @param message
	 */
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

	
	/** 
	 * Forwards player actions to game
	 * @param message
	 */
	private void sendToGame(Message message, int playerID) {
		try {
			.messagesFromServer.put(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Checks Log-In information, starts new game if correct
	 * @param message
	 */
	private void signInRequest(Message message) {
		if(message.userName==this.userName && message.password==this.password) {
			messagesToGame = new LinkedBlockingQueue<Message>();
			this.games.addElement(new Game(this, message.player, 30, this.messagesToGames.addElement(new LinkedBlockingQueue<Message>)));
			this.games.lastElement().run();
			this.messagesToClient.addElement(MessSignInAnswer(true,type,subtype));
		} else {
			this.messagesToClient.addElement(MessSignInAnswer(false,type,subtype));
		}
		
	}
	private void signOffRequest(Message message) {
		// TODO Auto-generated method stub
		
	}

	private void signOutRequest(Message message) {
		
	}

	private void signUpRequest(Message message) {
		// TODO Auto-generated method stub
		
	}

	private void ConnectionRequest(Message message) {
		// TODO Auto-generated method stub
		
	}
	private 
}
	