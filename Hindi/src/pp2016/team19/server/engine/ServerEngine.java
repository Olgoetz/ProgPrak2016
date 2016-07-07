package pp2016.team19.server.engine;

import java.util.ArrayList;
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
	//LinkedBlockingQueue<Message> messagesToGames = new LinkedBlockingQueue<Message>();

	private ExecutorService threadPool;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Game> games = new ArrayList<Game>();
	Timer tick = new Timer();
	HandlerServer network = new HandlerServer();
	private boolean playerIsNew;
	private boolean playerFound;
	private int currentPlayerID;

	/**
	 * Constructor sets Message Queues for communication
	 * 
	 * @param serverThreadPool
	 * @param messagesToClient
	 */
	public ServerEngine(ExecutorService serverThreadPool, LinkedBlockingQueue<Message> messagesToClient) {
		this.threadPool = serverThreadPool;
		this.messagesToClient = messagesToClient;
		players.add(new Player("user","123"));
		games.add(null);
	}

	/**
	 * Keeps processing Messages
	 *  @author Tobias Schrader
	 */
	public void run() {
		System.out.println("METHOD ServerEngine.run: Started");
		while (true) {
			Message message = network.getMessageFromClient();
			if (message != null) {
				if (message.getType() != 100) {
					System.out.println("SE: Message received");
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
	 *  @author Tobias Schrader
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
				break;
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
			break;
		case 100:
			this.confirmConnection();
			break;
		default:
			break;
		}
	}

	private void newGame(Message pmessage) {
		MessStartGameRequest message = (MessStartGameRequest) pmessage;
		Player player = players.get(message.getPlayerID());
		System.out.println(player.isLoggedIn());
		if (player.isLoggedIn()) {
		startGame(message.getPlayerID(), player);
		}
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
	 *  @author Tobias Schrader
	 */
	private void sendToGame(Message message) {
		try {
			games.get(currentPlayerID).messagesFromServer.put(message);
			System.out.println("METHOD ServerEngine.sendToGame: Messages forwarded");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Checks Log-In information, starts new game if correct
	 * 
	 * @param message
	 *  @author Tobias Schrader
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
					player.logIn();
					currentPlayerID = players.indexOf(player);
					Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(true, players.indexOf(player), 0,
							3);
					try {
						this.messagesToClient.put(answer);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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

	private void startGame(int ID, Player player) {
		System.out.println("METHOD SE.startGame: ID: "+ID);
//		if(this.games.get(ID)!=null) {
//		this.games.get(ID).stopGame();
//		}
		player.reset();
		this.games.set(ID, new Game(this, player, 16));
		System.out.println("METHOD SE.startGame: game.gameEnded: "+this.games.get(ID).gameEnded);
		System.out.println("METHOD SE.startGame: game.tester: "+this.games.get(ID).tester);
		tick.cancel();
		tick = new Timer();
		this.tick.scheduleAtFixedRate(this.games.get(ID), 0, 50);

	}

	private void signOffRequest(Message message) {
		// TODO Auto-generated method stub

	}

	private void signOutRequest(Message pmessage) {
		MessSignOutRequest message = (MessSignOutRequest) pmessage;
		if (games.get(message.getPlayerID())!=null) {
		games.get(message.getPlayerID()).stopGame();
		}
		players.get(message.getPlayerID()).logOut();
		
		Message answer = (MessSignOutAnswer) new MessSignOutAnswer(true,0,9);
		try {
			this.messagesToClient.put(answer);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			Player player=new Player(message.getUsername(),message.getPassword());
			players.add(player);
			player.logIn();
			games.add(null);
			System.out.println("Player registered");
			currentPlayerID = players.size() - 1;
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
