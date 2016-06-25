package pp2016.team19.shared;

import java.io.Serializable;


/**
 * <h1>Abstract Message Class providing timestamp, type, subType.</h1>
 * 
 * 
 * Level Answer Message from server. It contains the constructor as well as a getter and setter method.
 * <p>
 * 
 * @author Oliver Goetz, 5961343
 * 
 */


public class MessLevelAnswer extends Message implements Serializable{

	
	private static final long serialVersionUID = -7110582956443760665L;
	
	private Tile[][] labyrinth;
	
	/**
	 * @author Oliver Goetz, 5961343
	 * @param labyrinth the array for the map
	 * @param type the maintype of the message
	 * @param subType the subtype of the message
	 * 
	 * Constructor for the MessLevelRequest
	 * 
	 */
	
	public MessLevelAnswer(Tile[][] labyrinth, int type, int subType) {
		super(type,subType);
		this.labyrinth = labyrinth;
	}
	
	
	/**
	 * Method, that sets the labyrinth by passing a Tile array.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param labyrinth the array for the map
	 * 
	 * 
	 */
	public void setLabyrinth(Tile[][] labyrinth) {
		this.labyrinth = labyrinth;
	}
	
	/**
	 * @author Oliver Goetz, 596134
	 * @return the map
	 * 
	 * Method, that returs the Labyrinth.
	 */
	
	public Tile[][] getLabyrinth() {
		return labyrinth;
	}
	

}
