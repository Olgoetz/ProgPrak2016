package pp2016.team19.shared;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import pp2016.team19.client.gui.GameWindow;
import pp2016.team19.server.engine.Game;

/**
 * <h1>Abstract Character Class providing position, image, direction, window,
 * health & damage.</h1>
 * 
 * It contains the constructor, the A-Star-Search algorithm, move methods,
 * methods to calculate the next steps and whether steps are valid or walkable,
 * as well as getter and setter methods.
 * 
 * @author Strohbuecker, Max, 5960738
 */
public abstract class Character implements Serializable{

	
	private static final long serialVersionUID = -6464021522368997893L;
	public  int  xPos, yPos;
	private transient Image image;
	private int dir = -1; // Running direction: 0 North, 1 East, 2 South, 3 West

	private transient Game game;

	public int health;
	public int ID;
	private int damage;

	private int maxHealth;

	/**
	 * Standard-Contructor of class Character
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public Character() {

	}

	/**
	 * Contructor of class Character
	 * 
	 * @param window
	 *            contains the level/map
	 * @author Strohbuecker, Max, 5960738
	 */
	public Character(Game game) {
		this.game = game;
	}

	/**
	 * A-Star-Algorithm to search the shortest path from start to goal.
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
	public LinkedList<Node> AStarSearch(int xStart, int yStart, int xGoal, int yGoal) {
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
			if (!game.getGameMap()[actX][actY - 1].isRock())
				neighbors[0] = new Node(actX, actY - 1); // up
			else
				neighbors[0] = null;

			// Right-Neighbor valid?
			if (!game.getGameMap()[actX + 1][actY].isRock())
				neighbors[1] = new Node(actX + 1, actY); // right
			else
				neighbors[1] = null;

			// Down-Neighbor valid?
			if (!game.getGameMap()[actX][actY + 1].isRock())
				neighbors[2] = new Node(actX, actY + 1); // down
			else
				neighbors[2] = null;

			// Left-Neighbor valid?
			if (!game.getGameMap()[actX - 1][actY].isRock())
				neighbors[3] = new Node(actX - 1, actY); // left
			else
				neighbors[3] = null;

			// Check all Neighbors
			for (int k = 0; k < 4; k++) {

				if (neighbors[k] == null)
					continue;

				// Set neighbors' parents to cheapest
				neighbors[k].setParent(cheapest);

				if (neighbors[k].getXPos() == xGoal && neighbors[k].getYPos() == yGoal) {
					// Current Neighbor is the goal / player
					goalFound = true;
					goal = neighbors[k];
				} else {
					// Calculate costs of neighbor
					neighbors[k].setCostFromStart(cheapest.getCostFromStart() + 1);
					int xDiff = xGoal - neighbors[k].getXPos();
					int yDiff = yGoal - neighbors[k].getYPos();
					neighbors[k].setCostToGoal((int) Math.sqrt(xDiff * xDiff + yDiff * yDiff));
					neighbors[k].calculateCosts();

					boolean skipNode = false;
					for (Node checkOpen : openList) {
						if (checkOpen.getXPos() == neighbors[k].getXPos()
								&& checkOpen.getYPos() == neighbors[k].getYPos()) {
							// Neighbor already in the openList
							if (checkOpen.getTotalCosts() <= neighbors[k].getTotalCosts())
								skipNode = true;
						}
					}
					for (Node checkClosed : closedList) {
						if (checkClosed.getXPos() == neighbors[k].getXPos()
								&& checkClosed.getYPos() == neighbors[k].getYPos()) {
							// Neighbor already in the closedList
							if (checkClosed.getTotalCosts() <= neighbors[k].getTotalCosts())
								skipNode = true;
						}
					}
					if (!skipNode)
						openList.add(neighbors[k]);
				}
			} // end: for-neighbors
			closedList.add(cheapest);
		} // end: while

		// Go back path to node which parent is the start node (=monster)
		LinkedList<Node> path = new LinkedList<Node>();
		Node current = goal.getParent();
		while (current != start && current != null) {
			path.addFirst(current);
			current = current.getParent();
		}

		return path;
	}

	// Move-Methods
	
	/**
	 * Method to move up the character
	 * @author Strohbuecker, Max, 5960738
	 */
	public synchronized void moveUp() {
		yPos--;
	}

	/**
	 * Method to move down the character
	 * @author Strohbuecker, Max, 5960738
	 */
	public synchronized void moveDown() {
		yPos++;
	}

	public synchronized void moveLeft() {
		xPos--;
	}

	public synchronized void moveRight() {
		xPos++;
	}

	/**
	 * Removes the next Step of the AStarPath and changes the running direction
	 * of the monster
	 * 
	 * @param path
	 *            the path, of which the next step should be calculated
	 * @author Strohbuecker, Max, 5960738
	 */
	public void changeDir(LinkedList<Node> path) {
		Node nextNode = path.removeFirst();
		// Node test;
		// System.out.println("PATH:");
		// for (int i=0; i<path.size();i++){
		// test = path.get(i);
		// System.out.println(i+". " + test.getXPos() + "/" + test.getYPos());
		// }
		if (nextNode.getXPos() == this.getXPos() && nextNode.getYPos() == this.getYPos() - 1) {
			dir = 0;
		} else if (nextNode.getXPos() == this.getXPos() + 1 && nextNode.getYPos() == this.getYPos()) {
			dir = 1;
		} else if (nextNode.getXPos() == this.getXPos() && nextNode.getYPos() == this.getYPos() + 1) {
			dir = 2;
		} else if (nextNode.getXPos() == this.getXPos() - 1 && nextNode.getYPos() == this.getYPos()) {
			dir = 3;
		} else {
			System.out.println("Error while changing direction: Next step is not next to the monster");
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
		if (game.getGameMap()[x][y].isRock()) {
			return false;
		} else {
			return true;
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
			return isWalkable(getXPos(), getYPos() - 1);
		} else if (dir == 1 && getXPos() + 1 < game.getGameSize()) {
			return isWalkable(getXPos() + 1, getYPos());
		} else if (dir == 2 && getYPos() + 1 < game.getGameSize()) {
			return isWalkable(getXPos(), getYPos() + 1);
		} else if (dir == 3 && getXPos() > 0) {
			return isWalkable(getXPos() - 1, getYPos());
		} else {
			System.out.println("Error while validating step: Next step blocked by a wall, door or key.");
			return false;
		}
	}

	// Getter and Setter

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	public void changeHealth(int change) {
		health = Math.min(health + change, getMaxHealth());
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image img) {
		image = img;
	}

	public void setPos(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public int getYPos() {
		return yPos;
	}

	public int getXPos() {
		return xPos;
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}

}
