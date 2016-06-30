package pp2016.team19.shared;

import java.io.Serializable;

public class MessCollectItemAnswer extends Message implements Serializable {

	private static final long serialVersionUID = 719098351318086540L;
	private int ID;

	public MessCollectItemAnswer(int ID, int type, int subType) {
		super(type, subType);
		this.ID = ID;
	}

}
