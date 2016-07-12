package pp2016.team19.shared;

import java.io.Serializable;


/**
 * <h1>This message class contains the Astern character movement.</h1>
 * 
 * 
 * @author Oliver Goetz, 5961343
 *
 */
public class MessAstarAnswer extends Message implements Serializable {

	/**
	 * 
	 * Special attributes for the AstarAnswer.
	 * @author Oliver Goetz, 5961343
	 * 
	 */
	private static final long serialVersionUID = 7041547849671854239L;
	
	// the player
	private Player myPlayer;
	
	/**
	 * Message object, that is sent to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param player the player object
	 * @param type the maintype (=1) of the message
	 * @param subType the subtype (=11) of the message
	 */
	
	public MessAstarAnswer(Player player, int type, int subType) {
		super(type, subType);
		this.myPlayer = player;
	}
	
	/**
	 * Sets the player.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param player a player object
	 */
	
	public void setMyPlayer(Player player) {
		this.myPlayer = player;
	}
	
	/**
	 * Gets the player.
	 * @author Oliver Goetz, 5961343
	 * @return the player
	 */
	
	public Player getMyPlayer() {
		return myPlayer;
	}
	
	

}
