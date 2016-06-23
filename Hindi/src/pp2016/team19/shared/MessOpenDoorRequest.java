package pp2016.team19.shared;

import java.io.Serializable;

public class MessOpenDoorRequest extends Message implements Serializable {

	/**
	 * @author Oliver Goetz, 5961343
	 * OpenDoorRequest to Server.
	 */
	private static final long serialVersionUID = -6223977759661914096L;
	boolean openDoor;
	
	public MessOpenDoorRequest(boolean openDoor, int type,int subType) {
		super(type,subType);
		this.openDoor = openDoor;
	}
	
	public void setOpenDoor(boolean openDoor) {
		this.openDoor = openDoor;
	}
	
	public boolean getOpenDoor() {
		return openDoor;
	}
}
