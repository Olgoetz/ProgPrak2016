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

	public NetworkHandlerC() {

		while (this.server == null) {
			try {
				// this.server = new Socket("62.143.243.85", 33333);
				System.out.println("NetworkHandlerClient.NetworkHandlerC()");
				this.server = new Socket("localhost", 44444);
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
	}
	
	public Socket getServer() {
		return server;
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

}