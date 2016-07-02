package pp2016.team19.shared;

import java.io.Serializable;
import java.util.LinkedList;

public class MessAttackAnswer extends Message implements Serializable {

	
	
	/**
	 * @author Oliver Goetz, 5961343
	 * Attack Answer from server.
	 */
	private static final long serialVersionUID = -7005346683609579242L;
	boolean killed;
	boolean confirmed;
	private LinkedList<Monster> monster;
	
	public MessAttackAnswer(LinkedList<Monster> monster, int type, int subType) {
		super(type, subType);
		this.monster = monster;
	}
	
	public void setMonsterList(LinkedList<Monster> monster) {
		this.monster = monster;
	}
	
	public LinkedList<Monster> getMonster() {
		return monster;
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
