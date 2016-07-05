package pp2016.team19.client.comm;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import pp2016.team19.shared.Message;

/**
 * <h1>Builds the Thread for transmitting Message-objects from the Client to the
 * Server.</h1>
 * 
 * The TransmitterClient Class builds the Thread for sending Messages from the
 * Client to the Server. The Thread allows to write Data into the existing
 * OutputStream. The messages are going to be collected through a
 * LinkedBlockingQueue and a loop allows to poll these messages from the Queue
 * repetetively and eventually write into the OutputStream.
 * 
 * @author Bulut , Taner , 5298261
 * 
 */
public class TransmitterClient extends Thread {

	private Socket server;
	private ObjectOutputStream out;
	private LinkedBlockingQueue<Message> messagesToServer = new LinkedBlockingQueue<>();
	private Message messageTS;

	/**
	 * Initializes the Socket 'server' variable so that the OutputStream can be
	 * operated assigned to the Socket
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param server
	 *            defines the Socket for the Client-Side
	 */
	public TransmitterClient(Socket server) {
		this.server = server;
	}

	/**
	 * Builds an instance of ObjectOutputStream that is referring to the
	 * OutputStream of the Client-Socket and writes Messages into the
	 * ObjectOutputStream that are polled from the LinkedBlockingQueue in
	 * advance. A while-loop is executing the operation of polling these
	 * Messages and writing into the ObjectOutputStream repetetively. Each time
	 * a Message is polled from the Queue, the Thread is yielded before and
	 * waits for a certain time.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void transmitMessage() {
		try {
			/*
			 * The Stream header is written to the OutputStream and referring to
			 * the appropriate OutputStream. The BufferedOutputStream is
			 * allowing to write Data into the OutputStream.
			 */
			out = new ObjectOutputStream(server.getOutputStream());
			while (true) {
				/*
				 * The Thread is waiting before the messages can be polled from
				 * the Queue
				 */
				messageTS = messagesToServer.poll(100, TimeUnit.MILLISECONDS);
				if (messageTS != null) {
					out.writeObject(messageTS);
					/*
					 * The OutputStream is flushed so that the Data is sent
					 * entirely
					 */
					out.flush();
					/*
					 * The OutputStream is reseted so the Stream has the state
					 * of 'new ObjectOutputStream'
					 */
					out.reset();
				}
			}
		} catch (EOFException e) {
			System.out.println("ERROR ObjectOutputStream: TRANSMITTERCLIENT");
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("ERROR SocketException: TRANSMITTERCLIENT");
			e.printStackTrace();
		} catch (IOException | InterruptedException e) {
			System.out.println("ERROR: TRANSMITTERCLIENT resulting in transmitMessage()");
			e.printStackTrace();
		} finally {
			try {
				System.out.println("OutputStream is closed: TRANSMITTERCLIENT");
				this.out.close();
			} catch (IOException e) {
				System.out.println("ERROR: TRANSMITTERCLIENT in transmitMessage()");
				e.printStackTrace();
			}
			System.exit(1);
		}
	}

	/**
	 * Runs the Thread and executes the the sending of the messages by the
	 * transmitMessage() method
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	@Override
	public void run() {
		transmitMessage();
	}

	/**
	 * Puts the messages to the LinekdBlockingQueue 'messagesToServer' so that
	 * they can be sent to the Server afterwards by the transmitMessage() method
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param message
	 *            defines the Message-object that is saved in the
	 *            LinkedBlockingQueue and ultimately sent to the Server
	 */
	public void writeMessage(Message message) {
		try {
			this.messagesToServer.put(message);
		} catch (InterruptedException e) {
			System.out.println("ERROR: TRANSMITTERCLIENT.writeMessage(Message message)");
			e.printStackTrace();
		}
	}

	/**
	 * Returns the LinkedBlockingQueue 'messagesToServer' that gathers the
	 * messages that are going to be sent to the Server
	 * 
	 * @author Bulut , Taner , 5298261
	 * @return the LinkedBlockingQueue<Message> that gathers the Message-objects
	 *         that are going to be sent to the Server 
	 */
	public LinkedBlockingQueue<Message> getQueueMessagesToServer() {
		return messagesToServer;
	}

}
