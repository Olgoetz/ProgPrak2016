package pp2016.team19.shared;

import java.io.Serializable;
import java.util.LinkedList;

import pp2016.team19.server.engine.Game;

/**
 * <h1>Player Class extending Character class and providing name, password,
 * whether he is logged in, whether he has the key, number of potions he
 * carries, the effect of a potion, the score, whether he's invulnerable and a
 * path to a defined position.</h1>
 * 
 * Class Player contains all data about a player and offers methods to move the
 * character to a defined position or to interact (attack / use potion).
 * 
 * @author Strohbuecker, Max, 5960738
 */
public class Player extends Character implements Serializable {

	private static final long serialVersionUID = -3344880853631540753L;

	/**
	 * Attributes of class Player.
	 * 
	 * @param name
	 *            username of the player
	 * @param password
	 *            password which belongs to this username
	 * @param isLoggedIn
	 *            boolean, whether the player is actually logged in or not
	 * @param hasKey
	 *            boolean, whether the player has the key or not
	 * @param numberOfPotions
	 *            number, how many potions the player carries with him
	 * @param potionEffect
	 *            healing effect that a potion has
	 * @param score
	 *            the score of the player
	 * @param isInvulnerable
	 *            boolean, whether the player is invulnerable (can't take
	 *            damage) or not
	 * @param pathToPos
	 *            a path to a defined position (calculated with AStarSearch)
	 * @author Strohbuecker, Max, 5960738
	 */
	private String name;
	private String password;
	private boolean isLoggedIn;

	private boolean hasKey;
	private int numberOfPotions;
	private int potionEffect = 40;
	private int score;
	private boolean isInvulnerable;

	private LinkedList<Node> pathToPos = new LinkedList<Node>();

	/**
	 * Constructor of class Player. Initializes all relevant data and sets name
	 * and password.
	 * 
	 * @param userName
	 *            the name, which the player should have (entered username)
	 * @param password
	 *            the password, which belongs to the username
	 * @author Strohbuecker, Max, 5960738
	 */
	public Player(String userName, String password) {
		super();

		setName(userName);
		setPassword(password);
		setNumberOfPotions(0);
		setPos(0, 0);
		setHealth(100);
		setMaxHealth(getHealth());
		setDamage(8);
	}

	/**
	 * Standard constructor of class Player. Only sets the relevant data without
	 * setting name and password.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public Player() {
		super();
		setNumberOfPotions(0);
		setPos(0, 0);
		setHealth(100);
		setMaxHealth(getHealth());
		setName("Anonymous");
	}

	/**
	 * Alternative Contructor of class Player. Sets the game which belongs to
	 * the player.
	 * 
	 * @param game
	 *            the game, which belongs to the player / where the player
	 *            exists in
	 * @author Strohbuecker, Max, 5960738
	 */
	public Player(Game game) {
		super(game);

		setNumberOfPotions(0);
		setPos(0, 0);
		setHealth(100);
		setMaxHealth(getHealth());
		setName("Anonymous");
	}

	/**
	 * Resets the relevant data of the player to the default values.
	 */
	public void reset() {
		setNumberOfPotions(0);
		setPos(0, 0);
		setHealth(100);
	}

	/**
	 * Calculates a path to a specific position with AStarSearch. Additionally
	 * adds the goal position to this path, because AStarSeach only calculates
	 * until one field before the goal (easier for the monster system).
	 * 
	 * @param xGoal
	 *            x-coordinate of the goal position
	 * @param yGoal
	 *            y-coordinate of the goal position
	 * @return returns null if no path was found, otherwise returns the path to
	 *         the goal as list of nodes
	 * @author Strohbuecker, Max, 5960738
	 */
	public LinkedList<Node> moveToPos(int xGoal, int yGoal) {
		if (pathToPos.isEmpty()) {
			pathToPos = AStarSearch(this.getXPos(), this.getYPos(), xGoal,
					yGoal);
			if (pathToPos == null) {
				return null;
			}
			pathToPos.addLast(new Node(xGoal, yGoal));
		}

		// Tile[][] gameMap = getGame().getGameMap();
		// System.out.println("\n");
		// for (int i = 0; i < getGame().getGameSize(); i++) {
		// for (int j = 0; j < getGame().getGameSize(); j++) {
		//
		// if (gameMap[i][j].isEntry()) {
		// System.out.print("S ");
		//
		// } else if (gameMap[i][j].isExit()) {
		// System.out.print("Z ");
		//
		// } else if (gameMap[i][j].containsKey()) {
		// System.out.print("K ");
		//
		// } else if (gameMap[i][j].containsPotion()) {
		// System.out.print("P ");
		//
		// } else if (gameMap[i][j].containsMonster()) {
		// System.out.print("M ");
		//
		// } else if (gameMap[i][j].isRock()) {
		// System.out.print("+ ");
		//
		// } else if (gameMap[i][j].isFloor()) {
		// System.out.print("o ");
		//
		// }
		//
		//
		//
		//
		// }
		// System.out.println("");
		// }

		return pathToPos;
	}

