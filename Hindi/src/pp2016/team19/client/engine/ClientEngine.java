package pp2016.team19.client.engine;

import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pp2016.team19.client.comm.HandlerClient;
import pp2016.team19.client.gui.GameWindow;
import pp2016.team19.client.gui.SystemMessages;
import pp2016.team19.shared.*;

/***
 * 
 * <h1>This class represents the client-engine together with all its variables
 * and processing methods.</h1>
 * 
 * 
 * 
 * Section 1: - Constructor - A thread is initialized
 * <p>
 * Section 2: - MessageReader-Method to analyze incoming messages - Several
 * subsections with switch cases statements to call the appropriate method
 * <p>
 * Section 3: - Request Methods to send information to the server - Answer
 * Methods to process messages from the sever
 * <p>
 * Section 4: - Helper methods - Getter and Setter methods
 * <p>
 * 
 * @author Oliver Goetz, 5961343
 * 
 */

// *************** SECTION 1 ********************//

public class ClientEngine implements Runnable {

	/**
	 * There are 3 Types of attributes. - network attributes: responsible to
	 * build a up the network - game attributes: responsible to manage the game
	 * - static attributes: define the size of the gamewindow
	 * 
	 * @author Oliver Goetz, 5961343
	 */

	// network attributes
	private ExecutorService threadPool;
	private HandlerClient networkHandler;
	private GameWindow gamewindow;

	// game attributes
	private int playerID;
	private Player myPlayer = new Player();
	private LinkedList<Monster> myMonsterList;
	private Tile[][] labyrinth;
	private SystemMessages sysMes;
	private LinkedList<HighScoreElement> highscore = new LinkedList<HighScoreElement>();

	// static attributes
	public static final int BOX = 32;
	public static final int WIDTH = 16, HEIGHT = 16;

	/**
	 * @author Oliver Goetz, 5961343
	 * @param clientThreadPool
	 *            enables to start a thread
	 */

	public ClientEngine(ExecutorService clientThreadPool) {

		// opens a dialog window to type in a value for the server adress
		JOptionPane.setDefaultLocale(Locale.ENGLISH);

		// creates a new JFrame
		JFrame input = new JFrame();

		// provides a string for the serveradress
		String adress = JOptionPane.showInputDialog(input, "Serveradress", "localhost");

		// if the 'cancel'-button is hit, the serverconnection is canceled
		if (adress == null) {
			System.out.println("Serverconnection canceled!");
			System.exit(0);

			// the game is executed
		} else {

			this.setSystemMessages(new SystemMessages());
			writeSystemMessages("Connection to Server successfully built!");
			// sets the thread
			this.setThreadPool(clientThreadPool);

			// creates a new Networkhandler
			this.setNetworkHandler(new HandlerClient(adress));

			// creates a new GameWindow
			this.setGameWindow(new GameWindow(this, BOX * WIDTH, BOX * HEIGHT, "Hindi Bones"));

			// creates a new Systemmessages Frame
			this.sysMes.setTitle("System Messages");
			this.sysMes.setSize(500, 200);
			this.sysMes.setVisible(true);
			this.sysMes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

	}

	/***
	 * 
	 * This method starts a thread.
	 * 
	 * @author Oliver Goetz, 5961343
	 * 
	 * 
	 */

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Engine started");
		//
		while (true) {
			// the method in thread is every 50 milliseconds executed
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// receives a new message from the server
			Message message = this.getNetworkHandler().getMessageFromServer();
			if (message != null) {
				// analyzes the message
				this.messageReader(message);
			}
		}
	}

	// *************** SECTION 2 ********************//

	/**
	 * SECTION 2 contains the messageReader identifying the messages coming from
	 * the server.
	 * 
	 * There are several subsections that call the appropriate method.
	 * 
	 * TYPE = 0 >> SIGN{UP,IN,OUT,OFF} ACTIONS AND METHODS
	 * 
	 * TYPE = 1 >> USER TRIGGERED ACTIONS AND METHODS
	 * 
	 * TYPE = 2 >> WORLDMANAGEMENT TRIGGERED ACTIONS AND METHODS
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 *            message object that has to be read
	 */

