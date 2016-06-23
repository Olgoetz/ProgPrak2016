package pp2016.team19.client.comm;

import java.io.IOException;
import java.net.SocketException;
import java.util.TimerTask;

import pp2016.team19.shared.MessPing;

/**
 * The NetworkPingCheckC Class builds the TimerTask for sending a Message within
 * a certain interval to the Server. The connection between Server and Client is
 * going to be checked by sending these Messages to the Server repetetively. In
 * case that the messages from the Server cannot be read, the NetworkPingCheckC
 * is going to close the connection to the Server after a certain time.
 * 
 * @author Bulut , Taner , 5298261
 */
public class NetworkPingCheckC extends TimerTask {

	private NetworkHandlerC networkHandler;
	private int pingIteration = 0;

	/**
	 * Initializes the instance of the NetworkHandlerC 'networkHandler'
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public NetworkPingCheckC(NetworkHandlerC networkHandler) {
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
			pingOne();
		} else if (!this.networkHandler.getConnectedState1() && this.networkHandler.getConnectedState2()) {
			pingTwo();
		} else if (!this.networkHandler.getConnectedState1() && !this.networkHandler.getConnectedState2()) {
			stopConnection();
		}
	}

	/**
	 * Sends a MessPing-Message to the Server. Sets the variable 'connectedState1' of
	 * the NetworkHandlerC to false, in order to check if the NetworkReceiverC
	 * reads a message from the InputStream and then changes the value of
	 * 'connectedState1' back to true. This means that the Connection between Server
	 * and Client is alive otherwise pingTwo() is started.Therefore pingOne() is
	 * the first attempt to check the reading of the InputStream.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void pingOne() {
		this.networkHandler.setCloseNetwork(false);
		this.networkHandler.setConnectedState1(false);
		this.networkHandler.sendMessageToServer(new MessPing(100, 0));
	}

	/**
	 * Sends a MessPing-Message to the Server. Sets the variable 'connectedState2' of
	 * the NetworkHandlerC to false, in order to check if the NetworkReceiverC
	 * reads a message from the InputStream and then changes the value of
	 * 'connectedState2' back to true. This means that the Connection between Server
	 * and Client is alive otherwise stopConnection() is started.Therefore
	 * pingTwo() is the last attempt to check the reading of the InputStream.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void pingTwo() {
		this.networkHandler.setCloseNetwork(false);
		this.networkHandler.setConnectedState1(false);
		this.networkHandler.setConnectedState2(false);
		this.networkHandler.sendMessageToServer(new MessPing(100, 0));
	}

	
	/**
	 * Closing the connection between the Server and the Client and closing the
	 * application. The TimerTask is also cancelled.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void stopConnection() {

		this.networkHandler.close("NetworkPingCheckC STOPTHREADS for Client after " + pingIteration + " Pings");
		this.networkHandler.setCloseNetwork(true);
		this.cancel();
		try {
			System.out.println(
					"Connection to Server lost (PINGCHECK)! \n\n Please insure, that the server was not stopped! \n Start the game again afterwards!");
			this.networkHandler.getServer().close();
		} catch (SocketException e) {
			System.out.println("CLIENT SOCKET CLOSED IN NETWORKPINGCHECKC");
		} catch (IOException e) {
			System.out.println("ERROR: NETWORKPINGCHECKC");
			System.out.println("Socket ERROR: NetworkHandlerC resulting in NetworkPingCheckC");
			e.printStackTrace();
		} finally {
			System.out.println("NETWORKPINGCHECKC closed the game!");
			System.exit(1);
		}
	}

}
