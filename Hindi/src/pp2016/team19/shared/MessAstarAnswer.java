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

	
	private static final long serialVersionUID = 7041547849671854239L;
	private Player myPlayer;
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param confirmed ???
	 * @param type the maintype (=1) of the message
	 * @param subType the subtype (=11) of the message
	 */
	
	public MessAstarAnswer(Player player, int type, int subType) {
		super(type, subType);
		this.myPlayer = player;
	}
	
	public void setMyPlayer(Player player) {
		this.myPlayer = player;
	}
	
	public Player getMyPlayer() {
		return myPlayer;
	}
	
	
	

}
