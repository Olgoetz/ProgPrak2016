package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>OpenDoorAnswer from the server.</h1>
 * 
 * The client receives an opendoor message from the server.
 * <p>
 * @author Oliver Goetz, 5961343
 *
 */
public class MessOpenDoorAnswer extends Message implements Serializable{
	
	/**
	 * The attributes for the class.
	 * @author Oliver Goetz, 5961343
	 */
	private static final long serialVersionUID = 1L;
	
	// true, if the doors is opened
	boolean openDoor;
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param openDoor flag for the state of the door
	 * @param type the maintype (=1) of the message
	 * @param subType the subtype (=9) of the message
	 */

	public MessOpenDoorAnswer(boolean openDoor, int type, int subType) {
		super(type, subType);
		this.openDoor = openDoor;
	}
	
	/**
	 * Method, that sets the state of the door.
	 * @author Oliver Goetz, 5961343
	 * @param openDoor flag for the state of the door
	 */
	public void setOpenDoor(boolean openDoor) {
		this.openDoor = openDoor;
	}
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @return the state of the door
	 */
	
	public boolean getOpenDoor() {
		return openDoor;
	}
}
