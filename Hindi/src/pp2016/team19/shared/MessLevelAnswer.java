package pp2016.team19.shared;

import java.io.Serializable;

import pp2016.team19.server.map.Labyrinth;

public class MessLevelAnswer extends Message implements Serializable{

	/**
	 * @author Oliver Goetz, 5961343
	 * @parameter labyrinth
	 * @parameter type
	 * @parameter subType
	 * 
	 * Level Answer from Server
	 */
	private static final long serialVersionUID = -7110582956443760665L;
	
	private GameObject[][] labyrinth;
	
	public MessLevelAnswer(GameObject[][] labyrinth, int type, int subType) {
		super(type,subType);
		this.labyrinth = labyrinth;
	}
	
	public void setLabyrinth(GameObject[][] labyrinth) {
		this.labyrinth = labyrinth;
	}
	
	public GameObject[][] getLabyrinth() {
		return labyrinth;
	}
	

}
