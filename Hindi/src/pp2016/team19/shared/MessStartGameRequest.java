package pp2016.team19.shared;

import java.io.Serializable;

public class MessStartGameRequest extends Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 230217747996901093L;
	private int playerID;

	public MessStartGameRequest(int playerID, int type, int subType) {
		super(type, subType);
		this.playerID = playerID;
	} 

	public int getPlayerID() {
		return playerID;
	}

}
