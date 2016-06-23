package pp2016.team19.shared;

import java.awt.Image;
import java.io.Serializable;

/**
 * <h1>The PlayerAnswer Message from the server.</h1>
 * 
 * @author Oliver Goetz, 5961343
 * 
 * 
 * 
 */

public class MessPlayerAnswer extends Message implements Serializable {


	private static final long serialVersionUID = 3578181825991876365L;
	private Player myPlayer;
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param myPlayer The player
	 * 			
	 * @param type the maintype of the message
	 * 			
	 * @param subType the subtype of the message
	 * 			
	 */
	
	public MessPlayerAnswer(Player myPlayer, int type, int subType) {
		
		super(type, subType);
		
		this.myPlayer = myPlayer;
	}
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @return returns a player
	 * 			
	 */
	
	public Player getMyPlayer() {
		return myPlayer;
	}
	
	/**
	 * 
	 * Method, that sets a player.
	 * @author Oliver Goetz, 5961343
	 * @param myPlayer a player
	 * 			
	 */
	
	public void setPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}
	

}
