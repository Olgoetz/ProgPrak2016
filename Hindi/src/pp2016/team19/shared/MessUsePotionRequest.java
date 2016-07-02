package pp2016.team19.shared;

import java.io.Serializable;

public class MessUsePotionRequest extends Message implements Serializable {

	
	private static final long serialVersionUID = 4872412035824844038L;

	public MessUsePotionRequest(int type, int subType) {
		super(type, subType);
	}
}
