package pp2016.team19.client.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JOptionPane;

import pp2016.team19.shared.Message;
import pp2016.team19.shared.TestMessage;

public class NetworkHandlerC {
	
	public LinkedBlockingQueue<Message> outputQueue = new LinkedBlockingQueue<>();
	private Socket server;
	private NetworkReceiverC receiver;
	private NetworkTransmitterC transmitter;
	private Timer pingTimer;
	private boolean connected;

	public NetworkHandlerC() {
		this.pingTimer = new Timer();
		while (this.server == null) {
			try {
				// this.server = new Socket("62.143.243.85", 33333);
				System.out.println("NetworkHandlerClient.NetworkHandlerC()");
				this.server = new Socket("localhost", 44444);
				this.setConnected(true);
			} catch (UnknownHostException e) {
				System.out.println("ERROR: NetworkHandlerClient ");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("ERROR: >>>>>>>>>>NetworkHandlerClient SERVER UNREACHABLE<<<<<<<<<<");
				e.printStackTrace();
				JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
						"Connection server cannot be built! \n\n Please check : \n 1. The game-server is started? \n 2. The client follows the appropriate serveraddress? \n 3. The server-port and the client-port do match? \n\n Start the game again afterwards!",
						"Connection-Error!", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
		startComponents();
	}

	private void startComponents(){
		System.out.println("NetworkHandlerClient.startComponents()");
		transmitter = new NetworkTransmitterC(server);
		receiver = new NetworkReceiverC(server);
		transmitter.start();
		receiver.start();
		this.pingTimer.scheduleAtFixedRate(new NetworkPingCheckC(this), 3000, 3000);
	}
	
	public void close(String errorMessage){
		try {
			System.out.println("CLOSED: NetworkHandlerC");
			this.setConnected(false);
			this.pingTimer.cancel();
			this.getServer().close();
			System.out.println(errorMessage);
			System.exit(1);
		} catch (IOException e) {
			System.out.println("ERROR: NETWORKHANDLERC");
			e.printStackTrace();
		}
	}
	
	
	public void setConnected(boolean connected){
		this.connected = connected;
	}
	public boolean getConnected(){
		return this.connected;
	}
	public Socket getServer() {
		return this.server;
	}
	public void setServer(Socket server) {
		this.server = server;
	}
	public void sendMessageToServer(Message message){
		System.out.println("NetworkHandlerClient.sendMessageToServer()");
		transmitter.writeMessage(message);
	}
	public Message getMessageFromServer(){
		return receiver.getMessage();
	}
	
	public LinkedBlockingQueue<Message> getOutputQueue(){
		return this.outputQueue = transmitter.getQueueMessagesToServer();
	}
	
	public NetworkTransmitterC getTransmitterC(){
		return this.transmitter;
	}
	
	public NetworkReceiverC getReceiverC(){
		return this.receiver;
	}
	

}