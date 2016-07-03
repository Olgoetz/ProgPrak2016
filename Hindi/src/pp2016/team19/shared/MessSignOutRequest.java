package pp2016.team19.shared;

import java.io.Serializable;

public class MessSignOutRequest extends Message implements Serializable {

	private static final long serialVersionUID = -8080036863036899541L;
	private int playerID;
	
	
	public MessSignOutRequest(int ID, int type, int subType) {
		super(type,subType);
		this.playerID = ID;
	}
	
	public int getPlayerID() {
		return playerID;
	}
	
	public void setPlayerID(int id) {
		this.playerID = id;
	}
	

}
