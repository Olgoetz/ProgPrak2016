package pp2016.team19.client.comm;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JOptionPane;

import pp2016.team19.shared.Message;

/**
 * The NetworkHandlerC Class is responsible for building the Socket for the
 * Client-Side and connecting with the Server. This Class submits methods for
 * the Client-Engine in order to send and receive messages. Therefore it runs
 * the Threads NetworkReceiverC and NetworkTransmitterC. The TimerTask is also
 * run for sending the MessPing-Messages in a defined period of time to the
 * Server.
 * 
 * @author Bulut , Taner , 5298261
 * 
 */

public class NetworkHandlerC {

	public LinkedBlockingQueue<Message> outputQueue = new LinkedBlockingQueue<>();
	private Socket server;
	private NetworkReceiverC receiver;
	private NetworkTransmitterC transmitter;
	private Timer pingTimer;
	private boolean closeNetwork;
	private boolean connectedState1;
	private boolean connectedState2;


	/**
	 * Builds the Socket for the Client-Side and starts the Threads for sending
	 * and receiving through NetworkReceiverC and NetworkTransmitterC. The
	 * TimerTask is also started for sending MessPing messages to the server.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public NetworkHandlerC() {
		this.pingTimer = new Timer();
		this.closeNetwork = false;
		while (this.server == null) {
			try {
				// this.server = new Socket("62.143.243.85", 33333);
				System.out.println("NetworkHandlerClient.NetworkHandlerC()");
				this.server = new Socket("localhost", 44444);
				this.connectedState1 = true;
				this.connectedState2 = true;
			} catch (UnknownHostException e) {
				System.out.println("ERROR: NetworkHandlerClient");
				e.printStackTrace();
			} catch (IOException e) {
				// Handles the failed attempt to connect with the Server
				System.out.println("ERROR: >>>>>>>>>>NetworkHandlerClient SERVER UNREACHABLE<<<<<<<<<<");
				e.printStackTrace();
				System.out.println(
						"Connection server cannot be built! \n\n Please check : \n 1. The game-server is started? \n 2. The client follows the appropriate serveraddress? \n 3. The server-port and the client-port do match? \n\n Start the game again afterwards! \n Connection-Error!");
				System.exit(0);
			}
		}
		startComponents();
	}

	/**
	 * Starting the Threads and the TimerTask.Initializing the NetworkReceiverC
	 * and NetworkTransmitterC instances and starts the Threads for sending and
	 * receiving messages. The TimerTask is also started for sending MessPing
	 * messages to the server.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void startComponents() {
		System.out.println("NetworkHandlerClient.startComponents()");
		transmitter = new NetworkTransmitterC(server);
		receiver = new NetworkReceiverC(server, this);
		transmitter.start();
		receiver.start();
		/*
		 * The Timer 'pingTimer' executes the TimerTask-NetworkPingCheckC within
		 * a certain interval
		 */
		this.pingTimer.scheduleAtFixedRate(new NetworkPingCheckC(this), 5000, 5000);
	}

	/**
	 * Closing the Socket, the TimerTask and stops the running of the
	 * application. This method allows to stop the connection between Server and
	 * Client. Closes the Socket of the Client.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public void close(String errorMessage) {
		try {
			System.out.println("CLOSED: NetworkHandlerC");
			this.pingTimer.cancel();
			this.getServer().close();
			System.out.println(errorMessage);
			System.exit(1);
		} catch (IOException e) {
			System.out.println("ERROR: NETWORKHANDLERC.close(String errorMessage)");
			e.printStackTrace();
		}
	}

	/**
	 * Sending messages to the Server through the NetworkTransmitterC-Thread
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public void sendMessageToServer(Message message) {
		System.out.println("NetworkHandlerClient.sendMessageToServer()");
		transmitter.writeMessage(message);
	}

	/**
	 * Returning messages from the Server through the NetworkReceiverC-Thread
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public Message getMessageFromServer() {
		return receiver.getMessage();
	}

	/**
	 * Returning the LinkedBlockingQueue that collects the messages that are
	 * going to be sent to the Server through the NetworkTransmitterC-Thread.
	 * Allows an efficient testing of the sending operation
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public LinkedBlockingQueue<Message> getOutputQueue() {
		return this.outputQueue = transmitter.getQueueMessagesToServer();
	}

	/**
	 * Returning the instance of NetworkTransmitterC
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public NetworkTransmitterC getTransmitterC() {
		return this.transmitter;
	}

	/**
	 * Returning the instance of NetworkReceiverC
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public NetworkReceiverC getReceiverC() {
		return this.receiver;
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

	public Socket getServer() {
		return this.server;
	}

	public void setServer(Socket server) {
		this.server = server;
	}

}