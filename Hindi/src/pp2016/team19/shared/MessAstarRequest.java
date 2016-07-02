package pp2016.team19.shared;

import java.io.Serializable;


/**
 * <h1>This class requests the AStern character movement after a mouse click.</h1>
 * 
 * @author Oliver Goetz, 5961343
 *
 */

public class MessAstarRequest extends Message implements Serializable{

	private static final long serialVersionUID = -3182834598358521775L;
	private int mouseX, mouseY;
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param type the maintype (=1) of the message
	 * @param subType the subtype (=10) of the message
	 * 
	 */
	public MessAstarRequest(int mouseX, int mouseY, int type, int subType) {
		super(type, subType);
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	

}