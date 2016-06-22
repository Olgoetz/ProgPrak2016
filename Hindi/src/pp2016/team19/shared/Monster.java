package pp2016.team19.shared;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import pp2016.team19.client.gui.GameWindow;

/**
 * Class Monster describes every logic of the monsters, how they fight, how they
 * search a way to the player, how they flee, and so on.
 * 
 * @author Strohbuecker, Max, 5960738
 */
public class Monster extends Character {

	private long lastAttack;
	private long lastStep;
	private int cooldownAttack;
	private int cooldownWalk;
	
	private int actAction; // Defines what action the monster should do: 0 move to player, 1 flee, 2 regenerate

	private int[] lastPlayerPos;
	private LinkedList<Node> pathToPlayer;
	private LinkedList<Node> fleePath;

	private int dir; // Running direction: 0 North, 1 East, 2 South, 3 West
	private int type; // Present from beginning: 0, Appears later: 1

	private GameWindow window;

	// Spaeter ueber labyrinth abrufbar!
	private final int HEIGHT = 16;
	private final int WIDTH = 16;
	// private Labyrinth labyrinth;

	private Player player;

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
	public Monster(int x, int y, GameWindow window, int type) {
		this.window = window;
		this.player = window.player;
		this.type = type;
		setPos(x, y);
		setHealth(32);
		setMaxHealth(getHealth());
		lastAttack = System.currentTimeMillis();
		lastStep = System.currentTimeMillis();
		cooldownAttack = 500 - 10 * window.currentLevel; // ms
		cooldownWalk = 1000;
		lastPlayerPos = new int[2];
		lastPlayerPos[0] = -1;
		lastPlayerPos[1] = -1;
		pathToPlayer = new LinkedList<Node>();
		fleePath = new LinkedList<Node>();
		actAction = -1;

		setDamage(5 + window.currentLevel * 2);
		Random r = new Random();

		// Load image for monster
		int i = r.nextInt(3) + 1;

		try {
			setImage(ImageIO.read(new File("img//dragon" + i + ".png")));
		} catch (IOException e) {
			System.err.print("Error while loading the image dragon" + i
					+ ".png.");
		}
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
//		boolean attacked = false;

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
//				attacked = true;
			}
		}else{
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
			window.level[getXPos()][getYPos()] = new Potion(30);
			window.monsterList.remove(this);
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
				if (this.getHealth() > this.getMaxHealth()/4)
					moveToPlayer();
				else
					flee();
			} else{
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
		if (actAction != 0 || pathToPlayer.isEmpty() || player.getXPos() != this.lastPlayerPos[0]
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
	 * @return returns an int-array with the fleeing position
	 * @author Strohbuecker, Max, 5960738
	 */
	public Node getFleePos() {
		
		if (this.getXPos() < WIDTH/2 || this.getXPos() >= player.getXPos()){
			if (this.getYPos() < HEIGHT/2 || this.getYPos() >= player.getYPos()){
				// Monster in top left quartal, flee to down right corner
				for (int fleeX=WIDTH-1; fleeX>=WIDTH/2; fleeX--){
					for (int fleeY=HEIGHT-1; fleeY>=HEIGHT/2; fleeY--){
						if (isWalkable(fleeX,fleeY))
							return new Node(fleeX,fleeY);
					}
				}
			}else if (this.getYPos() >= HEIGHT/2 || this.getYPos() < player.getYPos()){
				// Monster in down left quartal, flee to top right corner
				for (int fleeX=WIDTH-1; fleeX>=WIDTH/2; fleeX--){
					for (int fleeY=0; fleeY<HEIGHT/2; fleeY++){
						if (isWalkable(fleeX,fleeY))
							return new Node(fleeX,fleeY);
					}
				}
			}
		}else if (this.getXPos() >= WIDTH/2 || this.getXPos() < player.getXPos()){
			if (this.getYPos() < HEIGHT/2 || this.getYPos() >= player.getYPos()){
				// Monster in top right quartal, flee to down left corner
				for (int fleeX=0; fleeX<WIDTH/2; fleeX++){
					for (int fleeY=HEIGHT-1; fleeY>=HEIGHT/2; fleeY--){
						if (isWalkable(fleeX,fleeY))
							return new Node(fleeX,fleeY);
					}
				}
			}else if (this.getYPos() >= HEIGHT/2 || this.getYPos() < player.getYPos()){
				// Monster in down right quartal, flee to top left corner
				for (int fleeX=0; fleeX<WIDTH/2; fleeX++){
					for (int fleeY=0; fleeY<HEIGHT/2; fleeY++){
						if (isWalkable(fleeX,fleeY))
							return new Node(fleeX,fleeY);
					}
				}
			}
		}
		// An error occured, no flee-position was found.
		System.out.println("No position found, where monster can flee.");
		return null;
	}

	/**
	 * A-Star-Algorithm to search for the player.
	 * 
	 * @param xStart
	 *            x-coordinate of the starting point
	 * @param yStart
	 *            y-coordinate of the starting point
	 * @param xGoal
	 *            x-coordinate of the goal
	 * @param yGoal
	 *            x-coordinate of the goal
	 * @return returns the calculated path
	 * @author Strohbuecker, Max, 5960738
	 */
	public LinkedList<Node> AStarSearch(int xStart, int yStart, int xGoal,
			int yGoal) {
		ArrayList<Node> openList = new ArrayList<Node>();
		ArrayList<Node> closedList = new ArrayList<Node>();
		Node start = new Node(xStart, yStart);
		start.calculateCosts();
		Node goal = null;

		openList.add(start);
		Node cheapest = null;
		boolean goalFound = false;
		while (!openList.isEmpty() && !goalFound) {
			// Find the cheapest node in the openList
			cheapest = openList.get(0);
			for (Node node : openList) {
				if (node.getTotalCosts() < cheapest.getTotalCosts()) {
					cheapest = node;
				}
			}
			openList.remove(cheapest);

			// Create the four neighbors (up,down,left,right)
			Node[] neighbors = new Node[4];
			int actX = cheapest.getXPos();
			int actY = cheapest.getYPos();

			// Up-Neighbor valid?
			if (!(window.level[actX][actY - 1] instanceof Wall))
				neighbors[0] = new Node(actX, actY - 1); // up
			else
				neighbors[0] = null;

			// Right-Neighbor valid?
			if (!(window.level[actX + 1][actY] instanceof Wall))
				neighbors[1] = new Node(actX + 1, actY); // right
			else
				neighbors[1] = null;

			// Down-Neighbor valid?
			if (!(window.level[actX][actY + 1] instanceof Wall))
				neighbors[2] = new Node(actX, actY + 1); // down
			else
				neighbors[2] = null;

			// Left-Neighbor valid?
			if (!(window.level[actX - 1][actY] instanceof Wall))
				neighbors[3] = new Node(actX - 1, actY); // left
			else
				neighbors[3] = null;

			// Check all Neighbors
			for (int k = 0; k < 4; k++) {

				if (neighbors[k] == null)
					continue;

				// Set neighbors' parents to cheapest
				neighbors[k].setParent(cheapest);

				if (neighbors[k].getXPos() == xGoal
						&& neighbors[k].getYPos() == yGoal) {
					// Current Neighbor is the goal / player
					goalFound = true;
					goal = neighbors[k];
				} else {
					// Calculate costs of neighbor
					neighbors[k]
							.setCostFromStart(cheapest.getCostFromStart() + 1);
					int xDiff = xGoal - neighbors[k].getXPos();
					int yDiff = yGoal - neighbors[k].getYPos();
					neighbors[k].setCostToGoal((int) Math.sqrt(xDiff * xDiff
							+ yDiff * yDiff));
					neighbors[k].calculateCosts();

					boolean skipNode = false;
					for (Node checkOpen : openList) {
						if (checkOpen.getXPos() == neighbors[k].getXPos()
								&& checkOpen.getYPos() == neighbors[k]
										.getYPos()) {
							// Neighbor already in the openList
							if (checkOpen.getTotalCosts() <= neighbors[k]
									.getTotalCosts())
								skipNode = true;
						}
					}
					for (Node checkClosed : closedList) {
						if (checkClosed.getXPos() == neighbors[k].getXPos()
								&& checkClosed.getYPos() == neighbors[k]
										.getYPos()) {
							// Neighbor already in the closedList
							if (checkClosed.getTotalCosts() <= neighbors[k]
									.getTotalCosts())
								skipNode = true;
						}
					}
					if (!skipNode)
						openList.add(neighbors[k]);
				}
			}// end: for-neighbors
			closedList.add(cheapest);
		}// end: while

		// Go back path to node which parent is the start node (=monster)
		LinkedList<Node> path = new LinkedList<Node>();
		Node current = goal.getParent();
		while (current != start && current != null) {
			path.addFirst(current);
			current = current.getParent();
		}

		return path;
	}

	/**
	 * Calculates, whether the target position is walkable for a monster.
	 * 
	 * @param x
	 *            x-coordinate of the target
	 * @param y
	 *            y-coordinate of the target
	 * @return returns true if field is free, returns false if field is a wall,
	 *         key or door
	 * @author Strohbuecker, Max, 5960738
	 */
	public boolean isWalkable(int x, int y) {
		if (window.level[x][y] instanceof Wall
				|| window.level[x][y] instanceof Key
				|| window.level[x][y] instanceof Door) {
			return false;
		} else {
			return true;
		}
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
	 * Removes the next Step of the AStarPath and changes the running direction
	 * of the monster
	 * 
	 * @param path the path, of which the next step should be calculated
	 * @author Strohbuecker, Max, 5960738
	 */
	public void changeDir(LinkedList<Node> path) {
		Node nextNode = path.removeFirst();
//		Node test;
//		System.out.println("PATH:");
//		for (int i=0; i<path.size();i++){
//			test = path.get(i);
//			System.out.println(i+". " + test.getXPos() + "/" + test.getYPos());
//		}
		if (nextNode.getXPos() == this.getXPos()
				&& nextNode.getYPos() == this.getYPos() - 1) {
			dir = 0;
		} else if (nextNode.getXPos() == this.getXPos() + 1
				&& nextNode.getYPos() == this.getYPos()) {
			dir = 1;
		} else if (nextNode.getXPos() == this.getXPos()
				&& nextNode.getYPos() == this.getYPos() + 1) {
			dir = 2;
		} else if (nextNode.getXPos() == this.getXPos() - 1
				&& nextNode.getYPos() == this.getYPos()) {
			dir = 3;
		} else {
			System.out
					.println("Error while changing direction: Next step is not next to the monster");
		}
		if (valid()) {
			switch (dir) {
			case 0:
				moveUp();
				break;
			case 1:
				moveRight();
				break;
			case 2:
				moveDown();
				break;
			case 3:
				moveLeft();
				break;
			}
		}
	}
	
	/**
	 * Checks, whether the next step is valid.
	 * 
	 * @return returns false if the next step is blocked by a wall, door or key
	 * @author Strohbuecker, Max, 5960738
	 */
	private boolean valid() {
		if (dir == -1)
			return true;

		if (dir == 0 && getYPos() - 1 > 0) {
			return !(window.level[getXPos()][getYPos() - 1] instanceof Wall)
					&& !(window.level[getXPos()][getYPos() - 1] instanceof Door)
					&& !(window.level[getXPos()][getYPos() - 1] instanceof Key);
		} else if (dir == 1 && getXPos() + 1 < window.WIDTH) {
			return !(window.level[getXPos() + 1][getYPos()] instanceof Wall)
					&& !(window.level[getXPos() + 1][getYPos()] instanceof Door)
					&& !(window.level[getXPos() + 1][getYPos()] instanceof Key);
		} else if (dir == 2 && getYPos() + 1 < window.HEIGHT) {
			return !(window.level[getXPos()][getYPos() + 1] instanceof Wall)
					&& !(window.level[getXPos()][getYPos() + 1] instanceof Door)
					&& !(window.level[getXPos()][getYPos() + 1] instanceof Key);
		} else if (dir == 3 && getXPos() > 0) {
			return !(window.level[getXPos() - 1][getYPos()] instanceof Wall)
					&& !(window.level[getXPos() - 1][getYPos()] instanceof Door)
					&& !(window.level[getXPos() - 1][getYPos()] instanceof Key);
		} else {
			System.out
					.println("Error while validating step: Next step blocked by a wall, door or key.");
			return false;
		}
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

}
