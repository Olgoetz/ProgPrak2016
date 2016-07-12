package pp2016.team19.shared;

import java.io.Serializable;


/**
 * <h1>A message class for signing IN as well as signing UP coming from the server.</h1>
 * 
 * 
 * @author Oliver Goetz, 5961343
 *
 */
public class MessSignInAndUpAnswer extends Message implements Serializable {


	private static final long serialVersionUID = -4381984974244494821L;
	boolean confirmed;
	private int playerID;
	
	/**
	 * The constructor of the class.
	 * @author Oliver Goetz, 5961343
	 * @param confirmed flag, if the username and password are equal
	 * @param playerID the playerID
	 * @param type the maintype (=0) of the message
	 * @param subType the subtype (=4) of the message
	 */
	
	public MessSignInAndUpAnswer(boolean confirmed, int playerID, int type, int subType) {
		super(type,subType);
		this.confirmed = confirmed;
		this.setPlayerID(playerID);
	}

	/**
	 * Gets the boolean confiremd.
	 * @author Oliver Goetz, 5961343
	 * @return a flag, if the sign IN or UP was confirmed by the server
	 */
	
	public boolean isConfirmed() {
		return confirmed;
	}
	
	/**
	 * Method, that sets a flag.
	 * @param confirmed a flag
	 */
	
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * Gets the playerID.
	 * @author Oliver Goetz, 5961343
	 * @return the playerID
	 */
	public int getPlayerID() {
		return playerID;
	}
	
	/**
	 * Sets the playerID.
	 * @author Oliver Goetz, 5961343
	 * @param playerID the playerID
	 */
	public void setPlayerID(int playerID) {
		this.playerID=playerID;
	}
}
