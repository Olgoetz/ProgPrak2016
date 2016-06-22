package pp2016.team19.client.engine;

import pp2016.team19.client.comm.NetworkHandlerC;
import pp2016.team19.client.gui.GameWindow;
import pp2016.team19.shared.Message;
import pp2016.team19.shared.TestMessage;


public class ClientMain {
	
	public final static int WIDTH = 16;
	public final static int HEIGHT = 16;
	public final static int BOX = 32;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new Engine();
		
		/*TESTS FOR SENDING MESSAGES FROM CLIENT TO SERVER 
		 *AND RECEIVING MESSAGES FROM SERVER TO CLIENT 
		 **/
//		TestMessage test1 = new TestMessage(0,1,"TestMessage1 from Client to Server");
//		TestMessage test2 = new TestMessage(0,1,"TestMessage2 from Client to Server");
//		TestMessage test3 = new TestMessage(0,1,"TestMessage3 from Client to Server");
//		TestMessage test4 = new TestMessage(0,1,"TestMessage4 from Client to Server");
//		Message message;
//		boolean sent = false;
//		NetworkHandlerC nhc = new NetworkHandlerC();
//		while(true){
//			message = nhc.getMessageFromServer();
//			if(message != null){
//				System.out.println("TestMessage received from Server : "+message.toString());
//			}
//			else if(!sent){
//			nhc.sendMessageToServer(test1);
//			nhc.sendMessageToServer(test2);
//			nhc.sendMessageToServer(test3);
//			nhc.sendMessageToServer(test4);
//			sent = true;
//			}
//		}
		
		//testEngine.getNetworkHandler();
		
	}



}

