package pp2016.team19.server.comm;

import java.io.IOException;
import java.util.TimerTask;

import pp2016.team19.shared.MessPing;

public class NetworkPingCheckS extends TimerTask{

	
	private int pingIteration = 0;
	private NetworkHandlerS networkHandler;
	private boolean ServerSocketConnected = true;
	
	public NetworkPingCheckS(NetworkHandlerS networkhandler) {
		this.networkHandler = networkhandler;
	}
	@Override
	public void run() {
		pingIteration++;
//		checkConnection();
		if(ServerSocketConnected){
			System.out.println("Server is connected to Client : NETWORKPINGCHECKS");
		}
		else if(!ServerSocketConnected){
			connectionLost();
		}
	}
	
//	private void checkConnection() {
//
//			if (networkHandler.get) {
//				this.ServerSocketConnected = true;
//			} else {
//				this.ServerSocketConnected = false;
//				System.out.println("Server is not connected to the Client: NETWORKPINGCHECKS ");
//			}
//	}

	private void connectionLost() {

		System.out.println("NetworkPingCheckS STOPTHREADS for Server after " + pingIteration + " Pings");
//		this.cancel();
		try {
			System.out.println(
					"Connection to Client lost (PINGCHECK)! \n\n Please insure, that the Client was not stopped! \n Start the game again afterwards!");
			this.networkHandler.getClient().close();
			System.out.println("NETWORKPINGCHECKS: CLIENT CLOSED");
			this.networkHandler.getServer().close();
			System.out.println("NETWORKPINGCHECKS: SERVER CLOSED");
		} catch (IOException e) {
			System.out.println("ERROR: NETWORKPINGCHECKS");
			System.out.println("Socket ERROR: NetworkHandlerS resulting in NetworkPingCheckS");
			e.printStackTrace();
		}
	}

}
