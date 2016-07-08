package pp2016.team19.client.engine;

import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pp2016.team19.client.comm.HandlerClient;
import pp2016.team19.client.gui.GameWindow;
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

	// network attributes
	private ExecutorService threadPool;
	private HandlerClient networkHandler;
	private GameWindow gamewindow;

	// game attributes
	private int playerID;
	private Player myPlayer = new Player();
	private LinkedList<Monster> myMonsterList;
	private Tile[][] labyrinth;
	private boolean gamewon;

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
		JFrame input = new JFrame();
		String adress = JOptionPane.showInputDialog(input, "Serveradress", "localhost");
		if(adress == null) {
			System.out.println("Serverconnection canceled");
			  System.exit(0);
		} else {
		
		// sets the thread
		this.setThreadPool(clientThreadPool);

		// creates a new Networkhandler
		this.setNetworkHandler(new HandlerClient(adress));

		// creates a new GameWindow
		this.setGameWindow(new GameWindow(this, BOX * WIDTH, BOX * HEIGHT, "Hindi Bones"));
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
		while (true) {
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// creates a new message and puts it in a queue of the networkandler
			Message message = this.getNetworkHandler().getMessageFromServer();
			if (message != null) {
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
		// **********
		case 0:
			// analyzes the subtype of the message
			switch (pMessage.getSubType()) {
			case 3:
				// processes a serverSignInAndUpAnswer see SECTION 3
				this.serverSignInAndUpAnswer(pMessage);
				break;

			 case 9:
			 this.serverSignOutAnswer(pMessage);
			 break;
			
			// case 7:
			// this.serverSignOffAnswer(pMessage);
			// break;

			default:
				break;
			}

			break;

		// ********** TYPE = 1 : USER TRIGGERED ACTIONS AND METHODS **********
		case 1:
			// analyzes the subtype of the message
			switch (pMessage.getSubType()) {
			case 1:
				// processes a serverSignInAndUpAnswer see SECTION 3
				this.moveCharacterAnswer(pMessage);
				break;

			case 3:
				this.attackAnswer(pMessage);
				break;

			case 5:
				this.collectItemAnswer(pMessage);
				break;

			case 7:
				this.usePotionAnswer(pMessage);
				break;

			case 9:
				this.openDoorAnswer(pMessage);
				break;

			case 11:
				this.aStarAnswer(pMessage);
				break;

			case 18:
				System.out.println("Message received");
				break;

			default:
				break;
			}

			break;

		// ********** TYPE = 2 : WORLDMANAGEMENT TRIGGERED ACTIONS AND METHODS
		// **********

		case 2:

			switch (pMessage.getSubType()) {
			case 1:
				this.levelAnswer(pMessage);
				break;

			case 3:
				this.updateMonsterAnswer(pMessage);
				break;

			case 5:
				this.playerAnswer(pMessage);
				break;

			case 7:
				this.endGameAnswer(pMessage);
				break;
				
			default:
				break;
			}

			break;

		} // end of great switch

	} // end of message-Reader

	// *************** SECTION 3 ********************//

	/***
	 * 
	 * In SECTION 3, methods are provided to process requests and answers
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
	 * @param pMessage
	 *            message oject coming from the server
	 * @param userName
	 *            a userName
	 * @param password
	 *            a password
	 * @param password2
	 *            a password that works as consistency check
	 */

	public void serverSignUpRequest(String pUsername, String pPassword, String pPassword2) {
		System.out.println("METHOD Engine.serverSignUpRequest() " + pUsername + ", " + pPassword + ", " + pPassword2);

		// check if passwords are equal
		// if true, the registration is successful and the game menu will be opened
		// if false, there is an output that the passwords are not equal
		if (pPassword.equals(pPassword2)) {
			this.sendToServer(new MessSignInAndUpRequest(pUsername, pPassword, 0, 2));
			System.out.println("Registration successful!");

		} else {
			JOptionPane.showMessageDialog(null, "Passwords are not equal!");
			System.out.println("Registration failed!");
		}
	}

	/**
	 * 
	 * This method sends a SignInRequest message object to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 *            message object coming from the server
	 * @param userName
	 *            a userName
	 * @param password
	 *            a password
	 */

	// Sends a SignInRequest to the server
	public void serverSignInRequest(String pUsername, String pPassword) {
		System.out.println("METHOD Engine.serverSignInRequest() " + pUsername + ", " + pPassword);
		this.sendToServer(new MessSignInAndUpRequest(pUsername, pPassword, 0, 4));
	}

	/**
	 * This method processes a SignInAndUpAnswer message coming from the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage the received message
	 */
	private void serverSignInAndUpAnswer(Message pMessage) {
		System.out.println("METHOD Engine.serverSignInAnswer() " + pMessage.toString());

		// casts the incoming message to usable message object
		MessSignInAndUpAnswer message = (MessSignInAndUpAnswer) pMessage;
		System.out.println("Server SignInAnswer received");

		// checks if the incoming message contains a boolean true
		if (message.isConfirmed()) {
			System.out.println("Data correct");
			playerID = message.getPlayerID();
			// this.myPlayer.ID = message.getPlayerID();

			// opens the game menu
			getGameWindow().showMenu();
			System.out.println("login successful");
		} else {
			JOptionPane.showMessageDialog(null, "Wrong Username or Password");
		}
	}
	
	
	/**
	 * @author Oliver Goetz, 5961343
	 * 
	 */
	
	public void serverSignOutRequest() {
		System.out.println("METHOD ClientEngine.serverSignOutRequest: SignOutRequest sent!");
		this.sendToServer(new MessSignOutRequest(this.playerID,0,8));
	}
	
	public void serverSignOutAnswer(Message pMessage) {
		System.out.println("METHOD ClientEngine.serverSignOutAnswer: SignOutAnswer received!");
		MessSignOutAnswer message = (MessSignOutAnswer) pMessage;
		if (message.isConfirmed()) {
			try {
				this.getGameWindow().getEngine().getThreadPool().awaitTermination(50, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.getGameWindow().showLogin();
			
		}
	}

	// ********** TYPE = 1 : USER TRIGGERED ACTIONS AND METHODS ********** //

	// Sends a moveCharacterRequest to the server
	public void moveCharacterRequest(int x, int y, int direction) {
		System.out.println("METHOD ClientEngine.moveCharacterRequest: Messages sent sent sent!!");
		
		this.sendToServer(new MessMoveCharacterRequest(x, y, direction, 1, 0));
	}

	// Processes a moveCharacterAnswer Message coming from the server
	private void moveCharacterAnswer(Message pMessage) {

		// Casts the message
		MessMoveCharacterAnswer message = (MessMoveCharacterAnswer) pMessage;

		// Checks if the boolean in message is true
		if (message.isConfirmed()) {

			// Sets new position of the player
			myPlayer.xPos = message.getX();
			myPlayer.yPos = message.getY();

		}
	}

	// Sends an attackRequest to the server
	public void attackRequest() {
		this.sendToServer(new MessAttackRequest(1, 2));
	}

	// Processes an attackAnswer Message coming from the server
	public void attackAnswer(Message pMessage) {

		MessAttackAnswer message = (MessAttackAnswer) pMessage;
		if (message.isConfirmed()) {

			this.myMonsterList = message.getMonster();
		}
	}

	public void collectItemRequest() {

		this.sendToServer(new MessCollectItemRequest(1, 4));
	}

	public void collectItemAnswer(Message pMessage) {

		MessCollectItemAnswer message = (MessCollectItemAnswer) pMessage;
		if (message.getID() == 0) {
			this.getMyPlayer().takeKey();
			this.getLabyrinth()[this.getMyPlayer().getXPos()][this.getMyPlayer().getYPos()].setContainsKey(false);

		} else if (message.getID() == 1) {
			this.getMyPlayer().takePotion();
			this.getLabyrinth()[this.getMyPlayer().getXPos()][this.getMyPlayer().getYPos()].setContainsPotion(false);
		} else if (message.getID() == -1) {

		}
	}

	public void usePotionRequest() {

		this.sendToServer(new MessUsePotionRequest(1, 6));

	}

	public void usePotionAnswer(Message pMessage) {

		MessUsePotionAnswer message = (MessUsePotionAnswer) pMessage;
		if (message.isConfirmed()) {
			this.myPlayer = message.getPlayer();
		}
	}

	public void openDoorRequest() {

		this.sendToServer(new MessOpenDoorRequest(true, 1, 8));

	}

	public void openDoorAnswer(Message pMessage) {

		MessOpenDoorAnswer message = (MessOpenDoorAnswer) pMessage;
		if (message.getOpenDoor() == true) {
			gamewindow.nextLevel();

			System.out.println("METHOD Engine.openDoorAnswer: Level completed!");
		} else {
			System.out.println("METHOD Engine.openDoorAnswer: No key available!");
		}

	}

	public void aStarRequest(int mouseX, int mouseY) {
		this.sendToServer(new MessAstarRequest(mouseX, mouseY, 1, 10));
	}

	public void aStarAnswer(Message pMessage) {
		MessAstarAnswer message = (MessAstarAnswer) pMessage;
		this.myPlayer = message.getMyPlayer();
	}



	// ********** TYPE = 2 : WORLDMANAGEMENT TRIGGERED ACTIONS AND METHODS ********** //


	public void startGameRequest(int ID) {
		System.out.println("METHOD ClientEngine.startGameRequest: New game requested");
		this.sendToServer(new MessStartGameRequest(this.getPlayerID(), 0, 6));
	}

	// Sends a levelRequest to the server
	public void levelRequest() {
		// int l = 0;
		// this.sendToServer(new MessLevelRequest(0,2,0));
	}

	// Processes a levelAnswer Message coming from the server
	public void levelAnswer(Message pMessage) {
		System.out.println("METHOD ClientEngine.levelAnswer: Level received! ");
		MessLevelAnswer message = (MessLevelAnswer) pMessage;
		this.myMonsterList = message.getMonsters();
		this.labyrinth = message.getLabyrinth();

	}

	// Sends a playerRequest to the server
	public void playerRequest() {
		this.sendToServer(new MessPlayerRequest(myPlayer, 2, 4));
	}

	// Processes an playerAnswer Message coming from the server
	public void playerAnswer(Message pMessage) {
		System.out.println("METHOD Engine.playerAnswer: Player received!");

		// Casts the incoming MessPlayerAnswer
		MessPlayerAnswer message = (MessPlayerAnswer) pMessage;

		// Sets a new player
		this.myPlayer = message.getMyPlayer();
		gamewindow.setTest(true);

	}
	

	public void updateMonsterRequest(Monster monster) {
		this.sendToServer(new MessUpdateMonsterRequest(2, 2));
	}

	public void updateMonsterAnswer(Message pMessage) {

		MessUpdateMonsterAnswer message = (MessUpdateMonsterAnswer) pMessage;
		this.myMonsterList = message.getMonsterList();
	}
	
	
	public void endGameRequest() {
		this.sendToServer(new MessEndGameRequest(2,6));
	}
	
	public void endGameAnswer(Message pMessage) {
		MessEndGameAnswer message = (MessEndGameAnswer) pMessage;
		this.myPlayer.setScore(message.getScore());
		if(message.isGameWon()) {
			this.getGameWindow().gameLost = false;
			this.getGameWindow().gameWon = true;
		} else {
			this.getGameWindow().gameLost = true;
			this.getGameWindow().gameWon = true;
		}
		
	}

	// ***** Section 4 *****//


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
	 * @param myMonsterList the MonsterList
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
	 * @param threadPool a threadPool
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
	 * @param networkHandler a networkhandler
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
	 * @param gamewindow a gamewindow
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


	
	
} // end of ClientEngine-class