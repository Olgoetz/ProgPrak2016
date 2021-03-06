package pp2016.team19.shared;

import java.io.Serializable;

/**
 *	<h1>A data structure called Tile, used in class called Labyrinth to generate Tile-Objects.</h1>
 * 
 * 	<p>
 * 	type = Tile type, for example "floor", "rock", "exit", "entry".
 * 	<p>							
 * 	containsKey = Does the Tile contain a Key?
 * 	<p>
 * 	containsPotion = Does the Tile contain a Potion? 
 * 	<p>
 * 	containsMonster = Does the Tile contain a Monster?
 * 	<p>
 * 	containsPlayer = Does the Tile contain the Player?
 * 	<p>
 * 	exitUnlocked = Is the exit already unlocked?
 * 	<p>
 * 
 * 	It contains for every attribut Getter and Setter and also a few other helpful methods.
 * 
 * 
 * @author Czernik, Christof, 5830621
 */

public class Tile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -213294409171693723L;
	/**
	 * 6 Attributes
	 * 
	 * @author Czernik, Christof, 5830621
	 */

	// Attribute, 	("Is the type of the Tile")
	private int type;
	// Attribute,	("Does the Tile contain a Key?")
	private boolean containsKey = false;
	// Attribute, 	("Does the Tile contain a Potion?")
	private boolean containsPotion = false;
	// Attribute,	("Does the Tile contain a Monster?")
	private boolean containsMonster = false;
	// Attribute, 	("Does the Tile contain a Player?")
	private boolean containsPlayer = false;
	// Attribute,	("Is the Exit unlocked? (:= Player has Key)")
	private boolean exitUnlocked = false;

	/**
	 * 8 Constants
	 * 
	 * @author Czernik, Christof, 5830621
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
	 * @param input type your Tile must become, example: "FLOOR".
	 * @author Czernik, Christof, 5830621
	 */
	
	public void setType(int input) {
		
		type = input;
		
	}

	/**
	 * Method, which returns a boolean, if a Tile is of type ROCK or not.
	 * 
	 * @return returns true, if the Tile is of Type Rock.
	 * @author Czernik, Christof, 5830621
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
	 * @return returns true, if the Tile is of Type Floor.
	 * @author Czernik, Christof, 5830621
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
	 * @return returns true, if the Tile is of Type Entry.
	 * @author Czernik, Christof, 5830621
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
	 * @return returns true, if the Tile is of Type Exit.
	 * @author Czernik, Christof, 5830621
	 */

	public boolean isExit() {

		if (type == EXIT) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Setter method, which sets the Exit Unlocked or Locked, if the Player has a key.
	 * 
	 * @param input set input false or true, for unlocking exit.
	 * @author Czernik, Christof, 5830621
	 */
	
	public void setExitUnlocked(boolean input) {
		exitUnlocked = input;
	}
	
	/**
	 * Setter method, which sets a Tile to contain a Player or not.
	 * 
	 * @param input set input false or true, for setting player on Tile.
	 * @author Czernik, Christof, 5830621
	 */
	
	public void setContainsPlayer(boolean input) {
		containsPlayer = input;
	}
	
	/**
	 * Setter method, which sets a Tile to contain a Key or not.
	 * 
	 * @param input set input false or true, for setting Key on Tile.
	 * @author Czernik, Christof, 5830621
	 */
	
	public void setContainsKey(boolean input) {
		containsKey = input;
	}

	/**
	 * Setter method, which sets a Tile to contain a Potion or not.
	 * 
	 * @param input set input false or true, for setting Potion on Tile.
	 * @author Czernik, Christof, 5830621
	 */
	
	public void setContainsPotion(boolean input) {
		containsPotion = input;
	}
	
	/**
	 * Setter method, which sets a Tile to contain a Monster or not.
	 * 
	 * @param input set input false or true, for setting Monster on Tile.
	 * @author Czernik, Christof, 5830621
	 */
	
	public void setContainsMonster(boolean input) {
		containsMonster = input;
	}
	
	/**
	 * Getter Method, which returns a boolean if a the exit is unlocked or not.
	 * 
	 * @return returns true, when exit unlocked.
	 * @author Czernik, Christof, 5830621
	 */
	
	public boolean exitUnlocked() {
		return exitUnlocked;
	}
	
	/**
	 * Getter Method, which returns a boolean if a Tile contains the Player or not.
	 * 
	 * @return returns true, when Tile contains player.
	 * @author Czernik, Christof, 5830621
	 */
	
	public boolean containsPlayer() {
		return containsPlayer;
	}
	
	/**
	 * Getter Method, which returns a boolean if a Tile contains a Key or not.
	 * 
	 * @return returns true, when Tile contains key.
	 * @author Czernik, Christof, 5830621
	 */
	
	public boolean containsKey() {
		return containsKey;
	}

	/**
	 * Getter Method, which returns a boolean if a Tile contains a Potion or not.
	 * 
	 * @return returns true, when Tile contains a potion.
	 * @author Czernik, Christof, 5830621
	 */
	
	public boolean containsPotion() {
		return containsPotion;
	}

	/**
	 * Getter Method, which returns a boolean if a Tile contains a Monster or not.
	 * 
	 * @return returns true, when Tile contains monster.
	 * @author Czernik, Christof, 5830621
	 */
	
	public boolean containsMonster() {

		return containsMonster;
	}

	/**
	 * Getter Method, which returns a boolean if a Tile is walkable or not.
	 * 
	 * @return returns true, when Tile is walkable.
	 * @author Czernik, Christof, 5830621
	 */

	public boolean isWalkable() {
		if (type == FLOOR || type == ENTRY || type == EXIT) {
			return true;
		}
		return false;
	}

} // Class End.
