
package pp2016.team19.server;

import pp2016.team19.server.engine.ServerEngine;
import pp2016.team19.shared.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/***
 * 
 * <h1>This class is used to execute the Server</h1>
 * 
 * @author Tobias Schrader
 */
public class ServerMain {

	public static void main(String[] args) {
		System.out.println("METHOD ServerMain.main: ServerEngine started");
		ExecutorService serverThreadPool = Executors.newCachedThreadPool();
		LinkedBlockingQueue<Message> messagesToClient = new LinkedBlockingQueue<Message>();
		serverThreadPool.execute(new ServerEngine(serverThreadPool, messagesToClient));
	}
}


