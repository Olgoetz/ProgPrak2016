
package pp2016.team19.server.engine;

import pp2016.team19.shared.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import pp2016.team19.server.map.*;

public class ServerMain {

	public static void main(String[] args) {
		ExecutorService serverThreadPool = Executors.newCachedThreadPool();
		LinkedBlockingQueue<Message> messagesFromClient = new LinkedBlockingQueue<Message>();
		LinkedBlockingQueue<Message> messagesToClient = new LinkedBlockingQueue<Message>();
		serverThreadPool.execute(new ServerEngine(serverThreadPool, messagesFromClient, messagesToClient));
	}
}


