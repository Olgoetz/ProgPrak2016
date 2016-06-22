package pp2016.team19.client.engine;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


import pp2016.team19.client.*;
import pp2016.team19.client.comm.NetworkHandlerC;
import pp2016.team19.client.gui.GameWindow;
import pp2016.team19.client.gui.LoginBuild;
import pp2016.team19.server.map.Labyrinth;
import pp2016.team19.shared.*;




/***
 * 
 * 
 * @author Oliver Goetz, 5961343
 * 
 * Summary:
 * This class represents the client-engine together with all its variables and processing
 * methods. It consists of several sections:
 * 
 * Section 1:
 * 				- Constructor 
 * 				- A thread is initialized
 * 				
 * 
 * Section 2:
 * 				- messageReader-Method to analyze incoming messages
 * 				- several subsections with switch cases statements to call the appropriate method
 * 
 * Section 3:
 * 				- Request Methods to send information to the server
 * 				- Answer Methods to process messages from the sever
 * 
 * Section 4:
 * 				- Helper methods
 * 				- Getter and Setter methods
 * 				
 */


//***** Section 1 *****//

public class Engine implements Runnable {

//	private LinkedBlockingQueue<Message> messagesFromServer;
//	private LinkedBlockingQueue<Message> messagesToServer;
	private ExecutorService threadPool;

	private NetworkHandlerC networkHandler;
	private GameWindow gamewindow;
	private LoginBuild loginbuild;

	private int playerID;
	private Player myPlayer;
	private Monster myMonster;
	private int direction;
	private Labyrinth labyrinth;

	
	public static final int BOX = 32;
	public static final int WIDTH = 16, HEIGHT = 16;
	
	
	public Engine() {
		this.setNetworkHandler(new NetworkHandlerC());
//		this.setLoginBuild(new LoginBuild());
		this.setGameWindow(new GameWindow(this,BOX*WIDTH, BOX*HEIGHT, "Hindi Bones"));
		// here comes si
		this.levelRequest();
		this.playerRequest();
	}

//	public Engine(ExecutorService pThreadPool) {
//
//		this.setMessagesFromServer(new LinkedBlockingQueue<Message>());
//		this.setMessagesToServer(new LinkedBlockingQueue<Message>());
//		this.setThreadPool(pThreadPool);
//
//		this.setNetworkHandler(
//				new NetworkHandlerC(this.getThreadPool(), this.getMessagesFromServer(), this.getMessagesToServer()));
//		this.setGUI(new GameWindow(this, 16,16, "Hindi Bones"));
//
//		
//
//		this.setClientID(this.getNetworkHandler().getClientID());
//		this.setPlayerID(-1);
//		this.setMyPlayer(null);
//
//		this.getGUI().activateSignInUpFrame();
//		this.getGUI().getLoginFrame().newStatus("connected to server", Color.BLACK);
//	}

	
	/***
	 * @author Oliver Goetz, 5961343
	 * This method starts a thread.
	 */

