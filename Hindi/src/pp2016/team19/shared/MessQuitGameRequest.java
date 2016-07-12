package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>The QuitGameRequest that will be sent to the server.</h1>
 * @author Oliver Goetz,5961343
 *
 */
public class MessQuitGameRequest extends Message implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * The constructor for the class.
	 * @author Oliver Goetz, 5961343
	 * @param type the maintype (=2) of the message
	 * @param subType the subtype (=10) of the message
	 */
	public MessQuitGameRequest(int type, int subType) {
		super(type, subType);
	}

}
