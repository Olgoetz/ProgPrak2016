package pp2016.team19.shared;

import java.io.Serializable;

/**
 * Requests new game, sends playerID
 * @author Schrader, Tobias, 5637252
 *
 */
public class MessStartGameRequest extends Message implements Serializable {
	private static final long serialVersionUID = 230217747996901093L;
	private int playerID;

	/**
	 * Constructor sets type, subtype and required playerID
	 * @author Schrader, Tobias, 5637252
	 * @param playerID the playerID
	 * @param type the maintype (=0) of the message
	 * @param subType the subtype (=6) of the message
	 */
	public MessStartGameRequest(int playerID, int type, int subType) {
		super(type, subType);
		this.playerID = playerID;
	} 
/**
 * 
 * @return the ID of the player
 * @author Schrader, Tobias, 5637252
 */
	public int getPlayerID() {
		return playerID;
	}

}
