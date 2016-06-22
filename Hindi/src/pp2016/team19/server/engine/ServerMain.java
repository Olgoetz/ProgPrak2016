
package pp2016.team19.server.engine;

import pp2016.team19.shared.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import pp2016.team19.server.comm.NetworkHandlerS;
import pp2016.team19.server.map.*;

/** 
 * Server Main, used to execute the Server Engine
 * 
 * @author Tobias Schrader
 */
public class ServerMain {

	public static void main(String[] args) {
//		ExecutorService serverThreadPool = Executors.newCachedThreadPool();
//		LinkedBlockingQueue<Message> messagesFromClient = new LinkedBlockingQueue<Message>();
//		LinkedBlockingQueue<Message> messagesToClient = new LinkedBlockingQueue<Message>();
//		serverThreadPool.execute(new ServerEngine(serverThreadPool, messagesToClient));
		
		/*TESTS FOR SENDING MESSAGES FROM SERVER TO CLIENT 
		 *AND RECEIVING MESSAGES FROM CLIENT TO SERVER 
		 **/
		TestMessage test1 = new TestMessage(0,1,"TestMessage1 from Server to Client");
		TestMessage test2 = new TestMessage(0,1,"TestMessage2 from Server to Client");
		TestMessage test3 = new TestMessage(0,1,"TestMessage3 from Server to Client");
		TestMessage test4 = new TestMessage(0,1,"TestMessage4 from Server to Client");
		Message message;
		boolean sent = false;
		NetworkHandlerS nhs = new NetworkHandlerS();
		while(true){
			message = nhs.getMessageFromClient();
			if(message != null){
				System.out.println("TestMessage received from Client : "+message.toString());
			}
			else if(!sent){
			nhs.sendMessageToClient(test1);
			nhs.sendMessageToClient(test2);
			nhs.sendMessageToClient(test3);
			nhs.sendMessageToClient(test4);
			sent = true;
			}
		}
	}
}


