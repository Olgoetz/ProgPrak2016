package pp2016.team19.shared;

import java.io.Serializable;

public class MessUpdateMonsterAnswer extends Message implements Serializable {

	
	private static final long serialVersionUID = -7778611457727967668L;
	private boolean confirmed;
	
	public MessUpdateMonsterAnswer(boolean confirmed, int type, int subType){
		super(type,subType);
		this.confirmed = confirmed;
	}
	
	public void setConfrimed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	public boolean isConfiremd() {
		return confirmed;
	}
}
