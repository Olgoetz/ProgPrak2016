package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>Move Request to server.
 * The message sends a request to the server, which direction the player wants to take.</h1>
 * 
 * @author Oliver Goetz, 5961343
 * 

 */

public class MessMoveCharacterRequest extends Message implements Serializable {

	
	/**
	 * The attributes for the class
	 * @author Oliver Goetz, 5961343
	 */

	private static final long serialVersionUID = -3530360293429750272L;
	
	// the movement direction
	private int direction;
	
	// x- and y-position of the player
	private int x,y;
	
	
	/**
	 * 
	 * The constructor of the class.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param direction integer for the direction (0=up, 1=down, 2=left, 3=right)
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param type the maintype (=1) of the message
	 * @param subType the subtype (=0) of the message
	 * 
	 * 
	 */
	

	public MessMoveCharacterRequest(int x, int y, int direction, int type, int subType) {
		super(type, subType);
		this.x = x;
		this.y = y;
		this.direction = direction;
	
	}
	
	/**
	 * Gets the x-position of the player.
	 * @author Oliver Goetz, 5961343
	 * @return the x-position
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the y-position of the player.
	 * @author Oliver Goetz, 5961343
	 * @return the y-position
	 */
	
	public int getY() {
		return y;
	}
	
	
	/**
	 * 
	 *  Method, that sets an int, in which direction the player wants to move
	 * 	0 = move up
	 *  1 = move down
	 *  2 = move left
	 *  3 = move right
	 * @author Oliver Goetz, 596134
	 * @param direction integer for the direction
	 * 
	
	 */
	
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * Method, that gets the int for the appropriate direction.
	 * @author Oliver Goetz, 5961343
	 * @return the integer for the direction
	 * 
	 * 
	 */
	public int getDirection() {
		return direction;
	}
	
}
