package pp2016.team19.shared;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Message{
	
	private final long timestamp;
	private int type;
	private int subType;
	private int clientID;
	
	public Message(int type, int subType, int clientID){
		this.timestamp = System.currentTimeMillis();
		this.type = type;
		this.subType = subType;
		this.clientID = clientID;
	}
	
	// Getter and Setter
	
	public void setType(int type){
		this.type = type;
	}
	public int getType(){
		return type;
	}
	public void setSubType(int subType){
		this.subType = subType;
	}
	public int getSubType(){
		return subType;
	}
	public void setClientID(int clientID){
		this.clientID = clientID;
	}
	public int getClientID(){
		return clientID;
	}
	
	/**
	 * @ author Oliver Götz, 5961343
	 * 
	 * This method overwrites the inherited String-Method
	 */
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-DD hh:mm:ss");
		return (dateFormat.format(new Date(this.timestamp)) + ":: ClientID :"
				+ this.getClientID() + " PlayerID: " 
				+ " Type: " + this.getType() + " Subtype: " + this.getSubType());
	}
	
}