package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>The MessUsePotionAnswer coming from the server.</h1>
 *
 * @author Oliver Goetz, 5961343
 *
 */


public class MessUsePotionAnswer extends Message implements Serializable {

	
	/**
	 * The attributes of the class.
	 * @author Oliver Goetz, 5961343
	 */

	private static final long serialVersionUID = 854152714970390583L;
	
	// a player object
	private Player myPlayer;
	
	// true, if a potion is used
	private boolean confirmed;
	
	/**
	 * The constructor for the class
	 * @author Oliver Goetz, 5961343
	 * @param myPlayer the player object
	 * @param confirmed the test variable, if a poition is used or net
	 * @param type the maintype (=1) of the message
	 * @param subType the subtype (=7) of the message
	 */
	public MessUsePotionAnswer (Player myPlayer, boolean confirmed, int type, int subType) {
		
		super(type, subType);
		this.myPlayer = myPlayer;
		this.confirmed = confirmed;
		
	}
	
	/**
	 * Gets the player object
	 * @author Oliver Goetz, 5961343
	 * @return a player object
	 */
	public Player getPlayer() {
		return myPlayer;
	}
	
	/**
	 * Sets a boolean variable.
	 * @author Oliver Goetz, 596134
	 * @param confirmed the boolean confirmed
	 */
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	
	/**
	 * Gets the boolean confirmed.
	 * @author Oliver Goetz, 5961343
	 * @return the boolean confirmed
	 */
	public boolean isConfirmed() {
		return confirmed;
	}
	

}