	/**
	 * Searches for a monster standing nearby, which the player could attack and
	 * returns it.
	 * 
	 * @return returns an attackable monster
	 * @author Strohbuecker, Max, 5960738
	 */
	public Monster monsterToAttack() {
		for (int i = 0; i < getGame().getMonsters().size(); i++) {
			Monster m = getGame().getMonsters().get(i);

			// Is the player able to attack?
			boolean ableToAttack = false;
			if (m.getType() == 0)
				ableToAttack = true;
			if (m.getType() == 1)
				ableToAttack = hasKey;

			if ((Math.sqrt(Math.pow(getXPos() - m.getXPos(), 2)
					+ Math.pow(getYPos() - m.getYPos(), 2)) < 2)
					&& ableToAttack) {
				return m;
			}
		}

		return null;
	}

	/**
	 * Uses one of the potions and heals the player.
	 * 
	 * @return returns the new health of the player, after taking the potion
	 * @author Strohbuecker, Max, 5960738
	 */
	public int usePotion() {
		setNumberOfPotions(numberOfPotions - 1);
		health = Math.min(health + potionEffect, getMaxHealth());
		return health;
	}

	/**
	 * Takes a potion from the ground and increases the number of carried
	 * potions.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public void takePotion() {
		numberOfPotions++;
	}

	/**
	 * Heals or damages the player if he is not invulnerable (can't take
	 * damage).
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public void changeHealth(int change) {
		if (!isInvulnerable) {
			super.changeHealth(change);
		}
	}

	/**
	 * Generates a string to give out the player with username, password and its
	 * position.
	 * 
	 * @return returns a string with a minimalistic info about the player to
	 *         give out.
	 * @author Strohbuecker, Max, 5960738
	 */
	public String toString() {
		return "Player: " + this.getName() + ", PW: " + this.getPassword()
				+ ", Pos: " + this.getXPos() + ", " + this.getYPos();
	}

	// GETTER AND SETTER

	/**
	 * Takes the key and sets the associated variable.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public void takeKey() {
		hasKey = true;
	}

	/**
	 * Removes the key when starting a new level or game.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public void removeKey() {
		hasKey = false;
	}

	/**
	 * Sets the number of potions the player should carry.
	 * 
	 * @param quantity
	 *            number of potions the player should carry
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setNumberOfPotions(int quantity) {
		if (quantity >= 0)
			numberOfPotions = quantity;
	}

	/**
	 * Gets the number of potions the player carries.
	 * 
	 * @return returns how many potions the player carries with him
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getNumberOfPotions() {
		return numberOfPotions;
	}

	/**
	 * Returns, whether the player has the key or not.
	 * 
	 * @return returns, whether the player has the key or not
	 * @author Strohbuecker, Max, 5960738
	 */
	public boolean hasKey() {
		return hasKey;
	}

	/**
	 * Gets the (user)name of the player.
	 * 
	 * @return returns the (user)name of the player.
	 * @author Strohbuecker, Max, 5960738
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the (user)name of the player.
	 * 
	 * @param name
	 *            (user)name the player should have
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the password, which belongs to this player / username.
	 * 
	 * @return returns the password, which belongs to this player / username
	 * @author Strohbuecker, Max, 5960738
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password belonging to the username.
	 * 
	 * @param password
	 *            password, which should be connected to this player / username
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the score of the player.
	 * 
	 * @return returns the actual score of the player
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the actual score of this player.
	 * 
	 * @param score
	 *            the score which the player should have
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Increases the score by a specific value.
	 * 
	 * @param points points which should be added to the score
	 * @author Strohbuecker, Max, 5960738
	 */
	public void increaseScore(int points) {
		score = score + points;
	}

	/**
	 * Returns, whether the player is logged in or not.
	 * 
	 * @return returns, whether the player is logged in or not
	 * @author Strohbuecker, Max, 5960738
	 */
	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	/**
	 * Logs in the player to the server.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public void logIn() {
		this.isLoggedIn = true;
	}

	/**
	 * Logs out the player from the server.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public void logOut() {
		this.isLoggedIn = false;
	}

	/**
	 * (De-)Activates the shield fpr the player and makes him invulnerable (can't take damage).
	 * 
	 * @param bool boolean, whether the shield should be activated or deactivated
	 * @author Strohbuecker, Max, 5960738
	 */
	public void characterShield(boolean bool) {
		isInvulnerable = bool;
	}
}
