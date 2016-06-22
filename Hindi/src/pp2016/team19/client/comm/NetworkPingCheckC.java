package pp2016.team19.client.comm;

import java.io.IOException;
import java.net.SocketException;
import java.util.TimerTask;

import pp2016.team19.shared.MessPing;

public class NetworkPingCheckC extends TimerTask {

	private NetworkHandlerC networkHandler;
	private int pingIteration = 0;
	private boolean clientSocketConnected = false;

	public NetworkPingCheckC(NetworkHandlerC networkHandler) {
		this.networkHandler = networkHandler;
	}

	@Override
	public void run() {
		pingIteration++;
		checkConnection();
		if(clientSocketConnected){
			this.networkHandler.sendMessageToServer(new MessPing(100, 0));
//			this.networkHandler.getTransmitterC().writeMessage(new MessPing(100, 0));
		}
		else{
			connectionLost();
		}
			
	}
	
	private void checkConnection(){
		if(networkHandler.getConnected()){
			clientSocketConnected = true;
		}
		else{
			clientSocketConnected = false;
		}
	}



	private void connectionLost() {
		this.cancel();
		this.networkHandler.close("NetworkPingCheckC STOPTHREADS for Client after " + pingIteration + " Pings");
		try {
			System.out.println(
					"Connection to Server lost (PINGCHECK)! \n\n Please insure, that the server was not stopped! \n Start the game again afterwards!");
			this.networkHandler.getServer().close();
		} catch (IOException e) {
			System.out.println("ERROR: NETWORKPINGCHECKC");
			System.out.println("Socket ERROR: NetworkHandlerC resulting in NetworkPingCheckC");
			e.printStackTrace();
		}
	}

}
