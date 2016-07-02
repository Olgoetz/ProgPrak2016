package pp2016.team19.shared;

import java.io.Serializable;

public class MessAttackRequest extends Message implements Serializable {

	

	/**
	 * @author Oliver Goetz, 5961343

	 * Attack Request Message to server.
	 */
	private static final long serialVersionUID = 1L;
	boolean attack;
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param attack
	 * @param type
	 * @param subType
	 * 
	 * Constructor for the MessAttackRequest
	 * 
	 */
	public MessAttackRequest(int type, int subType) {
		super(type, subType);
	
	}

}
