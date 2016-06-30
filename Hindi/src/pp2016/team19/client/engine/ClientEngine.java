package pp2016.team19.client.engine;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import pp2016.team19.client.*;
import pp2016.team19.client.comm.HandlerClient;
import pp2016.team19.client.gui.GameWindow;
import pp2016.team19.shared.*;




/***
 * 
 * <h1>This class represents the client-engine together with all its variables and processing
 * methods.</h1>
 * 
 * 
 * 
 * Section 1:
 * 				- Constructor 
 * 				- A thread is initialized
 * <p>	
 * Section 2:
 * 				- MessageReader-Method to analyze incoming messages
 * 				- Several subsections with switch cases statements to call the appropriate method
 * <p>
 * Section 3:
 * 				- Request Methods to send information to the server
 * 				- Answer Methods to process messages from the sever
 * <p>
 * Section 4:
 * 				- Helper methods
 * 				- Getter and Setter methods
 * <p>
 * @author Oliver Goetz, 5961343
 * 				
 */


//*************** SECTION 1 ********************//


public class ClientEngine implements Runnable   {

	// network attributes
	private ExecutorService threadPool;
	private HandlerClient networkHandler;
	private GameWindow gamewindow;
	private String serverAdress;
	private String port;
	
	// game attributes
	private int playerID;
	private Player myPlayer;
	private Monster myMonster;
	private int direction;
	public Tile[][] labyrinth;
	private Labyrinth test = new Labyrinth();
	
	// static attributes
	public static final int BOX = 32;
	public static final int WIDTH = 16, HEIGHT = 16;
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param clientThreadPool enables to start a tread
	 */
	public ClientEngine(ExecutorService clientThreadPool) {
		System.out.println("Start Constructor");
		this.setThreadPool(clientThreadPool);
		
		// creates a new Networkhandler
		this.setNetworkHandler(new HandlerClient());
		
		// creates a new GameWindow
		this.setGameWindow(new GameWindow(this,BOX*WIDTH, BOX*HEIGHT, "Hindi Bones"));
		this.getThreadPool().execute(this.getGameWindow());
		
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
			// creates a new message and puts in a queue of the networkandler
			Message message = this.getNetworkHandler().getMessageFromServer();
			if (message != null) {
				this.messageReader(message);
			}		
		}
	}
	

	
//*************** SECTION 2 ********************//
	
	
	/**
	 * SECTION 2 contains the messageReader identifying the messages coming from the server.
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
	 * @param pMessage message object that has to be read
	 */
	

	private void messageReader(Message pMessage) {

		// returns the maintype of the message
		switch (pMessage.getType()) {

		// ********** TYPE = 0 >> SIGN{UP,IN,OUT,OFF} ACTIONS AND METHODS **********
		case 0:
			// analyzes the subtype of the message
			switch (pMessage.getSubType()) {
			case 3:
				// processes a serverSignInAndUpAnswer see SECTION 3
				this.serverSignInAndUpAnswer(pMessage);
				break;

		

//			case 5:
//				this.serverSignOutAnswer(pMessage);
//				break;
//
//			case 7:
//				this.serverSignOffAnswer(pMessage);
//				break;

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
			// test case to see if messages are coming form the server	
			case 18:
				System.out.println("Message received");
				break;

			default:
				break;
			}

			break;	
			

		// ********** TYPE = 2 : WORLDMANAGEMENT TRIGGERED ACTIONS AND METHODS **********
		
		case 2:
		
			switch (pMessage.getSubType()) {
			case 1:
				this.levelAnswer(pMessage);
				break;
				
			case 5:
				this.playerAnswer(pMessage);
				break;
		
		
			default:
				break;
			}
		
			break;	
			
		} // end of great switch	
		
	} // end of message-Reader	
	
	

	
	
