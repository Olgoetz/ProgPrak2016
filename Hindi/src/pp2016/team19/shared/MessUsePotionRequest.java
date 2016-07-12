package pp2016.team19.shared;

import java.io.Serializable;


/**
 * <h1>The MessUsePotionRequest that is transmitted to the server.</h1>
 *
 * @author Goetz, Oliver, 5961343
 *
 */
public class MessUsePotionRequest extends Message implements Serializable {

	
	private static final long serialVersionUID = 4872412035824844038L;

	/**
	 * The constructor of the class.
	 * @author Goetz, Oliver, 5961343
	 * @param type the maintype (=1) of the message
	 * @param subType the subtype (=6) of the message
	 */
	public MessUsePotionRequest(int type, int subType) {
		super(type, subType);
	}
}
