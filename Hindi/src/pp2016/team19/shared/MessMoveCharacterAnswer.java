package pp2016.team19.shared;

import java.io.Serializable;

public class MessMoveCharacterAnswer extends Message implements Serializable {

	
	
	/**
	 * @author Oliver Goetz, 5961343
	 * Move-Down Request from server.
	 */
	private static final long serialVersionUID = -7005346683609579242L;
	private int posX;
	private int posY;
	boolean confirmed;
	
	public MessMoveCharacterAnswer(int posX, int posY, int type, int subType, boolean pConfirmed) {
		super(type, subType);
		this.posX = posX;
		this.posY = posY;
		this.confirmed = pConfirmed;
	}
	
	public void setConfirmed (boolean pConfirmed) {
		this.confirmed = pConfirmed;
	}
	
	public boolean isConfirmed() {
		return confirmed;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
}
