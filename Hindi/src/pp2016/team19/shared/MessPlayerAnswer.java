package pp2016.team19.shared;


import java.io.Serializable;

/**
 * <h1>The PlayerAnswer Message from the server.</h1>
 * 
 * @author Goetz, Oliver, 5961343
 * 
 * 
 * 
 */

public class MessPlayerAnswer extends Message implements Serializable {

	/**
	 * The attributes of the class.
	 * @author Goetz, Oliver, 5961343
	 */
	private static final long serialVersionUID = 3578181825991876365L;
	
	// a player object
	private Player myPlayer;
	
	// x- and y-coordinate of the player
	private int x,y;
	
	/**
	 * @author Goetz, Oliver, 5961343
	 * @param myPlayer the player	
	 * @param type the maintype (=2) of the message	
	 * @param subType the subtype (=5) of the message
	 * @param x the x-position of the player
	 * @param y the y-position of the player
	 * 			
	 */
	
	public MessPlayerAnswer(Player myPlayer, int type, int subType, int x, int y) {
		
		super(type, subType);
		this.x = x;
		this.y = y;
		this.myPlayer = myPlayer;
		
	}
	
	/**
	 * @author Goetz, Oliver, 5961343
	 * @return returns a player
	 * 			
	 */
	
	public Player getMyPlayer() {
		return myPlayer;
	}
	
	/**
	 * 
	 * Method, that sets a player.
	 * @author Goetz, Oliver, 5961343
	 * @param myPlayer a player
	 * 			
	 */
	
	public void setPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}
	
	
	/**
	 * Gets the x-position of the player.
	 * @author Goetz, Oliver, 5961343
	 * @return the x-coordinate
	 */

	public int getX() {
		return x;
	}

	/**
	 * Sets the x-position of the player
	 * @author Goetz, Oliver, 5961343
	 * @param x the x-coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	
	/**
	 * Gets the y-position of the player.
	 * @author Goetz, Oliver, 5961343
	 * @return the y-coordinate
	 */

	public int getY() {
		return y;
	}

	
	/**
	 * Sets the y-position of the player
	 * @author Goetz, Oliver, 5961343
	 * @param y the y-coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	

}
