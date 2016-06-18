package pp2016.team19.shared;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Message implements Serializable{
	
	private static final long serialVersionUID = -4125004909828171573L;
	private final long timestamp;
	private int type;
	private int subType;
	
	
	public Message(int type, int subType){
		this.timestamp = System.currentTimeMillis();
		this.type = type;
		this.subType = subType;

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

	
	/**
	 * @ author Oliver Goetz, 5961343
	 * 
	 * This method overwrites the inherited String-Method
	 * 
	 */
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-DD hh:mm:ss");
		return (dateFormat.format(new Date(this.timestamp))
				+ " Type: " + this.getType() + " Subtype: " + this.getSubType());
	}
	
}