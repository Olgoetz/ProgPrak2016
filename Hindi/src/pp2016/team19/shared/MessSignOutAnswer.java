package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>A message class for signing OUT coming from the server.</h1>
 * 
 * 
 * @author Oliver Goetz, 5961343
 *
 */
public class MessSignOutAnswer extends Message implements Serializable {

	/**
	 * The attributes for the class.
	 * @author Oliver Goetz, 5961343
	 */
	private static final long serialVersionUID = -5605404528849382335L;
	
	// true, if the button sign out is hit
	private boolean confirmed;
	
	/**
	 * The constructor of the class.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param confirmed a boolean
	 * @param type the maintype (=0) of the message
	 * @param subType subtype (=9) of the message
	 */
	public MessSignOutAnswer(boolean confirmed, int type, int subType) {
		super(type,subType);
		this.confirmed = confirmed;
	}
	
	
	/**
	 * Gets the boolean confirmed.
	 * @author Oliver Goetz, 5961343
	 * @return the boolean confirmed
	 */
	public boolean isConfirmed() {
		return confirmed;
	}
	
	/**
	 * Sets the boolean confirmed.
	 * @author Oliver Goetz, 5961343
	 * @param confirmed the boolean confirmed
	 */
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

}
