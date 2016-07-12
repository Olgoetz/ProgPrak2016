package pp2016.team19.shared;

import java.io.Serializable;


/**
 * <h1>This class represents a CollectItemAnswer.</h1>
 * 
 * @author Goetz, Oliver, 5961343
 *
 */

public class MessCollectItemAnswer extends Message implements Serializable {

	
	/**
	 * The attributes for the class.
	 * 
	 * @author Goetz, Oliver, 5961343
	 */
	private static final long serialVersionUID = 719098351318086540L;
	
	// ID = 0 key, 
	// ID = 1 potion,
	// ID = -1 none
	private int ID;

	
	/**
	 * The constructor of the class.
	 * @author Goetz, Oliver, 5961343
	 * @param ID an ID for the item identification
	 * @param type the maintype (=1)
	 * @param subType the subtype (=5)
	 */
	public MessCollectItemAnswer(int ID, int type, int subType) {
		super(type, subType);
		this.ID = ID;
	}
	
	/**
	 * Gets the ID
	 * @author Goetz, Oliver, 5961343
	 * @return the ID
	 */
	
	public int getID() {
		return ID;
	}
	
	/**
	 * Sets the ID
	 * @author Goetz, Oliver, 5961343
	 * @param id the ID
	 */
	public void setID(int id) {
		this.ID = id;
	}

}
