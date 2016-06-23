



package pp2016.team19.shared;

import java.io.Serializable;



/**
 *<h1>This class presents the MoveCharacterAnswer Message from the server.</h1>
 *
 *
 * Move Character Answer from server.
 * This message determines the position of the player.
 * 
 * It contains the constructor as well as a getter and setter methods.
 * 
 * @author Oliver Goetz, 5961343
 * 
 
 */

public class MessMoveCharacterAnswer extends Message implements Serializable {

	private static final long serialVersionUID = -7005346683609579242L;
	private int posX;
	private int posY;
	boolean confirmed;
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param posX the x-position of the player
	 * @param posY the y-position of the player
	 * @param type the maintype of the message
	 * @param subType the subtype of the message
	 * @param pConfirmed flag, if the player is allowed to move e. g. due to wall
	 * 
	 */
	
	public MessMoveCharacterAnswer(int posX, int posY, int type, int subType, boolean pConfirmed) {
		super(type, subType);
		this.posX = posX;
		this.posY = posY;
		this.confirmed = pConfirmed;
	}
	
	/**
	 * Method, that sets the boolean.
	 * @author Oliver Goetz, 5961343
	 * @param pConfirmed flag for allowed movement
	 * 
	 * 
	 */
	
	public void setConfirmed (boolean pConfirmed) {
		this.confirmed = pConfirmed;
	}
	
	/**
	 * Method, that returns the boolean.
	 * @author Oliver Goetz, 596134
	 * @return the value of the flag
	 * 
	 * 
	 */
	
	public boolean isConfirmed() {
		return confirmed;
	}
	
	/**
	 * Method, that gets the x-position of the player.
	 * @author Oliver Goetz, 5961343
	 * @return x-position of player
	 * 
	 * 
	 */
	public int getX() {
		return posX;
	}
	
	/**
	 * Method, that gets the y-position of the player.
	 * @author Oliver Goetz, 5961343
	 * @return y-positon of the player
	 * 
	 * 
	 */
	
	public int getY() {
		return posY;
	}
	
}
