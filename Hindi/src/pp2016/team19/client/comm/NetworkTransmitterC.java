package pp2016.team19.client.comm;

import java.io.BufferedOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import pp2016.team19.shared.Message;
import pp2016.team19.shared.ThreadWaitForMessage;

/**
 * The NetworkTransmitterC Class builds the Thread for sending Messages from the
 * Client to the Server. The Thread allows to write Data into the existing
 * OutputStream. The messages are going to be collected through a
 * LinkedBlockingQueue and a loop allows to poll these messages from the Queue
 * repetetively and eventually write into the OutputStream.
 * 
 * @author Bulut , Taner , 5298261
 * 
 */
public class NetworkTransmitterC extends Thread {

	private Socket server;
	private ObjectOutputStream out;
	private LinkedBlockingQueue<Message> messagesToServer = new LinkedBlockingQueue<>();
	private Message messageTS;

	/**
	 * Initializes the Socket 'server' variable so that the OutputStream can be
	 * operated assigned to the Socket
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public NetworkTransmitterC(Socket server) {
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
			out = new ObjectOutputStream(new BufferedOutputStream(server.getOutputStream()));
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
		} catch (SocketException e) {

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 */
	public void writeMessage(Message message) {
		try {
			this.messagesToServer.put(message);
		} catch (InterruptedException e) {
			System.out.println("ERROR: NETWORKTRANSMITTERC.writeMessage(Message message)");
			e.printStackTrace();
		}
	}

	/**
	 * Returns the LinkedBlockingQueue 'messagesToServer' that gathers the
	 * messages that are going to be sent to the Server
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public LinkedBlockingQueue<Message> getQueueMessagesToServer() {
		return messagesToServer;
	}

}
