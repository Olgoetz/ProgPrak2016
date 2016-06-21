package pp2016.team19.shared;

import java.io.Serializable;

public class MessMoveCharacterRequest extends Message implements Serializable {

	
	
	/**
	 * @author Oliver Goetz, 5961343
	 * Move Request to server.
	 */
	private static final long serialVersionUID = -3530360293429750272L;
	int direction;
	boolean confirmed;
	
	public MessMoveCharacterRequest(int direction, boolean pConfirmed, int type, int subType) {
		super(type, subType);
		this.direction = direction;
		this.confirmed = pConfirmed;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}
	
	public boolean isConfirmed() {
		return confirmed;
	}
}
