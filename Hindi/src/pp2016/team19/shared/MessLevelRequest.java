package pp2016.team19.shared;

import java.io.Serializable;

import pp2016.team19.server.map.Labyrinth;

public class MessLevelRequest extends Message implements Serializable {

	/**
	 * @author Oliver Goetz, 5961343
	 * @parameter level
	 * 
	 * Level Request from Client
	 */
	private static final long serialVersionUID = 8268363905964396507L;
	private Labyrinth labyrinth;
	
	public MessLevelRequest(Labyrinth labyrinth, int type, int subType) {
		super(type, subType);
		this.labyrinth = labyrinth;
	}
	
	public void setLabyrinth(Labyrinth labyrinth) {
		this.labyrinth = labyrinth;
	}
	
	public Labyrinth getLabyrinth() {
		return labyrinth;
	}
	

}