	private void messageReader(Message pMessage) {
		// returns the maintype of the message
		switch (pMessage.getType()) {

		// ********** TYPE = 0 >> SIGN{UP,IN,OUT,OFF} ACTIONS AND METHODS
		// ********** //

		case 0:

			// analyzes the subtype of the message
			switch (pMessage.getSubType()) {
			case 3:
				// processes a serverSignInAndUpAnswer see SECTION 3
				this.serverSignInAndUpAnswer(pMessage);
				break;

			case 9:
				// processes a serverSignOutAnswer see SECTION 3
				this.serverSignOutAnswer(pMessage);
				break;

			default:
				break;
			}

			break;

		// ********** TYPE = 1 : USER TRIGGERED ACTIONS AND METHODS **********
		// //

		case 1:

			// analyzes the subtype of the message
			switch (pMessage.getSubType()) {
			case 1:
				// processes a serverSignInAndUpAnswer see SECTION 3
				this.moveCharacterAnswer(pMessage);
				break;

			case 3:
				// processes a attackAnswer see SECTION 3
				this.attackAnswer(pMessage);
				break;

			case 5:
				// processes a collectItemAnswer see SECTION 3
				this.collectItemAnswer(pMessage);
				break;

			case 7:
				// processes a usePotionAnswer see SECTION 3
				this.usePotionAnswer(pMessage);
				break;

			case 9:
				// processes a openDoorAnswer see SECTION 3
				this.openDoorAnswer(pMessage);
				break;

			case 11:
				// processes an aStarAnswer see SECTION 3
				this.aStarAnswer(pMessage);
				break;

			case 18:
				// test system out, if messages from server arrive
				System.out.println("Message received");
				break;

			default:
				break;
			}

			break;

		// ********** TYPE = 2 : WORLDMANAGEMENT TRIGGERED ACTIONS AND METHODS
		// ********** //

		case 2:
			// analyzes the subtype of the message
			switch (pMessage.getSubType()) {
			case 1:
				// processes an levelAnswer see SECTION 3
				this.levelAnswer(pMessage);
				break;

			case 3:
				// processes an updateMonserAnswer see SECTION 3
				this.updateMonsterAnswer(pMessage);
				break;

			case 5:
				// processes a playerAnswer see SECTION 3
				this.playerAnswer(pMessage);
				break;

			case 7:
				// processes an endGameAnswer see SECTION 3
				this.endGameAnswer(pMessage);
				break;

			case 9:
				// processes a highScoreAnswer see SECTION 3
				this.highscoreAnswer(pMessage);

			default:
				break;
			}

			break;

		} // end of great switch

	} // end of message-Reader

	// *************** SECTION 3 ********************//

	/***
	 * 
	 * In SECTION 3, methods are provided to process requests and answers.
	 * 
	 * There are several subsections that call the appropriate method.
	 * 
	 * TYPE = 0 >> SIGN{UP,IN,OUT,OFF} ACTIONS AND METHODS
	 * 
	 * TYPE = 1 >> USER TRIGGERED ACTIONS AND METHODS
	 * 
	 * TYPE = 2 >> WORLDMANAGEMENT TRIGGERED ACTIONS AND METHODS
	 * 
	 * @author Oliver Goetz, 5961343
	 * 
	 * 
	 */

	// ********** TYPE = 0 >> SIGN{UP,IN} ACTIONS AND METHODS **********

	/**
	 * 
	 * This method sends a SignUpRequest message object to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pUsername
	 *            a userName
	 * @param pPassword
	 *            a password
	 * @param pPassword2
	 *            a password that works as consistency check
	 */

	public void serverSignUpRequest(String pUsername, String pPassword, String pPassword2) {
		System.out.println("METHOD Engine.serverSignUpRequest() " + pUsername + ", " + pPassword + ", " + pPassword2);

		// check if passwords are equal

		if (pPassword.equals(pPassword2)) {
			// if true, the registration is successful and the game menu will be
			// opened
			this.sendToServer(new MessSignInAndUpRequest(pUsername, pPassword, 0, 2));
			System.out.println("Registration successful!");
			writeSystemMessages("Registration successful!");

		} else {
			// if false, there is an output that the passwords are not equal
			JOptionPane.showMessageDialog(null, "Passwords are not equal!");
			System.out.println("Registration failed!");
			writeSystemMessages("Registration failed!");

		}
	}

