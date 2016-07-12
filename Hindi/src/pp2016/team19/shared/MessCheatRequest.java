package pp2016.team19.shared;

import java.io.Serializable;
/**
 * Sends a cheat code
 * 
 * @author Tobias Schrader, 5637252
 */
public class MessCheatRequest extends Message implements Serializable {

	private static final long serialVersionUID = -4251444541599983164L;
	private String cheat;
	/**
	 * Constructor sets type, subtype and cheat
	 * @param cheat
	 * @param type
	 * @param subType
	 */
	public MessCheatRequest(String cheat, int type, int subType) {
		super(type, subType);
		this.cheat = cheat;
	}
	/**
	 * 
	 * @return
	 */
	public String getCheat() {
		return cheat;
	}
	public void setCheat(String cheat) {
		this.cheat = cheat;
	}

}
