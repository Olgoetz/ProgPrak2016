package pp2016.team19.shared;

import java.awt.Image;
import java.io.Serializable;

public class MessPlayerAnswer extends Message implements Serializable {

	/**
	 * @author Oliver Goetz, 5961343
	 * @parameter player
	 * 
	 * Player Answer from from server.
	 */
	private static final long serialVersionUID = 3578181825991876365L;
	private Player myPlayer;
	
	public MessPlayerAnswer(Player myPlayer, int type, int subType) {
		
		super(type, subType);
		
		this.myPlayer = myPlayer;
	}
	
	public Player getMyPlayer() {
		return myPlayer;
	}
	
	public void setPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}
	
	
	
	
	

}
