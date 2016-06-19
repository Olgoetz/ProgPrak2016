package pp2016.team19.shared;

import java.io.Serializable;

public class MessSignInAndUpRequest extends Message implements Serializable {

	/**
	 * @ author Oliver Goetz, 5961343
	 * 
	 * Request for Sign UP and Sign UP to server.
	 */
	private static final long serialVersionUID = -6043901602629877055L;
	
	private String username;
	private String password;
	
	
	public MessSignInAndUpRequest(String username, String password, int type, int subType) {
		super(type, subType);
		this.username = username;
		this.password = password;
	}
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword (String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

}
