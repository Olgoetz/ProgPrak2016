package pp2016.team19.client.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class ClientMain {
	
	public final static int WIDTH = 16;
	public final static int HEIGHT = 16;
	public final static int BOX = 32;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ExecutorService clientThreadPool = Executors.newCachedThreadPool();
		clientThreadPool.execute(new ClientEngine(clientThreadPool));
		System.out.println("METHOD ClientMain.main: Client started");
		
		
	}
}

