package pp2016.team19.shared;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Sends updated monster list
 * @author Tobias Schrader, 5637252
 *
 */
public class MessUpdateMonsterAnswer extends Message implements Serializable {

	
	private static final long serialVersionUID = -7778611457727967668L;
	private LinkedList<Monster> MonsterList;
	/**
	 * Constructor sets type, subtype and the MonsterList
	 * @param MonsterList
	 * @param type
	 * @param subType
	 * @author Tobias Schrader, 5637252
	 */
	public MessUpdateMonsterAnswer(LinkedList<Monster> MonsterList, int type, int subType){
		super(type,subType);
		this.setMonsterList(MonsterList);
	}
/**
 * 
 * @return a LinkedList containing the monsters
 * @author Tobias Schrader, 5637252
 */
	public LinkedList<Monster> getMonsterList() {
		return MonsterList;
	}

	
	/**
	 * Sets the Monsterlist
	 * @param monsterList
	 * @author Tobias Schrader, 5637252
	 */
	public void setMonsterList(LinkedList<Monster> monsterList) {
		MonsterList = monsterList;
	}
}
