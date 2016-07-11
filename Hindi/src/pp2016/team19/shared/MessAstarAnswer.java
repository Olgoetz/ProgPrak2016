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
	private Player myPlayer;
	private boolean valid;
	
	/**
	 * Message object, that is sent to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param plyer the player object
	 * @param type the maintype (=1) of the message
	 * @param subType the subtype (=11) of the message
	 */
	
	public MessAstarAnswer(Player player, boolean valid, int type, int subType) {
		super(type, subType);
		this.myPlayer = player;
		this.valid = valid;
	}
	
	/**
	 * Sets the player.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param player
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
	
	/**
	 * Gets the value of the boolean valid, if the aStar movement is possibl.e
	 * 
	 * @author Oliver Goetz, 5961343
	 * @return the boolean valid
	 */
	
	public boolean isValid() {
		return valid;
	}
	
	
	

}
