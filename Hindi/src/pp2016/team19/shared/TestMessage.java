package pp2016.team19.shared;

import java.io.Serializable;
/**
 * <h1>A message class for random purposes. Can send a String.</h1>
 * @author Tobias Schrader, 5637252
 *
 */
public class TestMessage extends Message implements Serializable {

	private static final long serialVersionUID = -4125004909828171573L;
	public String messageName;
	/**
	 * Constructor sets type, subtype and the String to be sent
	 * @param type
	 * @param subType
	 * @param messageName
	 * @author Tobias Schrader, 5637252 
	 */
	public TestMessage(int type, int subType, String messageName) {
		super(type, subType);
		this.messageName = messageName;
	}

}
