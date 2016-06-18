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

import pp2016.team19.shared.Message;
import pp2016.team19.shared.TestMessage;



public class NetworkHandlerS {

	private ServerSocket server;
	private Socket client;
	private LinkedBlockingQueue<Message> outputQueue;
	private static LinkedBlockingQueue<Message> messagesFromClient;
	private static LinkedBlockingQueue<Message> messagesToClient;
	private int clientID;
	private Timer pingTimer;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	//INKONSTRUKTOR: Socket pClient,LinkedBlockingQueue<Message> pMessagesFromClient,LinkedBlockingQueue<Message> pMessagesToClient
	public NetworkHandlerS() {
		
		try {
			server = new ServerSocket(44444);
			while(true){
				client = server.accept();
				this.receiveMessage(client);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		this.client = pClient;
//		NetworkHandlerS.messagesFromClient = pMessagesFromClient;
//		NetworkHandlerS.messagesToClient = pMessagesToClient;
//		this.outputQueue = new LinkedBlockingQueue<Message>();
//		this.pingTimer = new Timer();
	}
	
	public void receiveMessage(Socket client){
		
		TestMessage test;
		LinkedBlockingQueue<Message> q = new LinkedBlockingQueue<Message>();
		try {
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
			
			try {
				test = (TestMessage) in.readObject();
				System.out.println("TEST_CONNECTION_NUM:"+test.messageName);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

//	@Override
//	public void run() {
//
//		System.out.println("THREAD STARTED: NetworkHandler for Client "
//				+ this.clientID);
//
//		this.clientID = NetworkServiceS.getClientPool().indexOf(this);
//		this.threadPool.execute(new NetworkTransmitterS(this));
//		this.threadPool.execute(new NetworkReceiverS(this));
//		this.pingTimer.scheduleAtFixedRate(new NetworkPingCheckS(this), 3000,
//				3000);
//
//		System.out.println("THREAD FINISHED: NetworkHandler for Client "
//				+ this.clientID);
//	}

//	public void close(){
//
//		try {
//			this.pingTimer.cancel();
//			this.client.close();
//			NetworkServiceS.getClientPool().set(getClientID(), null);
//			System.out.println(this + " CLOSED AND REMOVED");
//		} catch (IOException e) {
//			System.out.println("ERROR: NETWORKHANDLER 3");
//			e.printStackTrace();
//		}
//	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public LinkedBlockingQueue<Message> getOutputQueue() {
		return outputQueue;
	}

	public void setOutputQueue(
			LinkedBlockingQueue<Message> outputQueue) {
		this.outputQueue = outputQueue;
	}

	public LinkedBlockingQueue<Message> getEngineMessages() {
		return messagesFromClient;
	}

	public void setEngineMessages(
			LinkedBlockingQueue<Message> engineMessages) {
		NetworkHandlerS.messagesFromClient = engineMessages;
	}

	public static LinkedBlockingQueue<Message> getOutputMessages() {
		return messagesToClient;
	}

	public static  void setOutputMessages(
			LinkedBlockingQueue<Message> outputMessages) {
		NetworkHandlerS.messagesToClient = outputMessages;
	}

	public  Timer getPingTimer() {
		return pingTimer;
	}

	public  void setPingTimer(Timer pingTimer) {
		this.pingTimer = pingTimer;
	}
}