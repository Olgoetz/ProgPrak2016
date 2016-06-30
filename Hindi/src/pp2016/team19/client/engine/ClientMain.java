package pp2016.team19.client.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>ClientMain to start the client engine.</h1>
 * 
 * @author Oliver Goetz, 5961343
 *
 */

public class ClientMain {
	
	public final static int WIDTH = 16;
	public final static int HEIGHT = 16;
	public final static int BOX = 32;


	/**
	 * <h1>The main method starts the client engine. It initializes a new client together with
	 * a thread.</h1>
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ExecutorService clientThreadPool = Executors.newCachedThreadPool();
		clientThreadPool.execute(new ClientEngine(clientThreadPool));
		System.out.println("METHOD ClientMain.main: Client started");
		
		
	}
}

