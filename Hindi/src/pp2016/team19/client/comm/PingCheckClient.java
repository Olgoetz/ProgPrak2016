package pp2016.team19.client.comm;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.util.TimerTask;

import pp2016.team19.shared.MessPing;

/**
 * <h1>Checking the connection between Client and Server via Ping-Messages</h1>
 * 
 * The PingCheckClient Class builds the TimerTask for sending a Message within a
 * certain interval to the Server. The connection between Server and Client is
 * going to be checked by sending these Messages to the Server repetetively. In
 * case that the messages from the Server cannot be read, the PingCheckClient is
 * going to close the connection to the Server after a certain time.
 * 
 * @author Bulut , Taner , 5298261
 */
public class PingCheckClient extends TimerTask {

	private HandlerClient networkHandler;
	private int pingIteration = 0;

	/**
	 * Initializes the instance of the HandlerClient 'networkHandler'
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param networkHandler
	 *            defines the HandlerClient that executes the TimerTask
	 */
	public PingCheckClient(HandlerClient networkHandler) {
		this.networkHandler = networkHandler;
	}

	/**
	 * Runs the TimerTask that is going to be executed by a Timer within a
	 * certain interval. Sends a MessPing-Message to the Server that responds by
	 * returning the message:'I am still here!'. Closes the Client-Socket after
	 * no messages can be read. Starts two attempts to test the reading of
	 * messages.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	@Override
	public void run() {
		pingIteration++;
		if (this.networkHandler.getConnectedState1() && this.networkHandler.getConnectedState2()) {
			// the pingOne() is sending a Ping-Message and checks the Connection as the first attempt
			pingOne();
		} else if (!this.networkHandler.getConnectedState1() && this.networkHandler.getConnectedState2()) {
			// the pingTwo() is sending a Ping-Message and checks the Connection as the second attempt
			pingTwo();
		} else if (!this.networkHandler.getConnectedState1() && !this.networkHandler.getConnectedState2()) {
			// Closing the connection between Server and Client after two attempts 
			stopConnection();
		}
	}

	/**
	 * Sends a MessPing-Message to the Server. Sets the variable
	 * 'connectedState1' of the HandlerClient to false, in order to check if the
	 * ReceiverClient reads a message from the InputStream and then changes the
	 * value of 'connectedState1' back to true. This means that the Connection
	 * between Server and Client is alive otherwise pingTwo() is started.
	 * Therefore pingOne() is the first attempt to check the reading of the
	 * InputStream.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void pingOne() {
		this.networkHandler.setCloseNetwork(false);
		// Sets the first connection state to false
		this.networkHandler.setConnectedState1(false);
		// Sends a Ping-Message
		this.networkHandler.sendMessageToServer(new MessPing(100, 0));
	}

	/**
	 * Sends a MessPing-Message to the Server. Sets the variable
	 * 'connectedState2' of the HandlerClient to false, in order to check if the
	 * ReceiverClient reads a message from the InputStream and then changes the
	 * value of 'connectedState2' back to true. This means that the Connection
	 * between Server and Client is alive otherwise stopConnection() is
	 * started.Therefore pingTwo() is the last attempt to check the reading of
	 * the InputStream.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void pingTwo() {
		this.networkHandler.setCloseNetwork(false);
		this.networkHandler.setConnectedState1(false);
		// Sets the second connection state to false
		this.networkHandler.setConnectedState2(false);
		// Sends a Ping-Message
		this.networkHandler.sendMessageToServer(new MessPing(100, 0));
	}

	/**
	 * Closing the connection between the Server and the Client and closing the
	 * application. The TimerTask is also cancelled.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void stopConnection() {
		// Closes the Sockets
		this.networkHandler.close("PingCheckClient STOPTHREADS for Client after " + pingIteration + " Pings");
		this.networkHandler.setCloseNetwork(true);
		// Cancels this TimerTask
		this.cancel();
		try {
			System.out.println(
					"Connection to Server lost (PINGCHECK)! \n\n Please insure, that the server was not stopped! \n Start the game again afterwards!");
			this.networkHandler.getServer().close();
		} catch (EOFException e) {
			System.out.println("CLIENT SOCKET CLOSED IN PINGCHECKCLIENT");
		} catch (SocketException e) {
			System.out.println("CLIENT SOCKET CLOSED IN PINGCHECKCLIENT");
		} catch (IOException e) {
			System.out.println("ERROR: PINGCHECKCLIENT");
			System.out.println("Socket ERROR for HANDLERCLIENT resulting in PingCheckClient");
			e.printStackTrace();
		} finally {
			System.out.println("PINGCHECKCLIENT closed the game!");
			// Terminates the currently running Java Virtual Machine
			System.exit(1);
		}
	}

}
