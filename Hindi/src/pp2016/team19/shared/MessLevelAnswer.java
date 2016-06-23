package pp2016.team19.shared;

import java.io.Serializable;

import pp2016.team19.server.map.Labyrinth;
import pp2016.team19.server.map.Tile;

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
	
	private Tile[][] labyrinth;
	
	public MessLevelAnswer(Tile[][] labyrinth, int type, int subType) {
		super(type,subType);
		this.labyrinth = labyrinth;
	}
	
	public void setLabyrinth(Tile[][] labyrinth) {
		this.labyrinth = labyrinth;
	}
	
	public Tile[][] getLabyrinth() {
		return labyrinth;
	}
	

}
