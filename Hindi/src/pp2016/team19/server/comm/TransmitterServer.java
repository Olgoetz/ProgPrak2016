package pp2016.team19.server.comm;

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
 * <h1>Builds the Thread for transmitting Message-objects from the Server to the
 * Client.</h1>
 * 
 * The TransmitterServer Class builds the Thread for sending Messages from the
 * Server to the Client. The Thread allows to write Data into the existing
 * OutputStream. The Messages are going to be collected through a
 * LinkedBlockingQueue and a loop allows to poll these Messages from the Queue
 * repetetively and eventually write into the OutputStream.
 * 
 * @author Bulut , Taner , 5298261
 * 
 */
public class TransmitterServer extends Thread {

	private Socket client;
	private ObjectOutputStream out;
	private LinkedBlockingQueue<Message> messagesToClient = new LinkedBlockingQueue<>();
	private Message messageTC;

	/**
	 * Initializes the Socket 'client' variable so that the OutputStream can be
	 * operated assigned to the Socket
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param client
	 *            defines the Socket of the Client-Side
	 */
	public TransmitterServer(Socket client) {
		this.client = client;
	}

	/**
	 * Builds an instance of ObjectOutputStream and writes Messages into the
	 * ObjectOutputStream that are polled from the LinkedBlockingQueue in
	 * advance. A while-loop is executing the operation of polling these
	 * Messages and writing into the ObjectOutputStream repetetively. Each time
	 * a Message is polled from the Queue, the Thread waits for a certain time.
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	private void transmitMessage() {
		try {
			// The Stream header is written to the OutputStream and referring to
			// the appropriate OutputStream. The ObjectOutputStream is
			// allowing to write Data into the OutputStream.
			out = new ObjectOutputStream(client.getOutputStream());
			while (true) {
				// The Thread is waiting before the messages can be polled from
				// the Queue
				messageTC = messagesToClient.poll(100, TimeUnit.MILLISECONDS);
				if (messageTC != null) {
					// Writes the Message into the output stream
					out.writeObject(messageTC);
					// The OutputStream is flushed so that the Data is sent
					// entirely
					out.flush();
					// The OutputStream is reseted so the Stream has the state
					// of 'new ObjectOutputStream'
					out.reset();
				}
			}
		} catch (EOFException e) {
			System.out.println("ERROR ObjectOutputStream: TRANSMITTERSERVER");
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("ERROR SocketException: TRANSMITTERSERVER");
			e.printStackTrace();
		} catch (IOException | InterruptedException e) {
			System.out.println("ERROR: TRANSMITTERSERVER resulting in transmitMessage()");
			e.printStackTrace();
		} finally {
			try {
				System.out.println("OutputStream is closed: TRANSMITTERSERVER");
				// Closes the output stream
				this.out.close();
			} catch (IOException e) {
				System.out.println("ERROR: TRANSMITTERSERVER in transmitMessage()");
				e.printStackTrace();
			}
			// Terminates the currently running Java Virtual Machine
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
		this.transmitMessage();
	}

	/**
	 * Puts the messages to the LinekdBlockingQueue 'messagesToClient' so that
	 * they can be sent to the Client afterwards by the transmitMessage() method
	 * 
	 * @author Bulut , Taner , 5298261
	 * @param message
	 *            defines the Message-object that is saved in the
	 *            LinkedBlockingQueue and ultimately sent to the Client
	 */
	public void writeMessage(Message message) {
		try {
			// Puts the Message into the LinkedBlockingQueue 'messagesToClient'
			this.messagesToClient.put(message);
		} catch (InterruptedException e) {
			System.out.println("ERROR: TRANSMITTERSERVER.writeMessage(Message message)");
			e.printStackTrace();
		}
	}

}
