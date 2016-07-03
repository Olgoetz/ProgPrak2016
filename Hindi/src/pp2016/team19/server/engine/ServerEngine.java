package pp2016.team19.server.engine;

import java.util.LinkedList;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import pp2016.team19.server.comm.HandlerServer;
import pp2016.team19.shared.*;

/**
 * Server Engine, distributes messages, administrates players, and starts games
 * 
 * @author Tobias Schrader
 *
 */
public class ServerEngine implements Runnable {
	LinkedBlockingQueue<Message> messagesToClient;
	LinkedBlockingQueue<Message> messagesToGames = new LinkedBlockingQueue<Message>();

	private ExecutorService threadPool;
	// private LinkedList<Player> players;
	private Vector<Player> players = new Vector<Player>();
	private Vector<Game> games = new Vector<Game>();
	transient private Game game1; // Test
	private Timer tick = new Timer();
	HandlerServer network = new HandlerServer();
	private boolean playerIsNew;
	private boolean playerFound;

	/**
	 * Constructor sets Message Queues for communication
	 * 
	 * @param serverThreadPool
	 * @param messagesToClient
	 */
	public ServerEngine(ExecutorService serverThreadPool, LinkedBlockingQueue<Message> messagesToClient) {
		this.threadPool = serverThreadPool;
		this.messagesToClient = messagesToClient;
		players.addElement(new Player("user","123"));
		System.out.println(players.get(0).getName());
	}

	/**
	 * Keeps processing Messages
	 */
	public void run() {
		System.out.println("METHOD ServerEngine.run: Started");
		System.out.println(players.get(0).getName());
		while (true) {
			Message message = network.getMessageFromClient();
			if (message != null) {
				if (message.getType() != 100) {
					System.out.println("Message received");
					System.out.println(message.toString());
					this.distributor(message);
				}
			}
			if (!this.messagesToClient.isEmpty()) {
				System.out
						.println("METHOD ServerEngine.SendMessageToClient:" + this.messagesToClient.peek().toString());
				System.out.println("METHOD ServerEngine.SendMessageToClient: Answer came back");
				network.sendMessageToClient(this.messagesToClient.poll());
			}
		}
	}

	/**
	 * Determines action depending on type and subtype
	 * 
	 * @param message
	 */
	public void distributor(Message message) {
		switch (message.getType()) {
		case 0:
			switch (message.getSubType()) {
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
				this.newGame(message);
			case 8:
				this.signOutRequest(message);
				break;
			case 10:
				this.signOffRequest(message);
				break;
			default:
				break;
			}
			break;
		case 1:
			this.sendToGame(message);
			System.out.println("METHOD ServerEngine.sendToGame: Messages forwarded");
			break;
		case 100:
			this.confirmConnection();
			break;
		default:
			break;
		}
	}

	private void newGame(Message pmessage) {
		//MessStartGameRequest message = (MessStartGameRequest) pmessage;
		//startGame(players.get(message.getPlayerID()));

	}

	private void confirmConnection() {
		Message answer = (MessPing) new MessPing(100, 0);
		try {
			this.messagesToClient.put(answer);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Forwards player actions to game
	 * 
	 * @param message
	 */
	private void sendToGame(Message message) {
		try {
			games.get(0).messagesFromServer.put(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Checks Log-In information, starts new game if correct
	 * 
	 * @param message
	 */

	private void signInRequest(Message pmessage) {
		MessSignInAndUpRequest message = (MessSignInAndUpRequest) pmessage;
		System.out.println("METHOD ServerEngine.SignInRequest: Method engaging");
		playerFound = false;
		for (Player player : players) {
			if (player.getName().equals(message.getUsername())) {
				System.out.println("player found");
				playerFound = true;
				if (player.getPassword().equals(message.getPassword())) {
					System.out.println("METHOD ServerEngine.SignInRequest: Log-In successful");
					Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(true, players.indexOf(player), 0,
							3);
					try {
						this.messagesToClient.put(answer);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					startGame(player);
				} else {
					System.out.println("METHOD ServerEngine.SignInRequest: Wrong password");
					Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(false, -1, 0, 3);
					try {
						this.messagesToClient.put(answer);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		if (!playerFound) {
			System.out.println("METHOD ServerEngine.SignInRequest: Player doesn't exist"); 
			Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(false, -1, 0, 3);
			try {
				this.messagesToClient.put(answer);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void startGame(Player player) {
		this.messagesToGames = new LinkedBlockingQueue<Message>();
		this.games.addElement(new Game(this, player, 16, this.messagesToGames));
		this.tick.scheduleAtFixedRate(this.games.lastElement(), 0, 50);

	}

	private void signOffRequest(Message message) {
		// TODO Auto-generated method stub

	}

	private void signOutRequest(Message message) {

	}

	private void signUpRequest(Message pmessage) {
		MessSignInAndUpRequest message = (MessSignInAndUpRequest) pmessage;
		System.out.println("Checking Registration");
		playerIsNew = true;
		for (Player player : players) {
			if (message.getUsername().equals(player.getName())) {
				playerIsNew = false;
				Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(false, -1, 0, 3);
				try {
					this.messagesToClient.put(answer);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (playerIsNew) {
			players.addElement(new Player(message.getUsername(),message.getPassword()));
			System.out.println("Player reqgistered");
			Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(true, players.size() - 1, 0, 3);
			try {
				this.messagesToClient.put(answer);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void ConnectionRequest(Message message) {
		System.out.println("Connected");
		Message answer = new TestMessage(0, 5, "");
		try {
			this.messagesToClient.put(answer);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
