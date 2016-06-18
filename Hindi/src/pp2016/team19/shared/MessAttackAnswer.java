package pp2016.team19.shared;

import java.io.Serializable;

public class MessAttackAnswer extends Message implements Serializable {

	
	
	/**
	 * @author Oliver Goetz, 5961343
	 * Attack Answer from server.
	 */
	private static final long serialVersionUID = -7005346683609579242L;
	boolean killed;
	boolean confirmed;
	int monsterHealth;
	
	public MessAttackAnswer(boolean confirmed, boolean killed, int monsterHealth, int type, int subType, boolean pConfirmed) {
		super(type, subType);
		this.monsterHealth = monsterHealth;
		this.confirmed = pConfirmed;
		this.killed = killed;
	}
	
	public void setConfirmed (boolean pConfirmed) {
		this.confirmed = pConfirmed;
	}
	
	public boolean isConfirmed() {
		return confirmed;
	}
	
	public void setKilled (boolean killed) {
		this.killed = killed;
	}
	
	public boolean isKilled() {
		return killed;
	}
	
	
}
