/* Team 19: JAVALove
 * Teammitglieder:	Bulut, Taner, 5298261
 * 					Czernik, Christof, 5830621
 * 					Goetz, Oliver, 5961343
 * 					Langsdorf, Felizia, 6002960
 * 					Schrader, Tobias, 5637252
 * 					Strohbuecker, Max, 5960738 
 * 					
 */


package pp2016.team19.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pp2016.team19.client.engine.ClientEngine;

/**
 * <h1>ClientMain to start the client engine.</h1>
 * 
 * @author Oliver Goetz, 5961343
 *
 */

public class ClientMain {

	/**
	 * The main method starts the client engine. It initializes a new client together with
	 * a thread.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// initiates the clientThreadPool
		ExecutorService clientThreadPool = Executors.newCachedThreadPool();
		
		// executes the clienThreadPool
		clientThreadPool.execute(new ClientEngine(clientThreadPool));
		System.out.println("METHOD ClientMain.main: Client started");
		
		
	}
}

