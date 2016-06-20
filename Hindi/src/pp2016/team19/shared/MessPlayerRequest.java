package pp2016.team19.shared;

import java.io.Serializable;

public class MessPlayerRequest extends Message implements Serializable {

	/**
	 * @author Oliver Goetz, 5961343
	 * @parameter player
	 * 
	 * Player Request to server.
	 */
	private static final long serialVersionUID = -7001895885835235328L;   
	private Player player;
	
	public MessPlayerRequest(Player player, int type, int subType) {
		super(type, subType);
		this.player = player;
			
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	
}
