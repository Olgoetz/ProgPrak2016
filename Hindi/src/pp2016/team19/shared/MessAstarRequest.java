package pp2016.team19.shared;

import java.io.Serializable;


/**
 * <h1>This class requests the AStern character movement after a mouse click.</h1>
 * 
 * @author Oliver Goetz, 5961343
 *
 */

public class MessAstarRequest extends Message implements Serializable{

	
	/**
	 * Special attributes for the AstarRequest.
	 * @author Oliver Goetz, 5961343
	 * 
	 */
	private static final long serialVersionUID = -3182834598358521775L;
	
	// the x- and y-coordinate of a mouselcik
	private int mouseX, mouseY;
	
	/**
	 * 
	 * Message object, that is sent to the server.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param type the maintype (=1) of the message
	 * @param subType the subtype (=10) of the message
	 * @param mouseX the x-coordinate of the mouseclick
	 * @param mouseY the y-coordinate of the mouseclick
	 * 
	 */
	public MessAstarRequest(int mouseX, int mouseY, int type, int subType) {
		super(type, subType);
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		
	}
	
	/**
	 * Gets the x-coordinate of the mouseclick
	 * @author Oliver Goetz, 5961343
	 * @return the mouseX-coordinate
	 */
	
	public int getMouseX() {
		return mouseX;
	}
	
	/**
	 * Gets the y-coordinate of the mouseclick
	 * @author Oliver Goetz, 5961343
	 * @return the mouseY-coordinate
	 */
	
	public int getMouseY() {
		return mouseY;
	}
	

}
