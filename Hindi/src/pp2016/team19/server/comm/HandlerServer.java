package pp2016.team19.server.comm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;

import pp2016.team19.shared.Message;

/**
 * <h1>Builds the connection for the Server and handles the receiving and the
 * transmitting of messages.</h1>
 * 
 * The HandlerServer Class is responsible for building the ServerSocket for the
 * Server-Side and connecting with the Client. This Class submits methods for
 * the Server-Engine in order to send and receive messages. Therefore it runs
 * the Threads ReceiverServer and TransmitterServer. The TimerTask is also run
 * for checking the connection between the Server and the Client.
 * 
 * @author Bulut , Taner , 5298261
 * 
 */
public class HandlerServer {

	private ServerSocket server;
	private Socket client;
	private Timer pingTimer;
	private ReceiverServer receiver;
	private TransmitterServer transmitter;
	private boolean connected;
	private boolean closeNetwork;
	private boolean connectedState1;
	private boolean connectedState2;

	/**
	 * Builds the ServerSocket for the Server-Side and starts the Threads for
	 * sending and receiving through ReceiverServer and TransmitterServer. Also
	 * listens to connections that want to be made to the ServerSocket. The
	 * TimerTask is also started for sending MessPing Messages to the Client.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public HandlerServer() {
		/*
		 * The instance of Timer 'pingTimer' is build and later executes the
		 * TimerTask-PingCheckServer within a certain interval
		 */
		pingTimer = new Timer();
		this.connectedState1 = true;
		this.connectedState2 = true;
		this.closeNetwork = false;

		try {
			server = new ServerSocket(44444);
			while (!connected) {
				// Listens for the connections that want to be made to this
				// ServerSocket
				client = server.accept();
				// Starts the Thread and the TimerTask, then leaves the loop
				if (client.isConnected()) {
					receiver = new ReceiverServer(client, this);
					transmitter = new TransmitterServer(client);
					receiver.start();
					transmitter.start();
					pingTimer.scheduleAtFixedRate(new PingCheckServer(this), 3000, 3000);
					connected = true;
				}

			}
		} catch (IOException e) {
			System.out.println("ERROR: HANDLERSERVER PORT NOT FOUND OR OCCUPIED");
			e.printStackTrace();
			System.exit(1);
		}

	}

	/**
	 * Closing the Socket, the TimerTask and stops the running of the
	 * application. This method allows to stop the connection between Server and
	 * Client.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public void close() {

		try {
			System.out.println("CLOSED: HandlerServer");
			this.setCloseNetwork(true);
			this.pingTimer.cancel();
			this.client.close();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("ERROR: HANDLERSERVER");
			e.printStackTrace();
		}
	}

	/**
	 * Returning the Socket 'client' that is connected to the ServerSocket
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the connected Socket of the Client-Side that is bound to the
	 *         ServerSocket
	 */
	public Socket getClient() {
		return this.client;
	}

	/**
	 * Returning the ServerSocket 'server' that is connected to the Client
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the ServerSocket of the Server
	 */
	public ServerSocket getServer() {
		return this.server;
	}

	/**
	 * Sends messages to the Client by calling the writeMessage(Message) method
	 * of the transmitter instance
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param message
	 *            defines the Message that is written into the
	 *            ObjectOutputStream and sent to the Client
	 */
	public void sendMessageToClient(Message message) {
		transmitter.writeMessage(message);
	}

	/**
	 * Returns the messages from the Client by calling the getMessage() method
	 * of the receiver instance
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the Message-object that is read from the ObjectInputStream
	 */
	public Message getMessageFromClient() {
		return receiver.getMessage();
	}

	/**
	 * The following methods are standard Get-/Set-Methods that do not need
	 * further explanation
	 * 
	 * @author Bulut , Taner , 5298261
	 * 
	 */

	/**
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param closeNetwork
	 *            the boolean variable sets the connection state of the Server
	 * 
	 */
	public void setCloseNetwork(boolean closeNetwork) {
		this.closeNetwork = closeNetwork;
	}

	/**
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the connection state of the Server
	 * 
	 */
	public boolean getCloseNetwork() {
		return this.closeNetwork;
	}

	/**
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param connectedState1
	 *            the boolean variable sets the connection state of the Server
	 *            for the first Connection-Check via PingCheck
	 * 
	 */
	public void setConnectedState1(boolean connectedState1) {
		this.connectedState1 = connectedState1;
	}

	/**
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the connection state of the Server regarding the first
	 *         Connection-Check via PingCheck
	 * 
	 */
	public boolean getConnectedState1() {
		return this.connectedState1;
	}

	/**
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param connectedState2
	 *            the boolean variable sets the connection state of the Server
	 *            for the second Connection-Check via PingCheck
	 * 
	 */
	public void setConnectedState2(boolean connectedState2) {
		this.connectedState2 = connectedState2;
	}

	/**
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the connection state of the Server regarding the second
	 *         Connection-Check via PingCheck
	 * 
	 */
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