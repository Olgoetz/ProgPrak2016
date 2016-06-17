package client;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


import pp2016.team19.client.*;
import pp2016.team19.shared.*;
import pp2016.team19.shared.Character;


/***
 * 
 * 
 * @author Oliver Götz, 5961343
 * 
 * This class is the client engine. It contains methods to analyze the message type
 * as well as corresponding methods to process these messages.
 */


public class Engine implements Runnable {

	private LinkedBlockingQueue<Message> messagesFromServer;
	private LinkedBlockingQueue<Message> messagesToServer;
	private ExecutorService threadPool;

//	private NetworkHandlerC networkHandler;
//	private GUI GUI;

	private int clientID;
	private int playerID;
	private Player myPlayer;
	private Character myCharacter;

//	public Engine(ExecutorService pThreadPool) {
//
//		this.setMessagesFromServer(new LinkedBlockingQueue<Message>());
//		this.setMessagesToServer(new LinkedBlockingQueue<Message>());
//		this.setThreadPool(pThreadPool);
//
//		this.setNetworkHandler(
//				new NetworkHandlerC(this.getThreadPool(), this.getMessagesFromServer(), this.getMessagesToServer()));
//		this.setGUI(new GUI(this));
//
//		for (int i = 0; i < 100000000; i++) {
//
//		}
//
//		this.setClientID(this.getNetworkHandler().getClientID());
//		this.setPlayerID(-1);
//		this.setMyPlayer(null);
//
//		this.getGUI().activateSignInUpFrame();
//		this.getGUI().getLoginFrame().newStatus("connected to server", Color.BLACK);
//	}

	
	/***
	 * @author Oliver Götz, 596343
	 * This method starts a thread.
	 */

