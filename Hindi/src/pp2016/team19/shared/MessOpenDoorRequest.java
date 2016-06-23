package pp2016.team19.shared;

import java.io.Serializable;


/**
 * <h1>OpenDoorRequest Message to the server.</h1>
 * 
 * Client sends a requet to the server.
 * <p>
 * @author Oliver Goetz, 5961343
 * 
 * 
 */

public class MessOpenDoorRequest extends Message implements Serializable {


	private static final long serialVersionUID = -6223977759661914096L;
	boolean openDoor;
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param openDoor flag for the state of the door
	 * @param type the maintype of the message
	 * @param subType the subtype of the message
	 * 
	 */
	
	public MessOpenDoorRequest(boolean openDoor, int type,int subType) {
		super(type,subType);
		this.openDoor = openDoor;
	}
	
	/**
	 * Method, that sets the door on the state open.
	 * @author Oliver Goetz, 5961343
	 * @param openDoor flag for the state of the door
	 * 
	 * 
	 */
	public void setOpenDoor(boolean openDoor) {
		this.openDoor = openDoor;
	}
	
	/**
	 * Method, that returs the state of the door.
	 * @author Oliver Goetz, 5961343
	 * @return the state of the door
	 * 
	 * 
	 */
	
	public boolean getOpenDoor() {
		return openDoor;
	}
}
