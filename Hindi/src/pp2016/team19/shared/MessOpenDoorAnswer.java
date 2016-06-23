package pp2016.team19.shared;

import java.io.Serializable;

public class MessOpenDoorAnswer extends Message implements Serializable{
	
	/**
	 * @author Tobias Schrader
	 * Answer to OpenDoorRequest
	 */
	private static final long serialVersionUID = 1L;
	boolean openDoor;

	public MessOpenDoorAnswer(boolean openDoor, int type, int subType) {
		super(type, subType);
		this.openDoor = openDoor;
	}
	public void setOpenDoor(boolean openDoor) {
		this.openDoor = openDoor;
	}
	
	public boolean getOpenDoor() {
		return openDoor;
	}
}
