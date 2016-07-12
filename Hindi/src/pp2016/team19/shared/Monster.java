package pp2016.team19.shared;

import java.util.LinkedList;
import pp2016.team19.server.engine.Game;

/**
 * <h1>Monster Class extending Character class and providing last action times,
 * cooldown times, whether it takes the key, the last action it performed, the
 * player position, the path where to flee, whether it just attacked, the type
 * it is of, the player and the game it belongs to.</h1>
 * 
 * Class Monster describes every logic of the monsters, how they fight, how they
 * search a way to the player, how they flee, and so on.
 * 
 * @author Strohbuecker, Max, 5960738
 */
public class Monster extends Character {

	private static final long serialVersionUID = -8329575206755045242L;

	/**
	 * Attributes of class Monster.
	 * 
	 * @param lastAttack
	 *            the time, the monster attacked the last time
	 * @param lastStep
	 *            the time, the monster did the last step
	 * @param cooldownAttack
	 *            the interval in which the monster should attack
	 * @param cooldownWalk
	 *            the interval in which the monster should walk
	 * @param carriesKey
	 *            boolean, whether the monster carries the key or not
	 * @param lastAction
	 *            the action the monster performed last (0 = move to the player,
	 *            1 = flee, 2 = regenerate health)
	 * @param lastPlayerPos
	 *            position where the player was before calculating a path
	 * @param pathToPlayer
	 *            path to the player, calculated by AStarSearch
	 * @param fleePath
	 *            path to the calculated flee position
	 * @param justAttacked
	 *            boolean, whether the monster just attacked or not
	 * @param type
	 *            type of the monster (0 = spawns at the beginning, 1 = spawns
	 *            after taking the key)
	 * @param player
	 *            the player of the actual game
	 * @param game
	 *            the actual game which the monster belongs to / exists in
	 * @author Strohbuecker, Max, 5960738
	 */
	private long lastAttack;
	private long lastStep;
	private int cooldownAttack;
	private int cooldownWalk;
	private boolean carriesKey;

	private int lastAction; // Defines what action the monster should do: 0 move
							// to player, 1 flee, 2 regenerate

	private Node lastPlayerPos;
	private LinkedList<Node> pathToPlayer;
	private LinkedList<Node> fleePath;

	private boolean justAttacked = false;

	private int type; // Present from beginning: 0, Appears later: 1

	private Player player;
	private Game game;

	/**
	 * Contructor of the class Monster.
	 * 
	 * @param x
	 *            Coordinate, where the monster should spawn
	 * @param y
	 *            Coordinate, where the monster should spawn
	 * @param game
	 *            game which the monster belongs to, contains the gamefield/map
	 *            and the player data
	 * @param type
	 *            Type of the monster (0 = spawns at the beginning, 1 = spawns
	 *            after taking the key)
	 * @author Strohbuecker, Max, 5960738
	 */
	public Monster(int x, int y, Game game, int type) {
		super(game);
		this.game = game;
		this.player = game.getPlayer();
		this.type = type;
		setPos(x, y);
		setHealth(32);
		setMaxHealth(getHealth());
		lastAttack = System.currentTimeMillis();
		lastStep = System.currentTimeMillis();
		cooldownAttack = 500 - 10 * game.getLevelNumber(); // ms
		cooldownWalk = 1000;
		lastPlayerPos = new Node(-1, -1);
		pathToPlayer = new LinkedList<Node>();
		fleePath = new LinkedList<Node>();
		lastAction = -1;
		carriesKey = false;

		setDamage(5 + game.getLevelNumber() * 2);
	}


	// <<<<<<<<<< FSM >>>>>>>>>>
	
