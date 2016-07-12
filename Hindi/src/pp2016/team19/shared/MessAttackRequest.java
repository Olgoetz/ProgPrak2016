package pp2016.team19.shared;

import java.io.Serializable;


/**
 * <h1>This class requests an AttackAnswer.</h1>
 * 
 * @author Goetz, Oliver, 5961343
 *
 */

public class MessAttackRequest extends Message implements Serializable {


	private static final long serialVersionUID = 1L;
	
	
	/**
	 * The constructor of the class.
	 * @author Goetz, Oliver, 5961343
	 * @param type the maintype (=1)
	 * @param subType the subtype (=2)
	 * 
	 * Constructor for the MessAttackRequest
	 * 
	 */
	public MessAttackRequest(int type, int subType) {
		super(type, subType);
	
	}

}
