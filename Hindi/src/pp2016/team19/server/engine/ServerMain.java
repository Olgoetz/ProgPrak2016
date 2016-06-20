
package pp2016.team19.server.engine;

import pp2016.team19.shared.*;
import pp2016.team19.server.map.*;

public class ServerMain {

	public static void main(String[] args) {
		//Test
		Player player1 = new Player();
		Game game1 = new Game(player1, 20);
		game1.run();
	}

}