	/**
	 * 
	 * This method sends a SignInRequest message object to the server. The
	 * username as well as the password is sent.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pUsername
	 *            a userName
	 * @param pPassword
	 *            a password
	 */

	public void serverSignInRequest(String pUsername, String pPassword) {
		System.out.println("METHOD Engine.serverSignInRequest() " + pUsername + ", " + pPassword);
		this.sendToServer(new MessSignInAndUpRequest(pUsername, pPassword, 0, 4));
	}

	/**
	 * This method processes a SignInAndUpAnswer message coming from the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 *            the received message
	 */
	private void serverSignInAndUpAnswer(Message pMessage) {

		// casts the incoming message to an usable message object
		MessSignInAndUpAnswer message = (MessSignInAndUpAnswer) pMessage;
		System.out.println("METHOD Engine.serverSignInAndUpAnswer: SignInAnswer received");

		// checks if the incoming message contains a boolean true
		// if true, the login is conducted and a new player ID is received
		if (message.isConfirmed()) {
			System.out.println("METHOD Engine.serverSignInAndUpAnswer: Login-Data correct");
			playerID = message.getPlayerID();
			// opens the game menu
			getGameWindow().showMenu();
			System.out.println("METHOD Engine.serverSignInAndUpAnswer: Login successful");
			writeSystemMessages("Login successful. Welcome to Hindi Bones, enjoy the Maze Runner Adventure Game!");
		} else {
			JOptionPane.showMessageDialog(null, "Wrong Username or Password");
		}
	}

	/**
	 * Sends a SignOutRequest to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * 
	 */

	public void serverSignOutRequest() {
		System.out.println("METHOD ClientEngine.serverSignOutRequest: SignOutRequest sent!");
		this.sendToServer(new MessSignOutRequest(this.playerID, 0, 8));
	}

	/**
	 * Processes a serverSignOutAnswer from the server and conducts an orderly
	 * sign-out.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 *            a message object
	 * 
	 */
	public void serverSignOutAnswer(Message pMessage) {
		System.out.println("METHOD ClientEngine.serverSignOutAnswer: SignOutAnswer received!");
		// casts the incoming message to a proper message object
		MessSignOutAnswer message = (MessSignOutAnswer) pMessage;
		// checks, if the received message contains a boolean true
		// if true, the client will be shut down, the server will remain online
		if (message.isConfirmed()) {
			System.exit(0);
		}
	}

	// ********** TYPE = 1 : USER TRIGGERED ACTIONS AND METHODS ********** //

	/**
	 * Sends a moveCharacterRequest to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param x
	 *            the x-Position of the player
	 * @param y
	 *            the y-Position of the player
	 * @param direction
	 *            the direction of the player (0=up, 1=down, 2=left, 3=right)
	 */

	public void moveCharacterRequest(int x, int y, int direction) {
		this.sendToServer(new MessMoveCharacterRequest(x, y, direction, 1, 0));
	}

	/**
	 * 
	 * Processes the moverCharacterAnswer coming from the server.
	 * 
	 * @author Oliver Goetz, 5961363
	 * @param pMessage
	 *            a message object
	 * 
	 */

	private void moveCharacterAnswer(Message pMessage) {
		// casts the incoming message to a proper message object
		MessMoveCharacterAnswer message = (MessMoveCharacterAnswer) pMessage;

		// checks if the boolean in message is true
		// if true, a new position is set
		if (message.isConfirmed()) {

			// Sets new position of the player
			myPlayer.xPos = message.getX();
			myPlayer.yPos = message.getY();

		}
	}

	/**
	 * 
	 * Sends an attackRequest to the server.
	 * 
	 * 
	 * @author Oliver Goetz, 5961343
	 * 
	 */
	public void attackRequest() {
		this.sendToServer(new MessAttackRequest(1, 2));
	}

