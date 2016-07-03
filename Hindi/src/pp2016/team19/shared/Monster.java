package pp2016.team19.shared;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import pp2016.team19.client.gui.GameWindow;
import pp2016.team19.server.engine.Game;

/**
 * Class Monster describes every logic of the monsters, how they fight, how they
 * search a way to the player, how they flee, and so on.
 * 
 * @author Strohbuecker, Max, 5960738
 */
public class Monster extends Character {

	private static final long serialVersionUID = -8329575206755045242L;
	
	private long lastAttack;
	private long lastStep;
	private int cooldownAttack;
	private int cooldownWalk;

	private int actAction; // Defines what action the monster should do: 0 move
							// to player, 1 flee, 2 regenerate

	private int[] lastPlayerPos;
	private LinkedList<Node> pathToPlayer;
	private LinkedList<Node> fleePath;
	
	private boolean justAttacked = false;

	private int type; // Present from beginning: 0, Appears later: 1

	private Player player;
	private Game game;

	// // PERSOENLICHE NOTIZ: Spaeter statt GameWindow Laybrinth als Input
	/**
	 * Contructor of the class Monster.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 * @param x
	 *            Coordinate, where the monster should spawn
	 * @param y
	 *            Coordinate, where the monster should spawn
	 * @param window
	 *            contains the gamefield/map and the player data
	 * @param type
	 *            Type of the monster (0 = spawns at the beginning, 1 = spawns
	 *            after taking the key)
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
		lastPlayerPos = new int[2];
		lastPlayerPos[0] = -1;
		lastPlayerPos[1] = -1;
		pathToPlayer = new LinkedList<Node>();
		fleePath = new LinkedList<Node>();
		actAction = -1;

		setDamage(5 + game.getLevelNumber() * 2);
	}

	/**
	 * Saves the actual position of the player in an array * @author
	 * Strohbuecker, Max, 5960738
	 */
	public void updatePlayerPos() {
		this.lastPlayerPos[0] = this.player.getXPos();
		this.lastPlayerPos[1] = this.player.getYPos();
	}

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

		// Did the monster attack or is it weak?
		// boolean attacked = false;

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
				// attacked = true;
			}
		} else {
			move();
		}
		return playerInRange;
	}

	/**
	 * Gives the monster healing/damage. If health <= 0, the monster is deleted.
	 * 
	 * @param change
	 *            The value of the changing
	 * @author Strohbuecker, Max, 5960738
	 */
	public void changeHealth(int change) {
		super.changeHealth(change);
		if (getHealth() <= 0) {
			game.getGameMap()[getXPos()][getYPos()].setContainsPotion(true);
			game.getMonsters().remove(this);
		}
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

	// <<<<<<<<<< FSM >>>>>>>>>>

	public void move() {
		boolean nextWalk = (System.currentTimeMillis() - lastStep) >= cooldownWalk;
		if (nextWalk) {
			if (playerInRange()) {
				if (this.getHealth() > this.getMaxHealth() / 4)
					moveToPlayer();
				else
					flee();
			} else {
				changeHealth(5);
				actAction = 2;
			}

			lastStep = System.currentTimeMillis();
		}
	}

	/**
	 * Moves the monster to the player. If the playerPos has changed, it calls
	 * the AStar to calculate a path to him and the monster goes the first step
	 * of the path. Otherwise the monster should go further the calculated path.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public void moveToPlayer() {

		// Did the player move since the last route calculation?
		if (actAction != 0 || pathToPlayer.isEmpty()
				|| player.getXPos() != this.lastPlayerPos[0]
				|| player.getYPos() != this.lastPlayerPos[1]) {
			pathToPlayer.clear();
			pathToPlayer = AStarSearch(this.getXPos(), this.getYPos(),
					player.getXPos(), player.getYPos());
			updatePlayerPos();
		}

		changeDir(pathToPlayer);
		actAction = 0;
	}

	/**
	 * Compares the position of the player to the position of the monster and
	 * moves the monster in the other way.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public void flee() {

		if (actAction != 1 || fleePath.isEmpty()) {
			Node fleePos = getFleePos();
			System.out.println("Flee to: " + fleePos.getXPos() + ", "
					+ fleePos.getYPos());
			fleePath.clear();
			fleePath = AStarSearch(this.getXPos(), this.getYPos(),
					fleePos.getXPos(), fleePos.getYPos());
		}

		changeDir(fleePath);
		actAction = 1;
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
				// Monster is under and right of the player, flee to down right corner
				for (int fleeX = game.getGameSize() - 1; fleeX >= game.getGameSize() / 2; fleeX--) {
					for (int fleeY = game.getGameSize() - 1; fleeY >= game.getGameSize() / 2; fleeY--) {
						if (isWalkable(fleeX, fleeY))
							return new Node(fleeX, fleeY);
					}
				}
			} else if (this.getYPos() < player.getYPos()) {
				// Monster is above and right of the player, flee to top right corner
				for (int fleeX = game.getGameSize() - 1; fleeX >= game.getGameSize() / 2; fleeX--) {
					for (int fleeY = 0; fleeY < game.getGameSize() / 2; fleeY++) {
						if (isWalkable(fleeX, fleeY))
							return new Node(fleeX, fleeY);
					}
				}
			}
		} else if (this.getXPos() < player.getXPos()) {
			if (this.getYPos() >= player.getYPos()) {
				// Monster is under and left of the player, flee to down left corner
				for (int fleeX = 0; fleeX < game.getGameSize() / 2; fleeX++) {
					for (int fleeY = game.getGameSize() - 1; fleeY >= game.getGameSize() / 2; fleeY--) {
						if (isWalkable(fleeX, fleeY))
							return new Node(fleeX, fleeY);
					}
				}
			} else if (this.getYPos() < player.getYPos()) {
				// Monster is above and left of the player, flee to top left corner
				for (int fleeX = 0; fleeX < game.getGameSize() / 2; fleeX++) {
					for (int fleeY = 0; fleeY < game.getGameSize() / 2; fleeY++) {
						if (isWalkable(fleeX, fleeY))
							return new Node(fleeX, fleeY);
					}
				}
			}
		}
		// An error occured, no flee-position was found.
		System.out.println("No position found, where monster can flee.");
		return null;
	}

	/**
	 * Calculates, whether the player is in range of the monster (Needed for
	 * move()).
	 * 
	 * @return returns true if the player is in range of the monster, returns
	 *         false if not
	 * @author Strohbuecker, Max, 5960738
	 */
	public boolean playerInRange() {
		int range = 4;
		if ((player.getXPos() >= this.getXPos() - range && player.getXPos() <= this
				.getXPos() + range)
				&& (player.getYPos() >= this.getYPos() - range && player
						.getYPos() <= this.getYPos() + range)) {
			return true;
		} else
			return false;
	}

	/**
	 * Returns the type of the monster. 0 - spawns at beginning, 1 - spawns
	 * after taking the key
	 * 
	 * @return type of the monster
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getType() {
		return type;
	}
	
	public boolean justAttacked(){
		return this.justAttacked;
	}
	
	public void setJustAttacked(boolean attack){
		this.justAttacked = attack;
	}
	
	public String toString() {
		return "Monster: " + this.getXPos() + ", " + this.getYPos() + " | Health: " + this.getHealth();
	}

}