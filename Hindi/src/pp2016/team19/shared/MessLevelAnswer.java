package pp2016.team19.shared;

import java.io.Serializable;
import java.util.LinkedList;


/**
 * 
 * <h1>Level Answer Message from server. 
 * It contains the constructor as well as a getter and setter method.</h1>
 * 
 * @author Oliver Goetz, 5961343
 * 
 */


public class MessLevelAnswer extends Message implements Serializable{

	/**
	 * The attributes for the class.
	 * 
	 * @author Oliver Goetz, 5961343
	 */
	private static final long serialVersionUID = -7110582956443760665L;
	
	// the 2-dimensional labyrinth array
	private Tile[][] labyrinth;
	
	// the monsterlist
	private LinkedList<Monster> Monsters;
	
	/**
	 * 
	 * Constructor for the MessLevelRequest
	 * @author Oliver Goetz, 5961343
	 * @param labyrinth the array for the map
	 * @param monsters the monsterlist
	 * @param type the maintype of the message
	 * @param subType the subtype of the message
	 * 
	 */
	
	public MessLevelAnswer(Tile[][] labyrinth, LinkedList<Monster> monsters, int type, int subType) {
		super(type,subType);
		this.labyrinth = labyrinth;
		this.Monsters = monsters;
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
	 * 
	 * Method, that returs the Labyrinth.
	 * @author Oliver Goetz, 596134
	 * @return the map
	 * 
	 */
	
	public Tile[][] getLabyrinth() {
		return labyrinth;
	}

	/**
	 * Gets the monsterlist.
	 * @author Oliver Goetz, 5961343
	 * @return the monsterlist
	 */
	
	public LinkedList<Monster> getMonsters() {
		return Monsters;
	}

	
	/**
	 * Sets the monsterlist.
	 * @author Oliver Goetz, 5961343
	 * @param monsters the monsterlist
	 */

	public void setMonsters(LinkedList<Monster> monsters) {
		Monsters = monsters;
	}
	

}
