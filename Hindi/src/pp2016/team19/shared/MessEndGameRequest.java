package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>This class is the EndGameRequest for the server.</h1>
 * 
 * @author Goetz, Oliver, 5961343
 *
 */

public class MessEndGameRequest extends Message implements Serializable {

	
	private static final long serialVersionUID = 3658562025668598957L;
	
	
	/**
	 * The constructor for the class
	 * @author Goetz, Oliver, 5961343
	 * @param type the maintyp (=2)
	 * @param subType the subtype (=6)
	 */
	public MessEndGameRequest(int type, int subType) {
		super(type, subType);
		
		
	}
	
	

}
