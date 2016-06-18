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

	private Socket server;
	private LinkedBlockingQueue<Message> messagesFromServer;
	private LinkedBlockingQueue<Message> messagesToServer;
	private ObjectOutputStream out;
	private ObjectInputStream in;
//	private int clientID;
//	private Timer pingTimer;
	

	/*LinkedBlockingQueue<Message> pMessagesFromServer,
			LinkedBlockingQueue<Message> pMessagesToServer*/
	public NetworkHandlerC() {

//		this.messagesFromServer = pMessagesFromServer;
//		this.messagesToServer = pMessagesToServer;
//		this.clientID = -1;
//		this.pingTimer = new Timer();
		TestMessage test = new TestMessage(1,1,1,"TestMessage here");
		while (this.server == null) {
			try {

				// this.server = new Socket("62.143.243.85", 33333);
				this.server = new Socket("localhost", 55555);
				in = new ObjectInputStream(server.getInputStream());
				out = new ObjectOutputStream(server.getOutputStream());
				out.writeObject(test);

			} catch (UnknownHostException e) {
				System.out.println("ERROR: NetworkService 1");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("ERROR: >>>>>>>>>>NetworkService 2 SERVER UNREACHABLE<<<<<<<<<<");
				e.printStackTrace();
				JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
						"Verbindung zum Server kann nicht aufgebaut werden! \n\n Bitte ueberpruefen Sie: \n 1. Ist der Spiel-Server gestartet? \n 2. Ist die richtige Serveradresse in Ihrem Client eingestellt? \n 3. Stimmen die Ports in Server und Client ueberein? \n\n Starten Sie das Spiel anschliessend neu!",
						"Verbindungfehler 1!", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
//		this.startNetworkComponents();
	}
//
//	public void startNetworkComponents() {
//
//		this.getThreadPool().execute(new NetworkTransmitterC(this));
//		this.getThreadPool().execute(new NetworkReceiverC(this));
//		this.getPingTimer().scheduleAtFixedRate(new NetworkPingCheckC(this), 3000, 3000);
//	}

//	public void close(String message, String title) {
//
//		try {
//			System.out.println("CLOSED: NetworkHandler");
//			this.setStopNetwork(true);
//			this.getPingTimer().cancel();
//			this.getServer().close();
//			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, title, JOptionPane.ERROR_MESSAGE);
//			System.exit(1);
//		} catch (IOException e) {
//			System.out.println("ERROR: NETWORKHANDLER 3");
//			e.printStackTrace();
//		}
//	}


//	Socket getServer() {
//		return server;
//	}
//
//	private void setServer(Socket server) {
//		this.server = server;
//	}
//
//	LinkedBlockingQueue<Message> getMessagesFromServer() {
//		return messagesFromServer;
//	}
//
//	private void setMessagesFromServer(LinkedBlockingQueue<Message> messagesFromServer) {
//		this.messagesFromServer = messagesFromServer;
//	}
//
//	LinkedBlockingQueue<Message> getMessagesToServer() {
//		return messagesToServer;
//	}
//
//	private void setMessagesToServer(LinkedBlockingQueue<Message> messagesToServer) {
//		this.messagesToServer = messagesToServer;
//	}
//
//	public int getClientID() {
//		return clientID;
//	}
//
//	void setClientID(int clientID) {
//		this.clientID = clientID;
//	}
//
//	Timer getPingTimer() {
//		return pingTimer;
//	}
//
//	private void setPingTimer(Timer pingTimer) {
//		this.pingTimer = pingTimer;
//	}
}