//*************** SECTION 3 ********************//
	
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
	 * @param pMessage message oject coming from the server
	 * @param userName a userName
	 * @param password a password
	 * @param password2 a password that works as consistency check
	 */
	
	public void serverSignUpRequest(String pUsername, String pPassword, String pPassword2) {
		System.out.println("METHOD Engine.serverSignUpRequest() " + pUsername + ", " + pPassword + ", " + pPassword2);
		
		// check if passwords are equal
		// if true, the registration is successful and the game menu will be opened
		// if false, there is an output that the passwords are not equal
		if (pPassword.equals(pPassword2)) {
			this.sendToServer(new MessSignInAndUpRequest(pUsername, pPassword,0,2));
			getGameWindow().showMenu();
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
	 * @param pMessage message oject coming from the server
	 * @param userName a userName
	 * @param password a password
	 */
	
	// Sends a SignInRequest to the server
	public void serverSignInRequest(String pUsername, String pPassword) {
		System.out.println("METHOD Engine.serverSignInRequest() " + pUsername + ", " + pPassword);

		this.sendToServer(new MessSignInAndUpRequest(pUsername, pPassword,0,4));
	}

	/**
	 * This method processes a SignInAndUpAnswer message coming from the server
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
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
//			this.myPlayer.ID = message.getPlayerID();

			// opens the game menu
			getGameWindow().showMenu();
			System.out.println("login successful");
		}else{
			JOptionPane.showMessageDialog(null, "Wrong Username or Password");
		}
	}


	// ********** TYPE = 1 : USER TRIGGERED ACTIONS AND METHODS **********
	
	// Sends a moveCharacterRequest to the server
	public void moveCharacterRequest(int x, int y,int direction) {
		
	this.sendToServer(new MessMoveCharacterRequest(x,y,direction, 1, 0));
	System.out.println("METHOD Engine.MoveCharacterRequest: MovementRequest sent!");
	} 
	
	// Processes a moveCharacterAnswer Message coming from the server
	private void moveCharacterAnswer(Message pMessage) {	
		System.out.println("METHOD ClientEngine.moveCharacterAnswer: Message came from server");
		
			// Casts the message
			MessMoveCharacterAnswer message = (MessMoveCharacterAnswer) pMessage;
			
			// Checks if the boolean in message is true
			if(message.isConfirmed()) {
			System.out.println("METHOD ClientEngine.moveCharacterAnswer: Movement allowed");
			
			// Sets new position of the player
			myPlayer.xPos = message.getX();
			myPlayer.yPos = message.getY();
		
			} else {
				System.out.println("METHOD ClientEngine.moveCharacterAnswer: Movement NOT allowed");
			}
			
		}
	
	// Sends an attackRequest to the server
	public void attackRequest(boolean attack) {
//		this.sendToServer(new MessAttackRequest(attack,1,2));
	}
	
	// Processes an attackAnswer Message coming from the server
	public void attackAnswer(Message pMessage) {
		MessAttackAnswer message = (MessAttackAnswer) pMessage;
		
		if (message.isConfirmed()) {
			myMonster.health = message.getHealth();
		}
		
		if (message.isKilled()) {
			// still to do
		}
	}
	
	
	
	public void collectItemRequest() {
		
		this.sendToServer(new MessCollectItemRequest(1,4));
		System.out.println("METHOD Engine.collectItemRequest: CollectItemRequest sent! ");
	}
	
	public void collectItemAnswer(Message pMessage) {
		System.out.println("METHOD Engine.collectItemAnswer: collectItemAnswer received!");
		
		MessCollectItemAnswer message = (MessCollectItemAnswer) pMessage;
		if (message.getID() == 0) {
			this.getMyPlayer().takeKey();
		} else if (message.getID() == 1) {
			this.getMyPlayer().takePotion();
					
		} else {
			System.out.println("METHOD ClientEngine.collectItemAnswer: No Item on the floor!");
		}
	}
//	
//	public void usePotionRequest(Message pMessage) {
//		System.out.println("METHOD Engine.usePotionRequest:" + pMessage.toString());
//	}
//	
//	public void usePotionAnswer(Message pMessage) {
//		System.out.println("METHOD Egnine.usePotionAnswer:" + pMessage.toString());
//	}
//	
//	public void openDoorRequest(Message pMessage) {
//		System.out.println("METHOD Egnine.openDoorRequest:" + pMessage.toString());
//	}
//	
//	public void openDoorAnswer(Message pMessage){
//		System.out.println("METHOD Egnine.openDoorAnswer:" + pMessage.toString());
//	}
//	
//	
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 */
	
	
	// ********** TYPE = 2 : WORLDMANAGEMENT TRIGGERED ACTIONS AND METHODS **********
	
	
	public void startGameRequest(int ID) {
		System.out.println("METHOD ClientEngine.startGameRequest: New game requested");
		this.sendToServer(new MessStartGameRequest(this.getPlayerID(),0,6));
	}
	
	// Sends a levelRequest to the server
	public void levelRequest() {
//		int l = 0;
//		this.sendToServer(new MessLevelRequest(0,2,0));
	}
	
	// Processes a levelAnswer Message coming from the server
	public void levelAnswer(Message pMessage) {
		System.out.println("METHOD Engine.levelAnswer: Level received! ");
		MessLevelAnswer message = (MessLevelAnswer) pMessage;
		
		this.labyrinth = message.getLabyrinth();
	
	}
	
	// Sends a playerRequest to the server
	public void playerRequest() {
		System.out.println("METHOD Engine.playerRequest: Player requested!" );
		this.sendToServer(new MessPlayerRequest(myPlayer,2,4));
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
	
	

	public void updateMonsterRequest(Message pMessage) {
		System.out.println("METHOD Egnine.updateMonserRequest:" + pMessage.toString());
	}
	
	public void updateMonsterAnswer(Message pMessage) {
		System.out.println("METHOD Egnine.updateMonserAnswer:" + pMessage.toString());
	}
	
	
	
	
	
	
	
	
	//***** Section 4 *****//
	
	/***
	 * @author Oliver Goetz, 596313
	 * 
	 * @param pMessage
	 * @param myPlayer
	 * @param myMonster
	 * @param networkHandler
	 * @param gamewindow
	 * 
	 * This block contains helper, getter and setter methods
	 */


	
	/**
	 * SECTION xx contains helper methods as well Getter and Setter
	 * 
	 * @author Oliver Goetz, 5961343
	 
	 */

	// ********* HELPERS and GETTERS'n'SETTERS **********
	
	/**
	 * This methods calls the newtorkhandler a puts a message in queue.
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
	 * @param myPlayer the player
	 */
	private void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}
	
	private Monster getMyMonster() {
		return myMonster;
	}
	
	private void setMyMonster(Monster myMonster) {
		this.myMonster= myMonster;
	}
	


	private ExecutorService getThreadPool() {
		return threadPool;
	}

	private void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	public HandlerClient getNetworkHandler() {
		return networkHandler;
	}

	public void setNetworkHandler(HandlerClient networkHandler) {
		this.networkHandler = networkHandler;
	}

	public GameWindow getGameWindow() {
		return gamewindow;
	}

	public void setGameWindow(GameWindow gamewindow) {
		this.gamewindow = gamewindow;
	}
	

	
	public Tile[][] getLabyrinth() {
		return labyrinth;
	}
	
	public int getPlayerID() {
		return playerID;
	}




	

	
//	private void setCharacter(Character myCharacter) {
//		this.myCharacter = myCharacter;
//	}
//	
	
} // end of engine-class