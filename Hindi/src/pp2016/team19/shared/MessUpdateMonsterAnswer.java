package pp2016.team19.shared;

import java.io.Serializable;
import java.util.LinkedList;

public class MessUpdateMonsterAnswer extends Message implements Serializable {

	
	private static final long serialVersionUID = -7778611457727967668L;
	private LinkedList<Monster> MonsterList;
	private Monster myMonster;
	
	public MessUpdateMonsterAnswer(LinkedList<Monster> MonsterList, int type, int subType){
		super(type,subType);
		this.setMonsterList(MonsterList);
	}

	public LinkedList<Monster> getMonsterList() {
		return MonsterList;
	}

	public Monster myMonster() {
		return myMonster;
	}
	
	public void setMonsterList(LinkedList<Monster> monsterList) {
		MonsterList = monsterList;
	}
}
