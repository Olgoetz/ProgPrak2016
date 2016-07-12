package pp2016.team19.shared;

import java.io.Serializable;

/**
 * 
 * <h1>PlayerRequest Message to the server.</h1>
 * 
 * The client sends a message to the server, in order to receive one.
 * <p>
 * 
 * @author Goetz, Oliver, 5961343
 *
 */

public class MessPlayerRequest extends Message implements Serializable {

	
	/**
	 * The attributes for the class
	 * @author Goetz, Oliver, 5961343
	 */
	static final long serialVersionUID = -7001895885835235328L; 
	
	// a player object
	private Player player;
	
	/**
	 * @author Goetz, Oliver, 5961343
	 * @param player the player
	 * @param type the maintype (=2) of the message
	 * @param subType the subtype (=4) of the message
	 */
	
	public MessPlayerRequest(Player player, int type, int subType) {
		super(type, subType);
		this.player = player;
			
	}
	
	/**
	 * Method, that sets a player.
	 * @author Goetz, Oliver, 5961343
	 * @param player the player
	 */
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * Gets the player.
	 * @author Goetz, Oliver, 5961343
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	
	
}