	/**
	 * Processes an attackAnswer Message coming from the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 *            a message object
	 * 
	 */

	public void attackAnswer(Message pMessage) {
		// casts the incoming message to a proper message object
		MessAttackAnswer message = (MessAttackAnswer) pMessage;

		// checks, if a the message contains a boolean true
		if (message.isConfirmed()) {
			// sets the monsterList in the clien-engine, that comes from the
			// server
			this.myMonsterList = message.getMonster();
		}
	}

	/**
	 * 
	 * Sends a collectItemRequest to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * 
	 * 
	 */

	public void collectItemRequest() {
		this.sendToServer(new MessCollectItemRequest(1, 4));
	}

	/**
	 * 
	 * Processes a collectItemAnswer coming from the server.
	 * 
	 * @author Oliver Goetz, 596131
	 * @param pMessage
	 *            a message object
	 * 
	 * 
	 */

	public void collectItemAnswer(Message pMessage) {
		// casts the incoming message to a proper message object
		MessCollectItemAnswer message = (MessCollectItemAnswer) pMessage;

		// checks an ID within the message:
		// 0 = key
		// 1 = potion
		// -1 = nothing to take
		if (message.getID() == 0) {

			// the player takes the key
			this.getMyPlayer().takeKey();
			writeSystemMessages("Key taken!");

			// the key disappears from the labyrinth floor
			this.getLabyrinth()[this.getMyPlayer().getXPos()][this.getMyPlayer().getYPos()].setContainsKey(false);

		} else if (message.getID() == 1) {

			// the player takes a potion
			this.getMyPlayer().takePotion();
			writeSystemMessages("Potion taken!");

			// the potion disappears from the labyrinth floor
			this.getLabyrinth()[this.getMyPlayer().getXPos()][this.getMyPlayer().getYPos()].setContainsPotion(false);
		} else if (message.getID() == -1) {
			// nothing happens, because nothing is on the floor to take
		}
	}

	/**
	 * 
	 * Sends a usePotionRequest to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * 
	 * 
	 */

	public void usePotionRequest() {
		this.sendToServer(new MessUsePotionRequest(1, 6));
	}

	/**
	 * 
	 * Processes a usePotionAnswer coming from the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 *            a message object
	 * 
	 * 
	 */

	public void usePotionAnswer(Message pMessage) {

		// casts the incoming message to a proper message
		MessUsePotionAnswer message = (MessUsePotionAnswer) pMessage;

		// checks if the message contains a boolean true
		if (message.isConfirmed()) {

			// updates the player in the client-engine respectively in the gui
			this.myPlayer = message.getPlayer();
			writeSystemMessages("Potion used, health refilled!");
		}
	}

	/**
	 * Sends an openDoorRequest to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * 
	 */

	public void openDoorRequest() {
		this.sendToServer(new MessOpenDoorRequest(true, 1, 8));
	}

	/**
	 * 
	 * Processes an openDoorAnswer coming from the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 *            a message object
	 * 
	 */

	public void openDoorAnswer(Message pMessage) {

		// casts the incoming message to a proper message object
		MessOpenDoorAnswer message = (MessOpenDoorAnswer) pMessage;

		// checks, if the message contains a boolean true
		if (message.getOpenDoor() == true) {

			// the nextlevel is loaded
			gamewindow.nextLevel();

			// the player has the key and opens the door
			writeSystemMessages("Level completed. New level entered!");
		}
	}

	/**
	 * 
	 * Sends an aStarRequest to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param mouseX
	 *            the x-coordination of the mouseclick
	 * @param mouseY
	 *            the y-coordination of the mouseclick
	 * 
	 */

	public void aStarRequest(int mouseX, int mouseY) {
		this.sendToServer(new MessAstarRequest(mouseX, mouseY, 1, 10));
	}

	/**
	 * Processes an aStarAnswer coming from the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 *            a message object
	 * 
	 */

	public void aStarAnswer(Message pMessage) {

		// casts the incoming message to a proper message object
		MessAstarAnswer message = (MessAstarAnswer) pMessage;
		this.myPlayer = message.getMyPlayer();

	}

