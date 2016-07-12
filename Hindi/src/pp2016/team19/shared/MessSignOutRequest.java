package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>A message class for signing OUT that will be transmitted to the server.</h1>
 * 
 * 
 * @author Oliver Goetz, 5961343
 *
 */

public class MessSignOutRequest extends Message implements Serializable {
	
	/**
	 * The attributes for the class.
	 * @author Oliver Goetz, 5961343
	 */

	private static final long serialVersionUID = -8080036863036899541L;
	
	// the player ID
	private int playerID;
	
	
	/**
	 * The constructor of the class.
	 * @author Oliver Goetz, 5961343
	 * @param ID the playerID
	 * @param type the maintype (=0) of the message
	 * @param subType the subtype (=8) of the message
	 */
	
	public MessSignOutRequest(int ID, int type, int subType) {
		super(type,subType);
		this.playerID = ID;
	}
	
	/**
	 * Gets the playerID.
	 * @author Oliver Goetz, 5961343
	 * @return the player ID
	 */
	
	public int getPlayerID() {
		return playerID;
	}
	
	/**
	 * Sets the playerID
	 * @author Oliver Goetz, 5961343
	 * @param id a playerID
	 */
	public void setPlayerID(int id) {
		this.playerID = id;
	}
	

}
