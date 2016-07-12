package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>Message to request the level from the server.</h1>
 * 
 * Level Answer Message from server. It contains the constructor as well as a getter and setter method.
 * <p>
 * @author Goetz, Oliver, 5961343
 *
 */
public class MessLevelRequest extends Message implements Serializable {

	/**
	 * The attributes for the LevelRequest
	 * 
	 * @author Goetz, Oliver, 5961343
	 */

	private static final long serialVersionUID = 8268363905964396507L;
	
	// the levelnumber
	private int level;
	
	
	/**
	 * 
	 * 
	 * @author Goetz, Oliver, 5961343
	 * @param level receives an int for a level with the ID 0-5
	 * @param type the maintype (=2) of the message
	 * @param subType the subtype (=0) of the message
	 * @see Message
	 * 
	 
	 */
	public MessLevelRequest(int level, int type, int subType) {
		super(type, subType);
		this.level = level;
	}
	
	
	/**
	 *  Method, that sets the current level.
	 * @author Goetz, Oliver, 5961343
	 * @param level sets the level ID
	 * 
	 *
	 */
	public void setLabyrinth(int level) {
		this.level = level;
	}
	
	
	/**
	 * Gets the current level.
	 * @author Goetz, Oliver, 5961343
	 * @return the current level.
	 * 
	 *
	 */

	public int getLabyrinth() {
		return level;
	}
	

}