	/**
	 * The FSM of the monster. It decides (depending on the situation) what the
	 * monster should do next (move to the player, flee or regenerate health).
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public void move() {
		boolean nextWalk = (System.currentTimeMillis() - lastStep) >= cooldownWalk;
		if (nextWalk) {
			if (playerInRange(this.xPos, this.yPos)) {
				if (this.getHealth() > this.getMaxHealth() / 4)
					// STATE 0: Player is in range and monster has enough health
					// -> move to player
					moveToPlayer();
				else {
					// STATE 1: Player is in range, but monster is weak -> flee
					// from the player
					if (!flee())
						// Back to STATE 0: Player is in range, monster is weak,
						// but there's no possibility to flee -> move to player
						moveToPlayer();
				}
			} else {
				// STATE 2: Player is not in range -> regenerate health
				changeHealth(5);
				lastAction = 2;
			}

			lastStep = System.currentTimeMillis();
		}
	}
	
	// FSM - STATE 0: Move to Player
	
	/**
	 * Moves the monster to the player. If the playerPos has changed, it calls
	 * the AStar to calculate a new path to him and the monster goes the first
	 * step of the path. Otherwise the monster should go further the calculated
	 * path.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public void moveToPlayer() {

		cooldownWalk = 1000;

		// Did the player move since the last route calculation?
		if (lastAction != 0 || pathToPlayer.isEmpty()
				|| player.getXPos() != this.lastPlayerPos.getXPos()
				|| player.getYPos() != this.lastPlayerPos.getYPos()) {
			pathToPlayer.clear();
			pathToPlayer = AStarSearch(this.getXPos(), this.getYPos(),
					player.getXPos(), player.getYPos());
			updatePlayerPos();
		}

		changeDir(pathToPlayer);
		lastAction = 0;
	}

	// FSM - STATE 1: Flee
	
	/**
	 * Compares the position of the player to the position of the monster and
	 * moves the monster away of the player into the corner.
	 * 
	 * @return returns whether there's a position to flee to
	 * @author Strohbuecker, Max, 5960738
	 */
	public boolean flee() {

		cooldownWalk = 500;

		if (lastAction != 1 || fleePath.isEmpty()) {
			Node fleePos = getFleePos();
			// System.out.println("Flee to: " + fleePos.getXPos() + ", "
			// + fleePos.getYPos());
			fleePath.clear();
			if (fleePos == null) {
				lastAction = 1;
				return false;
			} else {
				fleePath = AStarSearch(this.getXPos(), this.getYPos(),
						fleePos.getXPos(), fleePos.getYPos());
			}
		}

		changeDir(fleePath);
		lastAction = 1;
		return true;
	}
	
	/**
	 * Calculates the point, where the monster should flee to.
	 * 
	 * @return returns a Node with the fleeing position
	 * @author Strohbuecker, Max, 5960738
	 */
	public Node getFleePos() {

		if (this.getXPos() >= player.getXPos()) {
			if (this.getYPos() >= player.getYPos()) {
				// Monster is under and right of the player, flee to down right
				// corner
				for (int fleeX = game.getGameSize() - 1; fleeX >= game
						.getGameSize() / 2; fleeX--) {
					for (int fleeY = game.getGameSize() - 1; fleeY >= game
							.getGameSize() / 2; fleeY--) {
						if (isWalkable(fleeX, fleeY)
								&& !playerInRange(fleeX, fleeY))
							return new Node(fleeX, fleeY);
					}
				}
			} else if (this.getYPos() < player.getYPos()) {
				// Monster is above and right of the player, flee to top right
				// corner
				for (int fleeX = game.getGameSize() - 1; fleeX >= game
						.getGameSize() / 2; fleeX--) {
					for (int fleeY = 0; fleeY < game.getGameSize() / 2; fleeY++) {
						if (isWalkable(fleeX, fleeY)
								&& !playerInRange(fleeX, fleeY))
							return new Node(fleeX, fleeY);
					}
				}
			}
		} else if (this.getXPos() < player.getXPos()) {
			if (this.getYPos() >= player.getYPos()) {
				// Monster is under and left of the player, flee to down left
				// corner
				for (int fleeX = 0; fleeX < game.getGameSize() / 2; fleeX++) {
					for (int fleeY = game.getGameSize() - 1; fleeY >= game
							.getGameSize() / 2; fleeY--) {
						if (isWalkable(fleeX, fleeY)
								&& !playerInRange(fleeX, fleeY))
							return new Node(fleeX, fleeY);
					}
				}
			} else if (this.getYPos() < player.getYPos()) {
				// Monster is above and left of the player, flee to top left
				// corner
				for (int fleeX = 0; fleeX < game.getGameSize() / 2; fleeX++) {
					for (int fleeY = 0; fleeY < game.getGameSize() / 2; fleeY++) {
						if (isWalkable(fleeX, fleeY)
								&& !playerInRange(fleeX, fleeY))
							return new Node(fleeX, fleeY);
					}
				}
			}
		}
		// An error occured, no flee-position was found.
		System.out.println("No position found, where monster can flee.");
		return null;
	}

