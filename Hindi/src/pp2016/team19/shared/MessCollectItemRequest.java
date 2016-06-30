package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>....
 * @author Oliver
 *
 */
public class MessCollectItemRequest extends Message implements Serializable {

	private static final long serialVersionUID = -4068512229521041462L;
	
	public MessCollectItemRequest(int type, int subType) {
		super(type, subType);
	}

}
