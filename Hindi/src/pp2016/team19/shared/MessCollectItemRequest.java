package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>This class is the CollectItemRequest for the server.</h1>
 * 
 * @author Goetz, Oliver, 5961343
 *
 */
public class MessCollectItemRequest extends Message implements Serializable {

	private static final long serialVersionUID = -4068512229521041462L;
	
	
	/**
	 * The contructor of the class
	 * @author Goetz, Oliver, 5961343
	 * @param type the maintype (=1)
	 * @param subType the subtype (=4)
	 */
	public MessCollectItemRequest(int type, int subType) {
		super(type, subType);
	}

}
