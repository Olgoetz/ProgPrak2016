
package pp2016.team19.server;

import pp2016.team19.server.engine.ServerEngine;
import pp2016.team19.shared.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/***
 * 
 * <h1>This class is used to execute the server engine.</h1>
 * 
 * @author Tobias Schrader
 */
public class ServerMain {
/**
 * Main Method - executed upon running the program. 
 * @param args
 */
	public static void main(String[] args) {
		System.out.println("METHOD ServerMain.main: ServerEngine started");
		//Threadpool for executing the ServerEngine as a thread
		ExecutorService serverThreadPool = Executors.newCachedThreadPool();
		//Stores Messages to be sent to the client
		LinkedBlockingQueue<Message> messagesToClient = new LinkedBlockingQueue<Message>();
		//Execution of the Server Engine
		serverThreadPool.execute(new ServerEngine(messagesToClient));
	}
}