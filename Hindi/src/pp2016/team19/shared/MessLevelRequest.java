package pp2016.team19.shared;

import java.io.Serializable;

import pp2016.team19.server.map.Labyrinth;

public class MessLevelRequest extends Message implements Serializable {

	/**
	 * @author Oliver Goetz, 5961343
	 * @parameter labyrinth
	 * @parameter type
	 * @parameter subType
	 * 
	 * Level Request from Client
	 */
	private static final long serialVersionUID = 8268363905964396507L;
	private int level;
	
	public MessLevelRequest(int level, int type, int subType) {
		super(type, subType);
		this.level = level;
	}
	
	public void setLabyrinth(Labyrinth labyrinth) {
		this.level = level;
	}
	
	public int getLabyrinth() {
		return level;
	}
	

}
