package pp2016.team19.client.engine;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import pp2016.team19.client.*;
import pp2016.team19.client.comm.NetworkHandlerC;
import pp2016.team19.client.gui.GameWindow;
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

public class ClientEngine implements Runnable   {

//	private LinkedBlockingQueue<Message> messagesFromServer;
//	private LinkedBlockingQueue<Message> messagesToServer;
	private ExecutorService threadPool;

	private NetworkHandlerC networkHandler;
	private GameWindow gamewindow;
	

	private int playerID;
	private Player myPlayer = new Player();
	private Monster myMonster;
	private int direction;
	public Tile[][] labyrinth;
	private Labyrinth test = new Labyrinth();
	
	public static final int BOX = 32;
	public static final int WIDTH = 16, HEIGHT = 16;
	
	
	public ClientEngine(ExecutorService clientThreadPool) {
		System.out.println("Start Constructor");
		this.setThreadPool(clientThreadPool);
		this.setNetworkHandler(new NetworkHandlerC());

		
		// here comes si
//		this.levelRequest();
//		this.playerRequest();

		this.setGameWindow(new GameWindow(this,BOX*WIDTH, BOX*HEIGHT, "Hindi Bones"));
		this.getThreadPool().execute(this.getGameWindow());
		
	}
	
	
	/***
	 * @author Oliver Goetz, 5961343
	 * This method starts a thread.
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
			Message message = this.getNetworkHandler().getMessageFromServer();
			if (message != null) {
				this.messageReader(message);
			}		
		}
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

			switch (pMessage.getSubType()) {
			case 1:
				this.moveCharacterAnswer(pMessage);
				break;

			case 3:
				this.attackAnswer(pMessage);
				break;
				
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
			
		} else {
			System.out.println("login failed");
		}
	}

	
	// Sends a SignInRequest to the server
	public void serverSignInRequest(String pUsername, String pPassword) {
		System.out.println("METHOD Engine.serverSignInRequest() " + pUsername + ", " + pPassword);

		this.sendToServer(new MessSignInAndUpRequest(pUsername, pPassword,0,2));
	//	this.levelRequest();
	//	this.playerRequest();
		
	
	}

	// Processes a SignInAnswer Message coming from the server
	private void serverSignInAndUpAnswer(Message pMessage) {
		System.out.println("METHOD Engine.serverSignInAnswer() " + pMessage.toString());

		MessSignInAndUpAnswer message = (MessSignInAndUpAnswer) pMessage;
		System.out.println("Server SignInAnswer received");
		if (message.isConfirmed()) {
			System.out.println("Data correct");

	
			getGameWindow().showMenu();
			
			System.out.println("login successful");
		}else{
			JOptionPane.showMessageDialog(null, "Wrong Username or Password");
		}
	}

	

	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param direction
	 * @param confirmed
	 */


	// ********** TYPE = 1 : USER TRIGGERED ACTIONS AND METHODS **********
	
	// Sends a moveCharacterRequest to the server
	public void moveCharacterRequest(int direction, boolean confirmed) {
		
	this.sendToServer(new MessMoveCharacterRequest(direction, 1, 0));
//	System.out.println("Message sent");
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
//		int l = 0;
//		this.sendToServer(new MessLevelRequest(0,2,0));
	}
	
	// Processes a levelAnswer Message coming from the server
	public void levelAnswer(Message pMessage) {
		System.out.println("METHOD Engine.playerAnswer: Level received! ");
		MessLevelAnswer message = (MessLevelAnswer) pMessage;
		
		this.labyrinth = message.getLabyrinth();
		test.setGameMap(this.labyrinth);
		test.PaintTest(30);
	}
	
	// Sends a playerRequest to the server
	public void playerRequest() {
		System.out.println("METHOD Engine.playerRequest: Player requested!" );
		this.sendToServer(new MessPlayerRequest(myPlayer,2,4));
	}
	
	// Processes an levelAnswer Message coming from the server
	public void playerAnswer(Message pMessage) {
		System.out.println("METHOD Engine.playerAnswer:" + pMessage.toString());
		MessPlayerAnswer message = (MessPlayerAnswer) pMessage;
		myPlayer.xPos = message.getX();
		myPlayer.yPos = message.getY();
		System.out.println(getMyPlayer().getXPos());
		System.out.println(getMyPlayer().getYPos());
		
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

	private ExecutorService getThreadPool() {
		return threadPool;
	}

	private void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

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
	

	
	public Tile[][] getLabyrinth() {
		return labyrinth;
	}



	

	
//	private void setCharacter(Character myCharacter) {
//		this.myCharacter = myCharacter;
//	}
//	
	
} // end of engine-class