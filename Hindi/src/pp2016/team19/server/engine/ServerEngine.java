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
	LinkedBlockingQueue<Message> messagesToGames;
	
	private ExecutorService threadPool;
	//private LinkedList<Player> players;
	private String userName = "user";
	private String password = "123";
	private Vector<Player> players;
	private Game game1;
	private Player player;
	
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
					System.out.println("Message received");
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
				this.signInAndUpRequest(message);
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
				System.out.println("Messages forwarded");
				break;	
		default:
			break;
			}
		}

	
	/** 
	 * Forwards player actions to game
	 * @param message
	 */
	private void sendToGame(Message message) {
		try {
			game1.messagesFromServer.put(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Checks Log-In information, starts new game if correct
	 * @param message
	 */
	private void signInAndUpRequest(Message pmessage) {
		MessSignInAndUpRequest message = (MessSignInAndUpRequest) pmessage;
		if(message.getUsername()==this.userName && message.getPassword()==this.password) {
			this.messagesToGames = new LinkedBlockingQueue<Message>();
			this.games.addElement(new Game(this, player, 30, this.messagesToGames));
			this.games.lastElement().run();
			Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(true,0,3);
			try {
				this.messagesToClient.put(new MessSignInAndUpAnswer(true,0,3));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(false,0,3);
			try {
				this.messagesToClient.put(new MessSignInAndUpAnswer(false,0,3));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
}
	