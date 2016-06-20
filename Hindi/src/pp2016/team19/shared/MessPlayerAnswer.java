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
	
	public MessPlayerAnswer(int NumberOfPoitions, int effect, int health,
								int x, int y, int maxHealth, Image img, int type, int subType) {
		
		super(type, subType);
		
		myPlayer.setPos(x, y);
		myPlayer.setHealth(maxHealth);
		myPlayer.setNumberOfPotions(NumberOfPoitions);
		myPlayer.setImage(img);
		myPlayer.setMaxHealth(maxHealth);
	}
	
	public Player getMyPlayer() {
		return myPlayer;
	}
	
	public void setPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}
	
	
	
	
	

}
