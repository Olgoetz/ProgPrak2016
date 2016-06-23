package pp2016.team19.shared;

import java.io.Serializable;

public class MessPing extends Message implements Serializable{

	private static final long serialVersionUID = -4125004909828171573L;
	
	public MessPing(int type, int subType) {
		super(type, subType);
	}

}
