package pp2016.team19.shared;

import java.io.Serializable;
import java.util.LinkedList;


/**
 * <h1>This class requests an AttackAnswer.</h1>
 * 
 * @author Oliver Goetz, 5961343
 *
 */
public class MessAttackAnswer extends Message implements Serializable {

	
	
	/**
	 * The attributes for the class.
	 * @author Oliver Goetz, 5961343
	 * 
	 */
	
	
	
	private static final long serialVersionUID = -7005346683609579242L;
	
	// status, if a monster is killed
	boolean killed;
	
	// check for an attack
	boolean confirmed;
	
	// the monsterlist
	private LinkedList<Monster> monster;
	
	/**
	 * 
	 * The constructor of the class.
	 * @author Oliver Goetz, 5961343
	 * @param monster the monsterlist
	 * @param confirmed control if attack is possible
	 * @param type the maintype (=0)
	 * @param subType the subtype (=7)
	 */
	
	public MessAttackAnswer(LinkedList<Monster> monster, boolean confirmed, int type, int subType) {
		super(type, subType);
		this.monster = monster;
		this.confirmed = confirmed;
	}
	
	/**
	 * Sets the monster list.
	 * @author Oliver Goetz, 5961343
	 * @param monster a monster list
	 */
	public void setMonsterList(LinkedList<Monster> monster) {
		this.monster = monster;
	}
	
	/**
	 * Gets the monsterlist.
	 * @author Oliver Goetz, 5961343
	 * @return the current monsterlist
	 */
	public LinkedList<Monster> getMonster() {
		return monster;
	}
	
	/**
	 * Sets the boolean.
	 * @author Oliver Goetz, 5961343
	 * @param pConfirmed the boolean confirmed
	 */
	public void setConfirmed (boolean pConfirmed) {
		this.confirmed = pConfirmed;
	}
	
	/**
	 * Gets the boolean
	 * @author Oliver Goetz, 5961343
	 * @return the boolean confirmed
	 */
	public boolean isConfirmed() {
		return confirmed;
	}
	
	/**
	 * Sets the monster status.
	 * @author Oliver Goetz, 5961343
	 * @param killed the status of the monster
	 */
	public void setKilled (boolean killed) {
		this.killed = killed;
	}
	
	/**
	 * Gets the status of the monster.
	 * @author Oliver Goetz, 5961343
	 * @return the monster status
	 */
	public boolean isKilled() {
		return killed;
	}
	
	
}
