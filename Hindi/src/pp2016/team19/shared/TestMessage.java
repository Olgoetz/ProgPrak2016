package pp2016.team19.shared;

public class TestMessage extends Message {

	public int type, subType, clientID;
	public String messageName;
	
	public TestMessage(int type, int subType, int clientID, String messageName) {
		super(type, subType, clientID);
		// TODO Auto-generated constructor stub
		this.type = type;
		this.subType = subType;
		this.clientID = clientID;
		this.messageName = messageName;
	}

}
