package pp2016.team19.shared;

import java.io.Serializable;
/**
 * <h1>Sends a cheat code.</h1>
 * 
 * @author Tobias Schrader, 5637252
 */
public class MessCheatRequest extends Message implements Serializable {

	private static final long serialVersionUID = -4251444541599983164L;
	private String cheat;
	/**
	 * Constructor sets type, subtype and cheat
	 * @param cheat the cheat
	 * @param type the maintype(=2) of the message
	 * @param subType the subtyp(=10) of the message
	 * @author Tobias Schrader, 5637252
	 */
	public MessCheatRequest(String cheat, int type, int subType) {
		super(type, subType);
		this.cheat = cheat;
	}
	/**
	 * Gets the cheat.
	 * @return  the Cheatcode
	 * @author Tobias Schrader
	 */
	public String getCheat() {
		return cheat;
	}
	
	/**
	 * sets the cheatcode
	 * @param cheat
	 * @author Tobias Schrader, 5637252
	 */
	public void setCheat(String cheat) {
		this.cheat = cheat;
	}

}
