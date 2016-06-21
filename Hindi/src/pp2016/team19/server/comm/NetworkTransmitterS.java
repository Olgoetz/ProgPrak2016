package pp2016.team19.server.comm;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import pp2016.team19.shared.Message;
import pp2016.team19.shared.SleepTool;

public class NetworkTransmitterS extends Thread {
	
	private Socket client;
	private ObjectOutputStream out;
	private LinkedBlockingQueue<Message> messagesToClient = new LinkedBlockingQueue<>();
	private Message messageTC;
	
	public NetworkTransmitterS(Socket client){
		this.client = client;
	}
	
	private void transmitMessage(){
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
			while (true)
			{
				SleepTool.sleepFor(100L);
				messageTC = messagesToClient.poll();
				if (messageTC != null)
				{
					out.writeObject(messageTC);
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
		this.transmitMessage();
	}
	
	public void writeMessage(Message message){
		this.messagesToClient.offer(message);
	}

}
