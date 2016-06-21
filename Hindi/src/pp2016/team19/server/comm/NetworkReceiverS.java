package pp2016.team19.server.comm;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import pp2016.team19.shared.Message;
import pp2016.team19.shared.ThreadWaitForMessage;

public class NetworkReceiverS extends Thread{

	private Socket client;
	private ObjectInputStream in;
	private LinkedBlockingQueue<Message> messagesFromClient = new LinkedBlockingQueue<>();
	private Message messageFC;
	
	public NetworkReceiverS(Socket client){
		this.client = client;
	}

	private void receiveMessage(){
		try {
			in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
			while(true){
				ThreadWaitForMessage.waitFor(100L);
				messageFC = (Message) in.readObject();
				if(messageFC != null){
					messagesFromClient.offer(messageFC);
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
	
	public LinkedBlockingQueue<Message> getMessagesFromClient(){
		return messagesFromClient;
	}
	public Message getMessage(){
		return messagesFromClient.poll();
	}
}
