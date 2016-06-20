package pp2016.team19.shared;

import java.io.Serializable;

public class MessSignInAndUpAnswer extends Message implements Serializable {

	/**
	 * @ author Oliver Goetz, 5961343
	 * 
	 * Answer for Sign UP and Sign UP to server.
	 */
	private static final long serialVersionUID = -4381984974244494821L;
	boolean confirmed;
	
	public MessSignInAndUpAnswer(boolean confirmed, int type, int subType) {
		super(type,subType);
		this.confirmed = confirmed;
	}
	
	public boolean isConfirmed() {
		return confirmed;
	}
	
	public void setConfrimed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	
	

}