	/**
	 * Sends an cheatRequeset to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param cheat
	 *            a cheat
	 */
	public void cheatRequest(String cheat) {
		this.sendToServer(new MessCheatRequest(cheat, 1, 12));
		
		if (cheat.equals("godmode")) {
			if (getMyPlayer().hasShield()) {
				writeSystemMessages(cheat + " " + "is deactivated!");
			} else {
				writeSystemMessages(cheat + " " + "is activated!");
			}
		} else if (cheat.equals("superdebugger")) {
			if (getMyPlayer().getDamage() < 100) {
				writeSystemMessages(cheat + " " + "is activated!");
			} else {
				writeSystemMessages(cheat + " " + "is deactivated!");
			}
		} else if ((cheat.equals("thekeytolearningjava")) || (cheat.equals("thislevelisboring"))) {
			writeSystemMessages(cheat + " " + "is activated!");
		} else {
			writeSystemMessages(cheat + " " + "does not exist!");
		}
	}

	// ********** TYPE = 2 : WORLDMANAGEMENT TRIGGERED ACTIONS AND METHODS
	// ********** //

	/**
	 * Sends a startGameRequest, that also initiates a levelRequest, to the
	 * server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param ID
	 *            the ID of the player
	 * 
	 * 
	 */

	public void startGameRequest(int ID) {
		this.sendToServer(new MessStartGameRequest(this.getPlayerID(), 0, 6));
		writeSystemMessages("New game started!");
	}

	/**
	 * 
	 * Processes a levelAnswer Message coming from the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 *            a message object
	 *
	 */

	public void levelAnswer(Message pMessage) {

		// casts the incoming message to a proper message object
		MessLevelAnswer message = (MessLevelAnswer) pMessage;

		// sets the new monsterlist
		this.myMonsterList = message.getMonsters();

		// sets the new labyrinth
		this.labyrinth = message.getLabyrinth();

	}

	/**
	 * Sends a playerRequest to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 */

	public void playerRequest() {
		this.sendToServer(new MessPlayerRequest(myPlayer, 2, 4));
	}

	/**
	 * Processes an playerAnswer coming from the server
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 *            a message object
	 */

	public void playerAnswer(Message pMessage) {

		// casts the incoming message to a proper message object
		MessPlayerAnswer message = (MessPlayerAnswer) pMessage;

		// sets a new player
		this.myPlayer = message.getMyPlayer();

		// sets a flag so that the gameloop {see Gamewindow} is correctly
		// conducted
		gamewindow.setTest(true);

	}

	/**
	 * 
	 * Sends an updateMonsterRequest to update the health to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param monster
	 *            a monster
	 * 
	 */

	public void updateMonsterRequest(Monster monster) {
		this.sendToServer(new MessUpdateMonsterRequest(2, 2));
	}

	/**
	 * 
	 * Process an updateMonsterAnswer coming from the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 *            a message object
	 * 
	 */

	public void updateMonsterAnswer(Message pMessage) {

		// casts the incoming message to a proper message object
		MessUpdateMonsterAnswer message = (MessUpdateMonsterAnswer) pMessage;

		// updates the monsterlist with new health
		this.myMonsterList = message.getMonsterList();
	}

	/**
	 * 
	 * Sends an endGameRequest to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * 
	 * 
	 */

	public void endGameRequest() {
		this.sendToServer(new MessEndGameRequest(2, 6));
	}

	/**
	 * Processes an endGameAnswer coming from the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 *            a message object
	 * 
	 */

	public void endGameAnswer(Message pMessage) {

		// casts the incoming message to a proper message object
		MessEndGameAnswer message = (MessEndGameAnswer) pMessage;

		// sets the player's achieved score
		this.myPlayer.setScore(message.getScore());

		// checks the state of the endgame.
		// gameWon: represents the end-state of the game
		// (e.g. last level has been finished, gameWon = true, 
		// no health left gameWon = true).
		// gameLost: gameLost = true, if all health is gone. False, if the
		// player ended the last level.
		if (message.isGameWon()) {

			// state, that the game is won. Player completed the last level.
			this.getGameWindow().gameLost = false;
			this.getGameWindow().gameWon = true;
			
			writeSystemMessages("Congratulations! You escaped from the Maze!!");
			
		} else {

			// state, that the game is lost. The game is over and lost because
			// the player has no health left.
			this.getGameWindow().gameLost = true;
			this.getGameWindow().gameWon = true;
			writeSystemMessages("Sorry! The monsters were stronger than you!");
		}

	}

