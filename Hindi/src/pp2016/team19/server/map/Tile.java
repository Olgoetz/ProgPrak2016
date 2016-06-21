package pp2016.team19.server.map;

/**
 * Generally:
 * 
 * 		Its a data structure called Tile, used in class called Labyrinth.
 * 
 * Class Tile contains:
 * 
 * 		4 Attributes called: 	type ("Is the type of the Tile") 									
 * 								containsKey ("Does the Tile contain a Key?") 
 * 								containsPotion ("Does the Tile contain a Potion?") 
 * 								contains Monster ("Does the Tile contain a Monster?")
 * 
 * 		7 Constants: 			Which represent the types and contants.
 * 
 * 		4 Setter methods: 		They set for example the type of the Tile 
 * 								or if a Tile contains a Potion.
 * 
 * 		8 Other methods: 		For example, check if a Tile is of the type ROCK. 
 * 								Or if a Tile is walkable.
 * 
 * @author < Czernik, Christof, 5830621 >
 */

public class Tile {

	/**
	 * 4 Attributes
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */

	// Attribute, 	("Is the type of the Tile")
	public int type;
	// Attribute,	("Does the Tile contain a Key?")
	public boolean containsKey = false;
	// Attribute, 	("Does the Tile contain a Potion?")
	public boolean containsPotion = false;
	// Attribute,	("Does the Tile contain a Monster?")
	public boolean containsMonster = false;

	/**
	 * 8 Constants
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	// Constants for attribute type
	public static final int ROCK = 1;
	public static final int FLOOR = 0;
	public static final int ENTRY = 2;
	public static final int EXIT = 3;

	// Constants for other attributes
	public static final int KEY = 11;
	public static final int POTION = 12;
	public static final int MONSTER = 13;
	public static final int PLAYER = 14;
	
	/**
	 * Setter method, which sets the type of Tile
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public void setType(int input) {
		
		type = input;
		
	}

	/**
	 * Method, which returns a boolean, if a Tile is of type ROCK or not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public boolean isRock() {

		if (type == ROCK) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method, which returns a boolean, if a Tile is of type FLOOR or not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */

	public boolean isFloor() {

		if (type == FLOOR) {
			return true;
		}

		return false;
	}

	/**
	 * Method, which returns a boolean, if a Tile is of type ENTRY or not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public boolean isEntry() {

		if (type == ENTRY) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method, which returns a boolean, if a Tile is of type EXIT or not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */

	public boolean isExit() {

		if (type == EXIT) {
			return true;
		}
		return false;
	}

	/**
	 * Setter method, which sets a Tile to contain a Key or not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	public void setContainsKey(boolean input) {
		containsKey = input;
	}

	/**
	 * Setter method, which sets a Tile to contain a Potion or not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public void setContainsPotion(boolean input) {
		containsPotion = input;
	}
	
	/**
	 * Setter method, which sets a Tile to contain a Monster or not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public void setContainsMonster(boolean input) {
		containsMonster = input;
	}
	
	/**
	 * Getter Method, which returns a boolean if a Tile contains a Key or not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public boolean containsKey() {
		return containsKey;
	}

	/**
	 * Getter Method, which returns a boolean if a Tile contains a Potion or not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public boolean containsPotion() {
		return containsPotion;
	}

	/**
	 * Getter Method, which returns a boolean if a Tile contains a Monster or not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public boolean containsMonster() {

		return containsMonster;
	}

	/**
	 * Getter Method, which returns a boolean if a Tile is walkable or not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */

	public boolean isWalkable() {
		if (type == FLOOR || type == ENTRY || type == EXIT) {
			return true;
		}
		return false;
	}

} // Class End.
