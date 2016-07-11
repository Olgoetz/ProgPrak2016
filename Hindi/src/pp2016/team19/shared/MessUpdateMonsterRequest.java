package pp2016.team19.shared;

import java.io.Serializable;

public class MessUpdateMonsterRequest extends Message implements Serializable {

	private static final long serialVersionUID = 7145790507327923681L;
	
	public MessUpdateMonsterRequest( int type, int subType) {
		super(type, subType);
	}
}
