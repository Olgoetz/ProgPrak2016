package pp2016.team19.server.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import pp2016.team19.shared.MessMoveCharacterRequest;
import pp2016.team19.shared.Message;
import pp2016.team19.shared.TestMessage;



public class NetworkHandlerS {

	private ServerSocket server;
	private Socket client;
	private int clientID;
	private Timer pingTimer;
	private NetworkReceiverS receiver;
	private NetworkTransmitterS transmitter;
	private boolean connected = false;

public NetworkHandlerS() {
		
		try {
			server = new ServerSocket(44444);
			while(!connected){
				client = server.accept();
				if(client.isConnected()){
					receiver = new NetworkReceiverS(client);
					transmitter = new NetworkTransmitterS(client);
					receiver.start();
					transmitter.start();
					connected = true;
							}
					
			}
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
	}
public void sendMessageToClient(Message message){
	transmitter.writeMessage(message);
}
public Message getMessageFromClient(){
	return receiver.getMessage();
}
public void addMessage(Message message) { //For Testing
	try {
		receiver.messagesFromClient.put(message);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	
}