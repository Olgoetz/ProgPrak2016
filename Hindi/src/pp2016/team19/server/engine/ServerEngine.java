package pp2016.team19.server.engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;

import pp2016.team19.server.comm.HandlerServer;
import pp2016.team19.shared.*;

/**
 * <h1>This class represents the Server Engine.</h1>
 * 
 * The server engine is responsible for distribution of messages,
 * administration of players and execution of games.
 * 
 * @author Tobias Schrader, 5637252
 *
 */
public class ServerEngine implements Runnable {
	LinkedBlockingQueue<Message> messagesToClient;
	// stores the players during runtime
	private ArrayList<Player> players = new ArrayList<Player>();
	// accesses userlist file database
	UserList users = new UserList(this);
	// schedules games
	Timer tick = new Timer();
	// communicates with client
	HandlerServer network = new HandlerServer();
	// handles highscores
	Highscore highscores;
	LinkedList<HighScoreElement> highscore;
	// attributes for administration
	private boolean playerIsNew;
	private boolean playerFound;
	private int currentPlayerID;

	/**
	 * Constructor sets Message Queues for communication, accesses files
	 * 
	 * @param messagesToClient
	 * @author Tobias Schrader, 5637252
	 */
	public ServerEngine(LinkedBlockingQueue<Message> messagesToClient) {
		this.messagesToClient = messagesToClient;
		players = users.readUserList();
		highscores = new Highscore();
	}

	/**
	 * This method is executed in a thread, it keeps processing Messages
	 * 
	 * @author Tobias Schrader, 5637252
	 */
	public void run() {
		System.out.println("METHOD ServerEngine.run: Started");
		while (true) {
			Message message = network.getMessageFromClient();
			if (message != null) {
				if (message.getType() != 100) {
					// System.out.println("SE: Message received");
					System.out.println(message.toString());
					this.distributor(message);
				}
			}
			if (!this.messagesToClient.isEmpty()) {
				network.sendMessageToClient(this.messagesToClient.poll());
			}
		}
	}

	/**
	 * Determines and executes action depending on message type and subtype Type
	 * 0: Server Management. Invokes a method in the Server Engine. Type 1:
	 * Character management. Forwarded to the game. Type 100: Ping.
	 * 
	 * @param message,
	 *            the request from the client
	 * @author Tobias Schrader, 5637252
	 */
	public void distributor(Message message) {
		switch (message.getType()) {
		case 0:
			switch (message.getSubType()) {
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
			default:
				break;
			}
			break;
		case 1:
			this.sendToGame(message);
			break;
		case 2:
			switch (message.getSubType()) {
			case 10:
			System.exit(0);
			break;
			}
		case 100:
			this.confirmConnection();
			break;
		default:
			break;
		}
	}

	/**
	 * This method registers new users. If the requested username doesn't exist,
	 * a new player object is created. An answer is sent back to the client with
	 * information about success and the players ID.
	 * 
	 * @param pmessage,
	 *            message that contains username and password
	 * @author Tobias Schrader, 5637252
	 */
	private void signUpRequest(Message pmessage) {
		MessSignInAndUpRequest message = (MessSignInAndUpRequest) pmessage;
		System.out.println("Checking Registration");
		playerIsNew = true;
		for (Player player : players) {
			if (message.getUsername().equals(player.getName())) { // searches if
																	// username
																	// already
																	// exists
				playerIsNew = false;
				System.out.println("Username already existing");
				Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(false, -1, 0, 3);
				try {
					this.messagesToClient.put(answer);
				} catch (InterruptedException e) {
				}
			}
		}
		if (playerIsNew) { // new player created
			Player player = new Player(message.getUsername(), message.getPassword());
			players.add(player);
			users.addPlayerToList(player);
			player.logIn();
			System.out.println("Player registered");
			currentPlayerID = players.size() - 1;
			Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(true, players.size() - 1, 0, 3);
			try {
				this.messagesToClient.put(answer);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * This method handles SignIn. If searches for the requested player and
	 * compares the password. If both is successful, player gets loggedIn
	 * status, which allows him to start games. An answer is sent back for
	 * confirmation.
	 * 
	 * @param pmessage,
	 *            message that contains username and password
	 * @author Tobias Schrader, 5637252
	 */
	private void signInRequest(Message pmessage) {
		MessSignInAndUpRequest message = (MessSignInAndUpRequest) pmessage;
		// System.out.println("METHOD ServerEngine.SignInRequest: Method
		// engaging");
		playerFound = false;
		for (Player player : players) {
			if (player.getName().equals(message.getUsername())) { // searches
																	// for
																	// player
				System.out.println("player found");
				playerFound = true;
				if (player.getPassword().equals(message.getPassword())) { // compares
																			// password
					System.out.println("METHOD ServerEngine.SignInRequest: Log-In successful");
					player.logIn();
					currentPlayerID = players.indexOf(player);
					Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(true, players.indexOf(player), 0,
							3);
					try {
						this.messagesToClient.put(answer);
					} catch (InterruptedException e) {
					}
				} else {
					System.out.println("METHOD ServerEngine.SignInRequest: Wrong password");
					Message answer = (MessSignInAndUpAnswer) new MessSignInAndUpAnswer(false, -1, 0, 3);
					try {
						this.messagesToClient.put(answer);
					} catch (InterruptedException e) {
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
			}
		}
	}

	/**
	 * If the player is logged in, all game info is reset and a new game is
	 * started.
	 * 
	 * @param pmessage,
	 *            messages that contains the ID of the player
	 * @author Tobias Schrader, 5637252
	 */
	private void newGame(Message pmessage) {
		MessStartGameRequest message = (MessStartGameRequest) pmessage;
		Player player = players.get(message.getPlayerID());
		if (player.isLoggedIn()) {
			if (player.getGame() != null) {
				player.getGame().stopGame();
			}
			player.reset();
			player.setGame(new Game(this, player, 16));
			tick.cancel();
			tick = new Timer();
			// has the game execute another step every 50ms
			this.tick.scheduleAtFixedRate(player.getGame(), 0, 50);
			// sends current highscore list for display in the game window
			Message highScore = (MessHighscoreAnswer) new MessHighscoreAnswer(highscores.getHighScore(), 2, 9);
			try {
				messagesToClient.put(highScore);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * This method logs a player out and stops his game if necessary.
	 * 
	 * @param pmessage,
	 *            message that contains the player
	 */
	private void signOutRequest(Message pmessage) {
		MessSignOutRequest message = (MessSignOutRequest) pmessage;
		if (players.get(message.getPlayerID()).getGame() != null) {
			players.get(message.getPlayerID()).getGame().stopGame();
		}
		players.get(message.getPlayerID()).logOut();
		tick.cancel();
		Message answer = (MessSignOutAnswer) new MessSignOutAnswer(true, 0, 9); // answer
																				// sent
																				// for
																				// confirmation
		try {
			this.messagesToClient.put(answer);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * This method just forwards player actions to the game
	 * 
	 * @param message
	 * @author Tobias Schrader, 5637252
	 */
	private void sendToGame(Message message) {
		try {
			players.get(currentPlayerID).getGame().messagesFromServer.put(message);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * This method sends a ping back to confirm connection
	 * 
	 * @author Tobias Schrader
	 */
	private void confirmConnection() {
		Message answer = (MessPing) new MessPing(100, 0);
		try {
			this.messagesToClient.put(answer);
		} catch (InterruptedException e) {
		}
	}
}