	// HELPING METHODS
	
	/**
	 * Attacks the player & gives damage
	 * 
	 * @param hasKey
	 *            does the player have the key?
	 * @return returns, whether a player is in attacking range of the monster.
	 * @author Strohbuecker, Max, 5960738
	 */
	public boolean attackPlayer(boolean hasKey) {
		// Is the player in range of the monster?
		boolean playerInRange = (Math.sqrt(Math.pow(player.getXPos()
				- getXPos(), 2)
				+ Math.pow(player.getYPos() - getYPos(), 2)) < 2);

		// Is the monster able to attack?
		boolean ableToAttack = false;
		if (type == 0)
			ableToAttack = ((System.currentTimeMillis() - lastAttack) >= cooldownAttack);
		if (type == 1)
			ableToAttack = (hasKey && ((System.currentTimeMillis() - lastAttack) >= cooldownAttack));

		if (this.getHealth() > this.getMaxHealth() / 4) {
			if (playerInRange && ableToAttack) {
				lastAttack = System.currentTimeMillis();
				player.changeHealth(-getDamage());
			}
		} else {
			move();
		}
		return playerInRange && ableToAttack;
	}
	
	/**
	 * Calculates, whether the player is in range of the monster (Needed for
	 * move() and getFleePos()).
	 * 
	 * @return returns whether the player is in range of the monster
	 * @author Strohbuecker, Max, 5960738
	 */
	public boolean playerInRange(int monsterX, int monsterY) {
		int range = 4;
		if ((player.getXPos() >= monsterX - range && player.getXPos() <= monsterX
				+ range)
				&& (player.getYPos() >= monsterY - range && player.getYPos() <= monsterY
						+ range)) {
			return true;
		} else
			return false;
	}
	
	/**
	 * Gives the monster healing or damage.
	 * 
	 * @param change
	 *            The value of the changing
	 * @author Strohbuecker, Max, 5960738
	 */
	public void changeHealth(int change) {
		super.changeHealth(change);
	}
	
	/**
	 * Saves the actual position of the player as a Node
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public void updatePlayerPos() {
		this.lastPlayerPos = new Node(this.player.getXPos(),
				this.player.getYPos());
	}
	
	/**
	 * Calculates the percentage of when the monster is able to attack again
	 * 
	 * @return returns the cooldown rate
	 * @author Strohbuecker, Max, 5960738
	 */
	public double cooldownRate() {
		return 1.0 * (System.currentTimeMillis() - lastAttack) / cooldownAttack;
	}

	// GETTER AND SETTER
	
	/**
	 * Returns the type of the monster. 0 = spawns at beginning, 1 = spawns
	 * after taking the key
	 * 
	 * @return type of the monster
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getType() {
		return type;
	}

	/**
	 * Did the monster just attack the player?
	 * 
	 * @return returns, whether the monster just attacked the player
	 * @author Strohbuecker, Max, 5960738
	 */
	public boolean justAttacked() {
		return this.justAttacked;
	}

	/**
	 * Sets, whether the monster just attacked the player.
	 * 
	 * @param attack
	 *            boolean, whether the just attacked the player
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setJustAttacked(boolean attack) {
		this.justAttacked = attack;
	}

	/**
	 * Generates a string to give out the monster with its position and actual
	 * health.
	 * 
	 * @return returns a string with minimalistic infos about the monster to
	 *         give it out
	 * @author Strohbuecker, Max, 5960738
	 */
	public String toString() {
		return "Monster: " + this.getXPos() + ", " + this.getYPos()
				+ " | Health: " + this.getHealth();
	}

	/**
	 * Returns, whether the monster carries the key or not.
	 * 
	 * @return returns, whether the monster carries the key
	 * @author Strohbuecker, Max, 5960738
	 */
	public boolean carriesKey() {
		return carriesKey;
	}

	/**
	 * Sets the attribut, whether the monster carries the key.
	 * 
	 * @param carriesKey boolean, whether the monster contains the key
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setCarriesKey(boolean carriesKey) {
		this.carriesKey = carriesKey;
	}

}