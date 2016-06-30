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


	private static final long serialVersionUID = -3530360293429750272L;
	private int direction;
	private int x,y;
	
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param direction integer for the direction (0=up, 1=down, 2=left, 3=left)
	 * @param type the maintype of the message
	 * @param subType the subtype of the message
	 * 
	 * 
	 */
	

	public MessMoveCharacterRequest(int x, int y, int direction, int type, int subType) {
		super(type, subType);
		this.x = x;
		this.y = y;
		this.direction = direction;
	
	}
	public int getX() {
		return x;
	}
	
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
