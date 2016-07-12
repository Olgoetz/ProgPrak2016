package pp2016.team19.server.comm;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

import pp2016.team19.shared.Message;
import pp2016.team19.shared.ThreadWaitForMessage;

/**
 * <h1>Builds the Thread for receiving Message-objects from the Client.</h1>
 * 
 * The ReceiverServer Class builds the Thread for receiving Messages from the
 * Client. The Thread allows to save the Data of the InputStream. The saved
 * messages are going to be gathered by a LinkedBlockingQueue and a loop allows
 * to read these messages from the InputStream repetetively and eventually save
 * the messages in a Queue.
 * 
 * @author Bulut , Taner , 5298261
 * 
 */
public class ReceiverServer extends Thread {

	// For setting the connection state
	private HandlerServer networkHandler;
	// For getting the input stream
	private Socket client;
	// For reading Message-Objects from the input stream
	private ObjectInputStream in;
	// Gathers the Messages from the Client 
	LinkedBlockingQueue<Message> messagesFromClient = new LinkedBlockingQueue<>();
	// Saving the received Message-Objects
	private Message messageFC;

	/**
	 * Initializes the Socket 'client' variable so that the InputStream can be
	 * operated assigned to the Socket and initializes the HandlerServer
	 * 'networkHandler'
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param client
	 *            defines the Socket from the Client-Side
	 * @param networkHandler
	 *            specifies the HandlerServer that starts the Thread and works
	 *            with the Server-Engine
	 */
	public ReceiverServer(Socket client, HandlerServer networkHandler) {
		this.client = client;
		this.networkHandler = networkHandler;
	}

	/**
	 * Builds an instance of ObjectInputStream that reads from the InputStream
	 * and saves the Data in a LinkedBlockingQueue. A while-loop is executing
	 * the operation consistently. And each time a Message is read from the
	 * InputStream, the Thread is yielded before and waits for a certain time.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void receiveMessage() {
		try {
			in = new ObjectInputStream(client.getInputStream());
			while (true) {
				// The Thread is waiting before the messages can be read and
				// saved in the Queue by executing Thread.yield()
				ThreadWaitForMessage.waitFor(100L);
				// Reads the Message from the input stream
				messageFC = (Message) in.readObject();
				this.networkHandler.setConnectedState1(true);
				this.networkHandler.setConnectedState2(true);
				// System.out.println(messageFC.toString());
				if (messageFC != null) {
					// Puts the Message into the LinkedBlockingQueue
					// 'messagesFromClient'
					messagesFromClient.put(messageFC);
				}
			}
		} catch (EOFException e) {
			System.out.println("ERROR ObjectInputStream: RECEIVERSERVER");
			e.printStackTrace();
		} catch (SocketException e) {
			// Closes Connection between Server and Client
			this.networkHandler.close();
			System.out.println("ERROR SocketException: RECEIVERSERVER");
			e.printStackTrace();
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			System.out.println("readObject() ERROR in RECEIVERSERVER.receiveMessage()");
			e.printStackTrace();
		} finally {
			// try {
			// System.out.println("InputStream is closed: RECEIVERSERVER");
			// //Closes the input stream
			// this.in.close();
			// } catch (IOException e) {
			// System.out.println("ERROR: RECEIVERSERVER");
			// e.printStackTrace();
			// }
			// Terminates the currently running Java Virtual Machine
			// System.exit(1);
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
	 * Returns the LinkedBlockingQueue 'messagesFromClient'
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the LinkedBlockingQueue<Message> that gathers the Message-objects
	 *         from the Client
	 */
	public LinkedBlockingQueue<Message> getMessagesFromClient() {
		return messagesFromClient;
	}

	/**
	 * Returns the Message that is polled from the Queue 'messagesFromClient'
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the Message-object from the LinkedBlockingQueue
	 *         'messagesFromClient'
	 */
	public Message getMessage() {
		return messagesFromClient.poll();
	}
}
