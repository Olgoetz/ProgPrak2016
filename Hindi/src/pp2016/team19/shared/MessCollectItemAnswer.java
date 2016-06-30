package pp2016.team19.shared;

import java.io.Serializable;

public class MessCollectItemAnswer extends Message implements Serializable {

	private static final long serialVersionUID = 719098351318086540L;
	private int ID;

	// ID = 0 key, 
	// ID = 1 potion,
	// ID = -1 none
	
	public MessCollectItemAnswer(int ID, int type, int subType) {
		super(type, subType);
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int id) {
		this.ID = id;
	}

}