	@Override
	public void run() {
		System.out.println("THREAD STARTED: Engine");

		while (true) {
			Message message = this.networkHandler.getMessageFromServer();
			if (message != null) {
			this.messageReader(message);
			}
		}
		// System.out.println("THREAD FINISHED: Engine");
	}

	
	

//***** Section 2 *****//
	
	
	/***
	 * @author Oliver Goetz, 5961343
	 * This method analyzes the messages coming from the server and 
	 * calls for the appropriate processing method.
	 */
	
	
	private void messageReader(Message pMessage) {
		System.out.println("METHOD Engine.messageReader() " + pMessage.toString());

		switch (pMessage.getType()) {

		// ********** TYPE = 0 >> SIGN{UP,IN,OUT,OFF} ACTIONS AND METHODS **********
		case 0:

			switch (pMessage.getSubType()) {
			case 3:
				this.serverSignUpAnswer(pMessage);
				break;

			case 5:
				this.serverSignInAnswer(pMessage);
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

			switch (pMessage.getSubType()) {
			case 1:
				this.moveCharacterAnswer(pMessage);
				break;

			case 3:
				this.attackAnswer(pMessage);
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
	
	
//
//	
//	/***
//	 * 
//	 * @author Oliver Goetz, 5961343
//	 * 
//	 * 
//	 * 
//	 * 
//	 * This section presents the corresponding methods for each received message.
//	 */
//
//	
	
//***** Section 3 *****//
	
	/***
	 * @author Oliver Goetz, 5961343
	 * @param pMessage
	 * @param userName
	 * @param password
	 * @param password2
	 * 
	 * In this section, methods are provided to process requests and answers
	 */
	
	// ********** TYPE = 0 >> SIGN{UP,IN} ACTIONS AND METHODS **********

	// Sends a SignUpRequest to the server
	public void serverSignUpRequest(String pUsername, String pPassword, String pPassword2) {
		System.out.println("METHOD Engine.serverSignUpRequest() " + pUsername + ", " + pPassword + ", " + pPassword2);

		if (pPassword.equals(pPassword2)) {
			this.sendToServer(new MessSignInAndUpRequest(pUsername, pPassword,0,2));
		//	this.GUI.getLoginFrame().newStatus("sign up requested", Color.BLACK);
		} else {
		//	this.GUI.getLoginFrame().newStatus("passwords not equal", Color.RED);
		}
	}

	// Processes a SignUpAnswer Message coming from the server
	private void serverSignUpAnswer(Message pMessage) {
		System.out.println("METHOD Engine.serverSignUpAnswer() " + pMessage.toString());

		MessSignInAndUpAnswer message = (MessSignInAndUpAnswer) pMessage;

		if (message.isConfirmed()) {
			
		//	this.getGUI().getLoginFrame().newStatus("sign up confirmed", Color.BLACK);

		//	this.serverPlayerRequest();
		//	this.GUI.getLoginFrame().newStatus("loading data", Color.BLACK);
		} else {
		//	this.GUI.getLoginFrame().newStatus("sign up denied", Color.RED);
		}
	}
	
	// Sends a SignInRequest to the server
	public void serverSignInRequest(String pUsername, String pPassword) {
		System.out.println("METHOD Engine.serverSignInRequest() " + pUsername + ", " + pPassword);

		this.sendToServer(new MessSignInAndUpRequest(pUsername, pPassword,0,4));
		//this.GUI.getLoginFrame().newStatus("sign in requested", Color.BLACK);
	}

	// Processes a SignInAnswer Message coming from the server
	private void serverSignInAnswer(Message pMessage) {
		System.out.println("METHOD Engine.serverSignInAnswer() " + pMessage.toString());

		MessSignInAndUpAnswer message = (MessSignInAndUpAnswer) pMessage;

		if (message.isConfirmed()) {
		//	this.setPlayerID(message.getPlayerID());
		//	this.GUI.getLoginFrame().newStatus("sign in confirmed", Color.BLACK);

		//	this.serverPlayerRequest();
		//	this.GUI.getLoginFrame().newStatus("loading data", Color.BLACK);
		} else {
		//	this.GUI.getLoginFrame().newStatus("sign in denied", Color.RED);
		}
	}

	

//	// ********** TYPE = 1 : TIME TRIGGERED ACTIONS AND METHODS **********
//	public void serverPlayerRequest() {
//		System.out.println("METHOD Engine.serverPlayerRequest()");
//		this.sendToServer(new MessPlayerReq(this.getPlayerID(), this.getClientID()));
//	}
//
//	private void serverPlayerAnswer(Message pMessage) {
//		System.out.println("METHOD Engine.serverPlayerAnswer() " + pMessage.toString());
//
//		MessPlayerAns message = (MessPlayerAns) pMessage;
//
//		if (message.getClientID() == this.getClientID() && this.getPlayerID() == message.getPlayerID()) {
//
//			this.setMyPlayer(message.getPlayer());
//			this.getGUI().activateGameFrame();
//			this.getGUI().getGameFrame().refresh();
//		}
//	}
	
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param direction
	 * @param confirmed
	 */


	// ********** TYPE = 1 : USER TRIGGERED ACTIONS AND METHODS **********
	
	// Sends a moveCharacterRequest to the server
	public void moveCharacterRequest(int direction, boolean confirmed) {
		
	this.sendToServer(new MessMoveCharacterRequest(direction, confirmed , 1, 0));
	System.out.println("Message sent");
	} 
	
	// Processes a moveCharacterAnswer Message coming from the server
	private void moveCharacterAnswer(Message pMessage) {	
		System.out.println("Message came from server");
			MessMoveCharacterAnswer message = (MessMoveCharacterAnswer) pMessage;
			if(message.isConfirmed()) {
			myPlayer.xPos = message.getX();
			myPlayer.yPos = message.getY();
		
			}
			
		}
	
	// Sends an attackRequest to the server
	public void attackRequest(boolean attack) {
		this.sendToServer(new MessAttackRequest(attack,0,2));
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
	
	
	
//	public void collectItemRequest(Message pMessage) {
//		System.out.println("METHOD Engine.collectItemRequest:" + pMessage.toString());
//	}
//	
//	public void collectItemAnswer(Message pMessage) {
//		System.out.println("METHOD Engine.collectItemAnswer:" + pMessage.toString());
//	}
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
	
	// Sends a levelRequest to the server
	public void levelRequest() {
		this.sendToServer(new MessLevelRequest(labyrinth,2,0));
	}
	
	// Processes a levelAnswer Message coming from the server
	public void levelAnswer(Message pMessage) {
		MessLevelAnswer message = (MessLevelAnswer) pMessage;
		
		this.labyrinth = message.getLabyrinth();
	}
	
	// Sends a playerRequest to the server
	public void playerRequest() {
		System.out.println("METHOD Engine.playerRequest: Player requested!" );
		this.sendToServer(new MessPlayerRequest(myPlayer,2,4));
	}
	
	// Processes an levelAnswer Message coming from the server
	public void playerAnswer(Message pMessage) {
		System.out.println("METHoD Engine.plyerAnswer:" + pMessage.toString());
		MessPlayerAnswer message = (MessPlayerAnswer) pMessage;
		myPlayer = message.getMyPlayer();	
		
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



	// ********* HELPERS and GETTERS'n'SETTERS **********
	
	// Calls the networkHandler a puts the message in a queue
	private void sendToServer(Message pMessage) {
		this.networkHandler.sendMessageToServer(pMessage);
	}


	public Player getMyPlayer() {
		return myPlayer;
	}

	private void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}
	
	private Monster getMyMonster() {
		return myMonster;
	}
	
	private void setMyMonster(Monster myMonster) {
		this.myMonster= myMonster;
	}
	

//	private LinkedBlockingQueue<Message> getMessagesFromServer() {
//		return messagesFromServer;
//	}
//
//	private void setMessagesFromServer(LinkedBlockingQueue<Message> messagesFromServer) {
//		this.messagesFromServer = messagesFromServer;
//	}
//
//	private LinkedBlockingQueue<Message> getMessagesToServer() {
//		return messagesToServer;
//	}
//
//	private void setMessagesToServer(LinkedBlockingQueue<Message> messagesToServer) {
//		this.messagesToServer = messagesToServer;
//	}

//	private ExecutorService getThreadPool() {
//		return threadPool;
//	}
//
//	private void setThreadPool(ExecutorService threadPool) {
//		this.threadPool = threadPool;
//	}
//
	public NetworkHandlerC getNetworkHandler() {
		return networkHandler;
	}

	public void setNetworkHandler(NetworkHandlerC networkHandler) {
		this.networkHandler = networkHandler;
	}

	public GameWindow getGameWindow() {
		return gamewindow;
	}

	public void setGameWindow(GameWindow gamewindow) {
		this.gamewindow = gamewindow;
	}
	
	public LoginBuild getLoginBuild() {
		return loginbuild;
	}
	
	public void setLoginBuild(LoginBuild loginbuild) {
		this.loginbuild = loginbuild;
	}
	
	

	
//	private void setCharacter(Character myCharacter) {
//		this.myCharacter = myCharacter;
//	}
//	
	
} // end of engine-class