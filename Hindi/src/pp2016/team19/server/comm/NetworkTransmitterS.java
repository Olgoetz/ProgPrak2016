package pp2016.team19.server.comm;

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
 * The NetworkTransmitterS Class builds the Thread for sending Messages from the
 * Server to the Client. The Thread allows to write Data into the existing
 * OutputStream. The messages are going to be collected through a
 * LinkedBlockingQueue and a loop allows to poll these messages from the Queue
 * repetetively and eventually write into the OutputStream.
 * 
 * @author Bulut , Taner , 5298261
 * 
 */
public class NetworkTransmitterS extends Thread {
	
	private Socket client;
	private ObjectOutputStream out;
	private LinkedBlockingQueue<Message> messagesToClient = new LinkedBlockingQueue<>();
	private Message messageTC;
	
	/**
	 * Initializes the Socket 'client' variable so that the OutputStream can be
	 * operated assigned to the Socket
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public NetworkTransmitterS(Socket client){
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
	private void transmitMessage(){
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
			while (true)
			{
				messageTC = messagesToClient.poll(100,TimeUnit.MILLISECONDS);
				if (messageTC != null && messageTC.getType()!=100)
				{
					out.writeObject(messageTC);
					out.flush();
					out.reset();
				}
			}
			} catch(SocketException e){

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
		this.transmitMessage();
	}
	
	/**
	 * Puts the messages to the LinekdBlockingQueue 'messagesToClient' so that
	 * they can be sent to the Client afterwards by the transmitMessage() method
	 * 
	 * @author Bulut , Taner , 5298261
	 */
	public void writeMessage(Message message){
		try {
			this.messagesToClient.put(message);
		} catch (InterruptedException e) {
			System.out.println("ERROR: NETWORKTRANSMITTERS.writeMessage(Message message)");
			e.printStackTrace();
		}
	}

}
