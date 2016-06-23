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
	int direction;
	boolean confirmed;
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param direction integer for the direction (0=up, 1=down, 2=left, 3=left)
	 * @param pConfirmed flag, if the movement is allowed
	 * @param type the maintype of the message
	 * @param subType the subtype of the message
	 * 
	 * 
	 */
	
	public MessMoveCharacterRequest(int direction, boolean pConfirmed, int type, int subType) {
		super(type, subType);
		this.direction = direction;
		this.confirmed = pConfirmed;
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
	
	/**
	 * Method, that checks if the movement is allowed. 
	 * If there is a wall, the movement is rejected.
	 * @author Oliver Goetz, 596134
	 * @return the flag, if the movement is allowed
	 * 
	
	 */
	public boolean isConfirmed() {
		return confirmed;
	}
}
