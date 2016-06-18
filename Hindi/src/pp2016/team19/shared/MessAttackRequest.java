package pp2016.team19.shared;

import java.io.Serializable;

public class MessAttackRequest extends Message implements Serializable {

	

	/**
	 * @author Oliver Goetz, 5961343
	 * Attack Request to server.
	 */
	private static final long serialVersionUID = 1L;
	boolean attack;
	
	public MessAttackRequest(boolean attack, int type, int subType) {
		super(type, subType);
		this.attack = attack;
		
	}

}
