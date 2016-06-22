package pp2016.team19.client.comm;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import pp2016.team19.shared.Message;
import pp2016.team19.shared.ThreadWaitForMessage;

public class NetworkReceiverC extends Thread {

	private LinkedBlockingQueue<Message> messagesFromServer = new LinkedBlockingQueue<>();
	private Socket server;
	private ObjectInputStream in;
	private Message messageFS;

	public NetworkReceiverC(Socket server) {
		this.server = server;
	}

	private void receiveMessage() {
		try {
			in = new ObjectInputStream(new BufferedInputStream(server.getInputStream()));
			while(true){
				ThreadWaitForMessage.waitFor(100L);
				messageFS = (Message) in.readObject();
				if(messageFS != null){
					messagesFromServer.offer(messageFS);
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.receiveMessage();
	}
	
	public LinkedBlockingQueue<Message> getMessagesFromServer(){
		return messagesFromServer;
	}
	public Message getMessage(){
		return messagesFromServer.poll();
	}

}
