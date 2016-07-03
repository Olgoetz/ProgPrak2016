package pp2016.team19.shared;

import java.io.Serializable;

public class MessSignOutAnswer extends Message implements Serializable {


	private static final long serialVersionUID = -5605404528849382335L;
	private boolean confirmed;
	
	public MessSignOutAnswer(boolean confirmed, int type, int subType) {
		super(type,subType);
		this.confirmed = confirmed;
	}
	
	
	public boolean isConfirmed() {
		return confirmed;
	}
	
	public void setConfirme(boolean confirmed) {
		this.confirmed = confirmed;
	}

}
