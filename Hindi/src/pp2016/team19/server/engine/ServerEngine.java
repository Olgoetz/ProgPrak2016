package pp2016.team19.server.engine;
import java.util.LinkedList;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import pp2016.team19.server.comm.NetworkHandlerS;
import pp2016.team19.shared.Labyrinth;
import pp2016.team19.shared.*;
/**
 * Server Engine, distributes messages, administrates players, and starts games
 * @author Tobias Schrader
 *
 */
public class ServerEngine implements Runnable {
	LinkedBlockingQueue<Message> messagesToClient;
	LinkedBlockingQueue<Message> messagesToGames = new LinkedBlockingQueue<Message>();
	
	private ExecutorService threadPool;
	//private LinkedList<Player> players;
	private String userName = "user";
	private String password = "123";
	private Vector<Player> players;
	private Vector<Game> games = new Vector<Game>();
	private Game game1; //Test
	private Player player = new Player(); //Test
	private Timer tick = new Timer();
	NetworkHandlerS network = new NetworkHandlerS();
	private boolean playerIsNew;
	/**
	 * Constructor sets Message Queues for communication
	 * @param serverThreadPool
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
		System.out.println("runs");
		while (true) {
				Message message = network.getMessageFromClient();
				if (message != null) {
					if (message.getType()!=100) {
					System.out.println("Message received");
					System.out.println(message.toString());
					this.distributor(message);
					}
				}
				if (!this.messagesToClient.isEmpty()) {
					System.out.println(this.messagesToClient.peek().toString());
					//System.out.println("Answer came back");
					network.sendMessageToClient(this.messagesToClient.poll());
				}
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
			break;
		case 1:
				this.sendToGame(message);
				System.out.println("Messages forwarded");
				break;	
		case 100:
			this.confirmConnection();
			break;
		default:
			break;
			}
		}

	
	private void confirmConnection() {
		Message answer = (MessPing) new MessPing(100,0);
		try {
			this.messagesToClient.put(answer);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/** 
	 * Forwards player actions to game
	 * @param message
	 */
	private void sendToGame(Message message) {
		try {
			games.get(0).messagesFromServer.put(message);
			System.out.println(game1.messagesFromServer.size());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Checks Log-In information, starts new game if correct
	 * @param message
	 */
	
	private void signInRequest(Message pmessage) {
		MessSignInAndUpRequest message = (MessSignInAndUpRequest) pmessage;
		System.out.println("Method engaging");
		if(userName.equals(message.getUsername()) && password.equals(message.getPassword())) {
			System.out.println("did work");
			this.messagesToGames = new LinkedBlockingQueue<Message>();
			this.games.addElement(new Game(this, player, 30, this.messagesToGames));
			this.tick.scheduleAtFixedRate(this.games.lastElement(), 0, 50);
			Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(true,0,3);
			try {
				this.messagesToClient.put(answer);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("did not work");
			Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(false,0,3);
			try {
				this.messagesToClient.put(answer);
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

	private void signUpRequest(Message pmessage) {
		MessSignInAndUpRequest message = (MessSignInAndUpRequest) pmessage;
		playerIsNew = true;
		for(Player player: players) {
			if(message.getUsername().equals(player.getName())) {
				//Answer: Player exists
				playerIsNew = false;
			}
		}
		if(playerIsNew) {
			players.addElement(new Player());
			//Answer true
		}
	}

	private void ConnectionRequest(Message message) {
		System.out.println("Connected");
		Message answer =  new TestMessage(0, 5,"");
		try {
			this.messagesToClient.put(answer);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
	