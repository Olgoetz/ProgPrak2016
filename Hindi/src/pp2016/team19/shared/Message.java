package pp2016.team19.shared;

import java.io.Serializable;

public abstract class Message implements Serializable {
	
	private int type;
	private int subType;
	private int clientID;
	
	public Message(int type, int subType, int clientID){
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
	
}