	@Override
	public void run() {
		System.out.println("THREAD STARTED: Engine");

		while (true) {
			try {
				Message message = this.messagesFromServer.poll(10, TimeUnit.MILLISECONDS);
				if (message != null) {
					this.messageReader(message);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// System.out.println("THREAD FINISHED: Engine");
	}

	
	/***
	 * @author Oliver Götz, 596343
	 * This method analyzes the messages and calls for the appropriate processing method.
	 */
	
	
	private void messageReader(Message pMessage) {
		System.out.println("METHOD Engine.distributor() " + pMessage.toString());

		switch (pMessage.getType()) {

		// ********** TYPE = 0 >> SIGN{UP,IN,OUT,OFF} ACTIONS AND METHODS **********
		case 0:

			switch (pMessage.getSubType()) {
			case 1:
				this.serverSignUpAnswer(pMessage);
				break;

			case 3:
				this.serverSignInAnswer(pMessage);
				break;

			case 5:
				this.serverSignOutAnswer(pMessage);
				break;

			case 7:
				this.serverSignOffAnswer(pMessage);
				break;

			default:
				break;
			}

			break;

		// ********** TYPE = 1 : TIME TRIGGERED ACTIONS AND METHODS **********
		case 1:

			switch (pMessage.getSubType()) {
			case 0:
				this.serverPlayerAnswer(pMessage);
				break;

			default:
				break;
			}

			break;

		// ********** TYPE = 2 : USER TRIGGERED ACTIONS AND METHODS **********
		case 2:

			switch (pMessage.getSubType()) {
			case 0:
				this.characterMoveUp(pMessage);
				break;

			case 1:

				break;

			default:
				break;
			}

			break;
			
			
		// ********** TYPE = xx : USER TRIGGERED ACTIONS AND METHODS **********

		// ********** TYPE = 100 : TECHNICAL ACTIONS AND METHODS **********
		case 100:
			switch (pMessage.getSubType()) {
			case 10:
				this.serverGetClientID(pMessage);
				break;

			default:
				break;
			}
		default:
			break;
		}
	}

	
	/***
	 * 
	 * @author Oliver Götz, 596343
	 * 
	 * @param userName
	 * @param password
	 * @param password2
	 * 
	 * 
	 * 
	 * This section presents the corresponding methods for each received message.
	 */

	
	// ********** TYPE = 0 >> SIGN{UP,IN,OUT,OFF} ACTIONS AND METHODS **********

	public void serverSignUpRequest(String pUsername, String pPassword, String pPassword2) {
		System.out.println("METHOD Engine.serverSignUpRequest() " + pUsername + ", " + pPassword + ", " + pPassword2);

		if (pPassword.equals(pPassword2)) {
			this.sendToServer(new MessSignInUpReq(0, pUsername, pPassword));
			this.GUI.getLoginFrame().newStatus("sign up requested", Color.BLACK);
		} else {
			this.GUI.getLoginFrame().newStatus("passwords not equal", Color.RED);
		}
	}

	private void serverSignUpAnswer(Message pMessage) {
		System.out.println("METHOD Engine.serverSignUpAnswer() " + pMessage.toString());

		MessSignInUpAns message = (MessSignInUpAns) pMessage;

		if (message.isConfirmed()) {
			this.setPlayerID(message.getPlayerID());
			this.getGUI().getLoginFrame().newStatus("sign up confirmed", Color.BLACK);

			this.serverPlayerRequest();
			this.GUI.getLoginFrame().newStatus("loading data", Color.BLACK);
		} else {
			this.GUI.getLoginFrame().newStatus("sign up denied", Color.RED);
		}
	}

	public void serverSignInRequest(String pUsername, String pPassword) {
		System.out.println("METHOD Engine.serverSignInRequest() " + pUsername + ", " + pPassword);

		this.sendToServer(new MessSignInUpReq(2, pUsername, pPassword));
		this.GUI.getLoginFrame().newStatus("sign in requested", Color.BLACK);
	}

	private void serverSignInAnswer(Message pMessage) {
		System.out.println("METHOD Engine.serverSignInAnswer() " + pMessage.toString());

		MessSignInUpAns message = (MessSignInUpAns) pMessage;

		if (message.isConfirmed()) {
			this.setPlayerID(message.getPlayerID());
			this.GUI.getLoginFrame().newStatus("sign in confirmed", Color.BLACK);

			this.serverPlayerRequest();
			this.GUI.getLoginFrame().newStatus("loading data", Color.BLACK);
		} else {
			this.GUI.getLoginFrame().newStatus("sign in denied", Color.RED);
		}
	}

//	public void serverSignOutRequest() {
//		System.out.println("METHOD Engine.serverSignOutRequest()");
//
//		MessSignOutReq message = new MessSignOutReq(this.getPlayerID(), this.getClientID(), 0, 4);
//		this.sendToServer(message);
//
//	}
//
//	private void serverSignOutAnswer(Message pMessage) {
//		System.out.println("METHOD Engine.serverSignOutAnswer() " + pMessage.toString());
//
//		this.getGUI().activateSignInUpFrame();
//		this.getGUI().getLoginFrame().newStatus("signed out", Color.BLACK);
//
//	}
//
//	public void serverSignOffRequest() {
//		System.out.println("METHOD Engine.serverSignOffRequest()");
//
//		// still to do
////	}
//
//	private void serverSignOffAnswer(Message pMessage) {
//		System.out.println("METHOD Engine.serverSignOffAnswer() " + pMessage.toString());
//
//		// still to do
//
//	}

	// ********** TYPE = 1 : TIME TRIGGERED ACTIONS AND METHODS **********
	public void serverPlayerRequest() {
		System.out.println("METHOD Engine.serverPlayerRequest()");
		this.sendToServer(new MessPlayerReq(this.getPlayerID(), this.getClientID()));
	}

	private void serverPlayerAnswer(Message pMessage) {
		System.out.println("METHOD Engine.serverPlayerAnswer() " + pMessage.toString());

		MessPlayerAns message = (MessPlayerAns) pMessage;

		if (message.getClientID() == this.getClientID() && this.getPlayerID() == message.getPlayerID()) {

			this.setMyPlayer(message.getPlayer());
			this.getGUI().activateGameFrame();
			this.getGUI().getGameFrame().refresh();
		}
	}
	
	
	private void characterMovement(Message pMessage) {
		
	//	this.sendToServer(new characterMovementReq(this.getPlayer(), this.getx);
		
		
	} // end of caracterMoveUp-method
	
	private void characterMovementAnswer(Message pMessage) {
		
		this.myCharacter.getXPos();
		this.myCharacter.getYPos();
	}
	

	// ********** TYPE = 2 : USER TRIGGERED ACTIONS AND METHODS **********
	public void serverDoSomethingRequest() {
		System.out.println("METHOD Engine.serverDoSomethingRequest()");
	}

	private void serverDoSomethingAnswer(Message pMessage) {
		System.out.println("METHOD Engine.serverDoSomethingAnswer() " + pMessage.toString());
	}

	
	
	
	/***
	 * @author Oliver Götz, 596313
	 * 
	 * @param pMessage
	 * 
	 * This block contains helper, getter and setter methods
	 */



	// ********* HELPERS and GETTERS'n'SETTERS **********
	private void sendToServer(Message pMessage) {
		try {
			this.messagesToServer.put(pMessage);
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
	}

	private int getClientID() {
		return clientID;
	}

	private void setClientID(int clientID) {
		this.clientID = clientID;
	}

	private int getPlayerID() {
		return playerID;
	}

	private void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public Player getMyPlayer() {
		return myPlayer;
	}

	private void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}

	private LinkedBlockingQueue<Message> getMessagesFromServer() {
		return messagesFromServer;
	}

	private void setMessagesFromServer(LinkedBlockingQueue<Message> messagesFromServer) {
		this.messagesFromServer = messagesFromServer;
	}

	private LinkedBlockingQueue<Message> getMessagesToServer() {
		return messagesToServer;
	}

	private void setMessagesToServer(LinkedBlockingQueue<Message> messagesToServer) {
		this.messagesToServer = messagesToServer;
	}

	private ExecutorService getThreadPool() {
		return threadPool;
	}

	private void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

//	private NetworkHandlerC getNetworkHandler() {
//		return networkHandler;
//	}
//
//	private void setNetworkHandler(NetworkHandlerC networkHandler) {
//		this.networkHandler = networkHandler;
//	}
//
//	private GUI getGUI() {
//		return GUI;
//	}
//
//	private void setGUI(GUI gUI) {
//		GUI = gUI;
//	}
	
	
} // end of engine-class