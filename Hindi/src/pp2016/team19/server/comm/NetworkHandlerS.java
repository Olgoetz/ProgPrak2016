package pp2016.team19.server.comm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;

import pp2016.team19.shared.Message;

/**
 * The NetworkHandlerS Class is responsible for building the ServerSocket for
 * the Server-Side and connecting with the Client. This Class submits methods
 * for the Server-Engine in order to send and receive messages. Therefore it
 * runs the Threads NetworkReceiverS and NetworkTransmitterS. The TimerTask is
 * also run for checking the connection between the Server and the Client.
 * 
 * @author Bulut , Taner , 5298261
 * 
 */
public class NetworkHandlerS {

	private ServerSocket server;
	private Socket client;
	private Timer pingTimer;
	private NetworkReceiverS receiver;
	private NetworkTransmitterS transmitter;
	private boolean connected;
	private boolean closeNetwork;
	private boolean connectedState1;
	private boolean connectedState2;

	/**
	 * Builds the ServerSocket for the Server-Side and starts the Threads for
	 * sending and receiving through NetworkReceiverS and NetworkTransmitterS.
	 * Also listens to connections that want to be made to the ServerSocket. The
	 * TimerTask is also started for sending MessPing messages to the Client.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public NetworkHandlerS() {
		/*
		 * The instance of Timer 'pingTimer' is build and later executes the
		 * TimerTask-NetworkPingCheckS within a certain interval
		 */
		pingTimer = new Timer();
		this.connectedState1=true;
		this.connectedState2=true;
		this.closeNetwork=false;
		
		try {
			server = new ServerSocket(44444);
			while (!connected) {
				// Listens for the connections that want to be made to this
				// ServerSocket
				client = server.accept();
				// Starts the Thread and the TimerTask, then leaves the loop
				if (client.isConnected()) {
					receiver = new NetworkReceiverS(client,this);
					transmitter = new NetworkTransmitterS(client);
					receiver.start();
					transmitter.start();
					pingTimer.scheduleAtFixedRate(new NetworkPingCheckS(this), 3000, 3000);
					connected = true;
				}

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	/**
	 * Closing the Socket, the TimerTask and stops the running of the
	 * application. This method allows to stop the connection between Server and
	 * Client.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public void close(){

		try {
			System.out.println("CLOSED: NetworkHandlerS");
			this.setCloseNetwork(true);
			this.pingTimer.cancel();
			this.client.close();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("ERROR: NETWORKHANDLERS");
			e.printStackTrace();
		}
	}

	/**
	 * Returning the Socket 'client' that is connected to this ServerSocket
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public Socket getClient() {
		return this.client;
	}

	/**
	 * Returning the ServerSocket 'server' that is connected to the Client
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public ServerSocket getServer() {
		return this.server;
	}

	/**
	 * Sends messages to the Client by calling the writeMessage(Message) method
	 * of the transmitter instance
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public void sendMessageToClient(Message message) {
		transmitter.writeMessage(message);
	}

	/**
	 * Returns the messages from the Client by calling the getMessage() method
	 * of the receiver instance
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public Message getMessageFromClient() {
		return receiver.getMessage();
	}
	
	/**
	 * The following methods are standard Get-/Set-Methods that do not need further explanation
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public void setCloseNetwork(boolean closeNetwork) {
		this.closeNetwork = closeNetwork;
	}

	public boolean getCloseNetwork() {
		return this.closeNetwork;
	}

	public void setConnectedState1(boolean connectedState1) {
		this.connectedState1 = connectedState1;
	}

	public boolean getConnectedState1() {
		return this.connectedState1;
	}

	public void setConnectedState2(boolean connectedState2) {
		this.connectedState2 = connectedState2;
	}

	public boolean getConnectedState2() {
		return this.connectedState2;
	}

	// For Testing
	public void addMessage(Message message) { 
		try {
			receiver.messagesFromClient.put(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}