package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>This Message class contains the Ping-Signal for indicating the connection state.</h1>
 *  
 * 
 * @author Taner Bulut, 5298261
*/
public class MessPing extends Message implements Serializable{

	private static final long serialVersionUID = -4125004909828171573L;
	
	/**
	 * @author Taner Bulut, 5298261
	 * @param type - the maintype (=100) of the message
	 * @param subType - the subtype (=0) of the message
	 */
	public MessPing(int type, int subType) {
		super(type, subType);
	}

}
