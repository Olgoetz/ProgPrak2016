package pp2016.team19.client.comm;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import pp2016.team19.shared.Message;
import pp2016.team19.shared.ThreadWaitForMessage;

public class NetworkTransmitterC extends Thread {
	
	private Socket server;
	private ObjectOutputStream out;
	private LinkedBlockingQueue<Message> messagesToServer = new LinkedBlockingQueue<>();
	private Message messageTS;
	
	public NetworkTransmitterC(Socket server) {
		this.server = server;
	}
	
	private void transmitMessage(){
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(server.getOutputStream()));
			while (true)
			{
				ThreadWaitForMessage.waitFor(100L);
//				Thread.sleep(100);
				messageTS = messagesToServer.poll();
				if (messageTS != null)
				{
					out.writeObject(messageTS);
					out.flush();
					out.reset();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		transmitMessage();
	}
	
	public void writeMessage(Message message){
		this.messagesToServer.offer(message);
	}
	
	public LinkedBlockingQueue<Message> getQueueMessagesToServer(){
		return messagesToServer;
	}

}
