package pp2016.team19.server.comm;

import java.io.IOException;
import java.util.TimerTask;

import pp2016.team19.shared.MessPing;

/**
 * <h1>Checking the connection between Server and Client via Ping-Messages</h1>
 * 
 * The PingCheckServer Class builds the TimerTask for sending a Message within a
 * certain interval to the Client. The connection between Server and Client is
 * going to be checked by sending these Messages to the Client repetetively. In
 * case that the messages from the Client cannot be read, the PingCheckServer is
 * going to close the connection to the Client after a certain time.
 * 
 * @author Bulut , Taner , 5298261
 */
public class PingCheckServer extends TimerTask {

	private int pingIteration = 0;
	private HandlerServer networkHandler;

	/**
	 * Initializes the instance of the HandlerServer 'networkHandler'
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param networkHandler
	 *            defines the HandlerServer that executes the TimerTask
	 */
	public PingCheckServer(HandlerServer networkhandler) {
		this.networkHandler = networkhandler;
	}

	/**
	 * Runs the TimerTask that is going to be executed by a Timer within a
	 * certain interval. Sends a MessPing-Message to the Client. Closes the
	 * Sockets as soon as no messages can be read.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	@Override
	public void run() {
		pingIteration++;

		if (this.networkHandler.getConnectedState1() && this.networkHandler.getConnectedState2()) {
			pingOne();
		} else if (!this.networkHandler.getConnectedState1() && this.networkHandler.getConnectedState2()) {
			pingTwo();
		} else if (!this.networkHandler.getConnectedState1() && !this.networkHandler.getConnectedState2()) {
			stopConnection();
		}
	}

	/**
	 * Sends a MessPing-Message to the Server. Sets the variable
	 * 'connectedState2' of the HandlerServer to false, in order to check if the
	 * ReceiverServer reads a message from the InputStream.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void pingTwo() {
		this.networkHandler.setCloseNetwork(false);
		this.networkHandler.setConnectedState1(false);
		this.networkHandler.setConnectedState2(false);
		this.networkHandler.sendMessageToClient(new MessPing(100, 0));
	}

	/**
	 * Sends a MessPing-Message to the Server. Sets the variable
	 * 'connectedState1' of the HandlerServer to false, in order to check if the
	 * ReceiverServer reads a message from the InputStream.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void pingOne() {
		this.networkHandler.setCloseNetwork(false);
		this.networkHandler.setConnectedState1(false);
		this.networkHandler.sendMessageToClient(new MessPing(100, 0));
	}

	/**
	 * Closing the connection between the Server and the Client and closing the
	 * application. The TimerTask is also cancelled.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void stopConnection() {

		System.out.println("PingCheckServer STOPTHREADS for Server after " + pingIteration + " Pings");
		this.cancel();
		try {
			System.out.println(
					"Connection to Client lost (PINGCHECK)! \n\n Please insure, that the Client was not stopped! \n Start the game again afterwards!");
			this.networkHandler.getClient().close();
			System.out.println("PINGCHECKSERVER: CLIENT CLOSED");
			this.networkHandler.getServer().close();
			System.out.println("PINGCHECKSERVER: SERVER CLOSED");
		} catch (IOException e) {
			System.out.println("ERROR: PINGCHECKSERVER");
			System.out.println("Socket ERROR for HandlerServer resulting in PingCheckServer");
			e.printStackTrace();
		}
	}

}