	/**
	 * Processes an highscoreAnswer message coming from the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 *            a message object
	 * 
	 */

	public void highscoreAnswer(Message pMessage) {

		// casts the incoming message to a proper message object
		MessHighscoreAnswer message = (MessHighscoreAnswer) pMessage;

		// sets the highscore list by taking the highscore list from
		// the incoming message object

		this.highscore = message.getHighscore();
	}

	/**
	 * Sends a quitGameRequeset to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 */

	public void quitGameRequest() {
		this.sendToServer(new MessQuitGameRequest(2, 10));
	}

	// *************** SECTION 4 ********************//

	/**
	 * SECTION 4 contains helper methods as well Getter and Setter.
	 * 
	 * @author Oliver Goetz, 5961343
	 * 
	 */

	// ********* HELPERS and GETTERS'n'SETTERS ********** //

	/**
	 * This methods calls the newtorkhandler and puts a message in a queue.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 */
	private void sendToServer(Message pMessage) {
		this.networkHandler.sendMessageToServer(pMessage);
	}

	/**
	 * Writes the systemmessages into the textarea of the SysteMessages class.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param sysmes
	 *            a systemmessage
	 */
	public void writeSystemMessages(String sysmes) {
		sysMes.getMessArea().setText(sysmes + "\n" + sysMes.getMessArea().getText());
	}

	/**
	 * The getter Method for the player.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @return a player
	 */
	public Player getMyPlayer() {
		return myPlayer;
	}

	/**
	 * Setter Method for the player.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param myPlayer
	 *            the player
	 */
	public void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}

	/**
	 * Getter Method for the MonsterList.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @return myMonsterList
	 */
	public LinkedList<Monster> getMyMonster() {
		return myMonsterList;
	}

	/**
	 * Setter Method for the MonsterList.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param myMonsterList
	 *            the MonsterList
	 */
	public void setMyMonster(LinkedList<Monster> myMonsterList) {
		this.myMonsterList = myMonsterList;
	}

	/**
	 * Getter Method for the threadPool
	 * 
	 * @author Oliver Goetz, 5961343
	 * @return a threadPool
	 */
	public ExecutorService getThreadPool() {
		return threadPool;
	}

	/**
	 * Setter Method for the threadPool
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param threadPool
	 *            a threadPool
	 */
	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	/**
	 * Getter Method for the networkhandler.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @return the Client NetworkHandler
	 */
	public HandlerClient getNetworkHandler() {
		return networkHandler;
	}

	/**
	 * Setter Method for the networkhandler.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param networkHandler
	 *            a networkhandler
	 */
	public void setNetworkHandler(HandlerClient networkHandler) {
		this.networkHandler = networkHandler;
	}

	/**
	 * Getter Method for a gamewindow.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @return the gamewindow
	 */
	public GameWindow getGameWindow() {
		return gamewindow;
	}

	/**
	 * Setter Method for a gamewindow.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param gamewindow
	 *            a gamewindow
	 */
	public void setGameWindow(GameWindow gamewindow) {
		this.gamewindow = gamewindow;
	}

	/**
	 * Getter Method for the labyrinth.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @return a labyrinth
	 */

	public Tile[][] getLabyrinth() {
		return labyrinth;
	}

	/**
	 * Getter Method for an unique playerID.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @return a playerID
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * Sets the SystemMessages class.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param sysmes
	 *            the systemmessages
	 */
	public void setSystemMessages(SystemMessages sysmes) {
		this.sysMes = sysmes;
	}

	/**
	 * Gets the linked list that contains the highscore
	 * 
	 * @author Oliver Goetz, 5961343
	 * @return the highscore linked list
	 */

	public LinkedList<HighScoreElement> getHighscore() {
		return highscore;
	}

} // end of ClientEngine-class