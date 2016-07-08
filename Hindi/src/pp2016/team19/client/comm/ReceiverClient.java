package pp2016.team19.client.comm;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

import pp2016.team19.shared.Message;
import pp2016.team19.shared.ThreadWaitForMessage;

/**
 * <h1>Builds the Thread for receiving Message-objects from the Server.</h1>
 * 
 * The ReceiverClient Class builds the Thread for receiving Messages from the
 * Server. The Thread allows to save the Data of the InputStream. The saved
 * messages are going to be gathered by a LinkedBlockingQueue and a loop allows
 * to read these messages from the InputStream repetetively and eventually save
 * the messages in a Queue.
 * 
 * @author Bulut , Taner , 5298261
 * 
 */
public class ReceiverClient extends Thread {

	private HandlerClient networkHandler;
	private LinkedBlockingQueue<Message> messagesFromServer = new LinkedBlockingQueue<>();
	private Socket server;
	private ObjectInputStream in;
	private Message messageFS;

	/**
	 * Initializes the Socket 'server' variable so that the InputStream can be
	 * operated assigned to the Socket and initializes the HandlerClient
	 * 'networkHandler'
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param server
	 *            defines the Socket for the Client-Side
	 * @param networkHandler
	 *            specifies the HandlerClient that starts the Thread and works
	 *            with the Client-Engine
	 */
	public ReceiverClient(Socket server, HandlerClient networkHandler) {
		this.server = server;
		this.networkHandler = networkHandler;
	}

	/**
	 * Builds an instance of ObjectInputStream that reads from the InputStream
	 * of the Client-Socket and saves the Data in a LinkedBlockingQueue. A
	 * while-loop is executing the operation consistently. And each time a
	 * message is read from the InputStream, the Thread is yielded before and
	 * waits for a certain time.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void receiveMessage() {
		try {

			// The Stream header is read from the InputStream that refers to the
			// appropriate InputStream. The ObjectInputStream is allowing to
			// save the arguments of the InputStream.
			in = new ObjectInputStream(server.getInputStream());
			while (true) {
				// The Thread is waiting before the messages can be read and
				// saved in the Queue by executing Thread.yield()
				ThreadWaitForMessage.waitFor(100L);
				// The read argument has to be parsed into a Message-Type
				messageFS = (Message) in.readObject();
				// System.out.println(messageFS.toString());

				// Sets the variables connectedState1 and connectedState2 to
				// true, so that the connection between the Server and the
				// Client is proofed. The PingCheckClient then tries the first
				// attempt again to check if the InputStream is readable.
				networkHandler.setConnectedState1(true);
				networkHandler.setConnectedState2(true);
				if (messageFS != null) {
					// Puts the read messages into the Queue messagesFromServer
					messagesFromServer.put(messageFS);
				}
			}
		} catch (EOFException e) {
			System.out.println("ERROR ObjectInputStream: RECEIVERCLIENT");
			e.printStackTrace();
		} catch (SocketException e) {
			//Closing the Connection between the Server and the Client
			this.networkHandler.close("Connection to the Server lost!");
			System.out.println("ERROR SocketException: RECEIVERCLIENT");
			e.printStackTrace();
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("InputStream is closed: RECEIVERCLIENT");
				//Closing the input stream
				this.in.close();
			} catch (IOException e) {
				System.out.println("ERROR: RECEIVERCLIENT");
				e.printStackTrace();
			}
			//Terminates the currently running Java Virtual Machine
			System.exit(1);
		}
	}

	/**
	 * Runs the Thread and executes the consistent receiving of the messages by
	 * the receiveMessage() method
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	@Override
	public void run() {
		this.receiveMessage();
	}

	/**
	 * Returns the LinkedBlockingQueue 'messagesFromServer'
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the LinkedBlockingQueue<Message> that gathers the Message-objects
	 *         from the Server
	 */
	public LinkedBlockingQueue<Message> getMessagesFromServer() {
		return messagesFromServer;
	}

	/**
	 * Returns the Message that is polled from the Queue 'messagesFromServer'
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the Message-object from the LinkedBlockingQueue
	 *         'messagesFromServer'
	 */
	public Message getMessage() {
		return messagesFromServer.poll();
	}

}
