package pp2016.team19.shared;

import java.io.Serializable;

public class MessUsePotionAnswer extends Message implements Serializable {


	private static final long serialVersionUID = 854152714970390583L;
	
	public boolean confirmed;
	
	
	public MessUsePotionAnswer (boolean confirmed, int type, int subType) {
		
		super(type, subType);
		this.confirmed = confirmed;
		
	}
	
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	public boolean isConfirmed() {
		return confirmed;
	}
	

}
