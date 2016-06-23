package pp2016.team19.shared;

import java.io.Serializable;

/**
 * 
 * <h1>PlayerRequest Message to the server.</h1>
 * 
 * The client sends a message to the server, in order to receive one.
 * <p>
 * 
 * @author Oliver Goetz, 5961343
 *
 */

public class MessPlayerRequest extends Message implements Serializable {

	static final long serialVersionUID = -7001895885835235328L;   
	private Player player;
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param player the player
	 * @param type the maintype of the message
	 * @param subType the subtype of the message
	 */
	
	public MessPlayerRequest(Player player, int type, int subType) {
		super(type, subType);
		this.player = player;
			
	}
	
	/**
	 * Method, that sets a plyer
	 * @author Oliver Goetz, 5961343
	 * @param player the player
	 */
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @return a player
	 */
	public Player getPlayer() {
		return player;
	}
	
	
}
