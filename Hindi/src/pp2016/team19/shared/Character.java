package pp2016.team19.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import pp2016.team19.server.engine.Game;

/**
 * <h1>Abstract Character Class providing position, direction, game, health, ID
 * & damage.</h1>
 * 
 * It contains the constructor, the A-Star-Search algorithm, move methods,
 * methods to calculate the next steps and whether steps are valid or walkable,
 * as well as getter and setter methods.
 * 
 * @author Strohbuecker, Max, 5960738
 */
public abstract class Character implements Serializable {

	private static final long serialVersionUID = -6464021522368997893L;

	/**
	 * Attributes of class Character.
	 * 
	 * xPos: x-coordinate of the characters position // yPos: y-coordinate of
	 * the characters position // dir: direction, where the character should go
	 * next // game: game which the character belongs to / exists in // health:
	 * health of the player // ID: ID of the character to uniquely identify it
	 * // damage: damage the character deals // maxHealth: the maximum health
	 * the character should have
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */

	public int xPos, yPos;
	private int dir = -1; // Running direction: 0 North, 1 East, 2 South, 3 West

	private transient Game game;

	public int health;
	public int ID;
	private int damage;

	private int maxHealth;

	/**
	 * Standard-Contructor of class Character.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public Character() {

	}

	/**
	 * Contructor of class Character.
	 * 
	 * @param game
	 *            contains the level/map & other gameinfos
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
			if (actY - 1 > 0 && !game.getGameMap()[actX][actY - 1].isRock())
				neighbors[0] = new Node(actX, actY - 1); // up
			else
				neighbors[0] = null;

			// Right-Neighbor valid?
			if (actX + 1 < getGame().getGameSize()
					&& !game.getGameMap()[actX + 1][actY].isRock())
				neighbors[1] = new Node(actX + 1, actY); // right
			else
				neighbors[1] = null;

			// Down-Neighbor valid?
			if (actY + 1 < getGame().getGameSize()
					&& !game.getGameMap()[actX][actY + 1].isRock())
				neighbors[2] = new Node(actX, actY + 1); // down
			else
				neighbors[2] = null;

			// Left-Neighbor valid?
			if (actX - 1 > 0 && !game.getGameMap()[actX - 1][actY].isRock())
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
					int neighborX = neighbors[k].getXPos();
					int neighborY = neighbors[k].getYPos();
					if (this.getGame().getGameMap()[neighborX][neighborY]
							.containsPlayer()) {
						neighbors[k].setCostFromStart(cheapest
								.getCostFromStart() + 999);
					} else {
						neighbors[k].setCostFromStart(cheapest
								.getCostFromStart() + 1);
					}
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
			} // end: for-neighbors
			closedList.add(cheapest);
		} // end: while

		// Go back path to node which parent is the start node (=monster)
		LinkedList<Node> path = new LinkedList<Node>();
		if (goal == null) {
			System.out.println("METHOD Character.AStarSearch: Goal not found!");
			return null;
		} else {
			Node current = goal.getParent();
			while (current != start && current != null) {
				path.addFirst(current);
				current = current.getParent();
			}
		}
		return path;
	}

	// MOVE-METHODS

	/**
	 * Method to move up the character & update the new position to the map.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public synchronized void moveUp() {
		if (this instanceof Player) {
			this.getGame().getGameMap()[xPos][yPos].setContainsPlayer(false);
			this.getGame().getGameMap()[xPos][yPos - 1].setContainsPlayer(true);
		} else if (this instanceof Monster) {
			this.getGame().getGameMap()[xPos][yPos].setContainsMonster(false);
			this.getGame().getGameMap()[xPos][yPos - 1]
					.setContainsMonster(true);
		}
		yPos--;
	}

	/**
	 * Method to move down the character & update the new position to the map.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public synchronized void moveDown() {
		if (this instanceof Player) {
			this.getGame().getGameMap()[xPos][yPos].setContainsPlayer(false);
			this.getGame().getGameMap()[xPos][yPos + 1].setContainsPlayer(true);
		} else if (this instanceof Monster) {
			this.getGame().getGameMap()[xPos][yPos].setContainsMonster(false);
			this.getGame().getGameMap()[xPos][yPos + 1]
					.setContainsMonster(true);
		}
		yPos++;
	}

	/**
	 * Method to move left the character & update the new position to the map.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public synchronized void moveLeft() {
		if (this instanceof Player) {
			this.getGame().getGameMap()[xPos][yPos].setContainsPlayer(false);
			this.getGame().getGameMap()[xPos - 1][yPos].setContainsPlayer(true);
		} else if (this instanceof Monster) {
			this.getGame().getGameMap()[xPos][yPos].setContainsMonster(false);
			this.getGame().getGameMap()[xPos - 1][yPos]
					.setContainsMonster(true);
		}
		xPos--;
	}

	/**
	 * Method to move right the character & update the new position to the map.
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public synchronized void moveRight() {
		if (this instanceof Player) {
			this.getGame().getGameMap()[xPos][yPos].setContainsPlayer(false);
			this.getGame().getGameMap()[xPos + 1][yPos].setContainsPlayer(true);
		} else if (this instanceof Monster) {
			this.getGame().getGameMap()[xPos][yPos].setContainsMonster(false);
			this.getGame().getGameMap()[xPos + 1][yPos]
					.setContainsMonster(true);
		}
		xPos++;
	}

	// HELPING METHODS

	/**
	 * Removes the next step/node of the AStarPath and changes the running
	 * direction of the monster.
	 * 
	 * @param path
	 *            the path, of which the next step/node should be calculated
	 * @author Strohbuecker, Max, 5960738
	 */
	public boolean changeDir(LinkedList<Node> path) {
		Node nextNode = path.removeFirst();
		// Node test;
		// System.out.println("PATH:");
		// for (int i=0; i<path.size();i++){
		// test = path.get(i);
		// System.out.println(i+". " + test.getXPos() + "/" + test.getYPos());
		// }

		if (this.getGame().getGameMap()[nextNode.getXPos()][nextNode.getYPos()]
				.containsMonster()) {
			path.clear();
			return false;
		} else if (nextNode.getXPos() == this.getXPos()
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
		return true;
	}

	/**
	 * Calculates, whether the target position is walkable.
	 * 
	 * @param x
	 *            x-coordinate of the target position
	 * @param y
	 *            y-coordinate of the target position
	 * @return returns true if field is free, returns false if field is a wall
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
	 * @return returns false if the next step is not walkable or out of the map
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
			System.out
					.println("Error while validating step: Next step blocked by a wall or out of the map.");
			return false;
		}
	}

	// GETTER AND SETTER

	/**
	 * Gets the maximum health of the character.
	 * 
	 * @return returns the maximum health of the character
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * Sets the maximum health of the character.
	 * 
	 * @param maxHealth
	 *            the maximum health the character should have
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	/**
	 * Gets the damage which the character deals.
	 * 
	 * @return returns the damage which the character deals
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Sets the damage which the character should deal.
	 * 
	 * @param damage
	 *            the damage which the character should deal
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * Changes the health of the player (needed for regenerating health or
	 * dealing damage).
	 * 
	 * @param change
	 *            the value how much health the character should gain or lose
	 * @author Strohbuecker, Max, 5960738
	 */
	public void changeHealth(int change) {
		health = Math.min(health + change, getMaxHealth());
	}

	/**
	 * Sets the health of the character.
	 * 
	 * @param health
	 *            the value how much health the character should have
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Gets the health of the character.
	 * 
	 * @return returns the actual health of the character
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Sets the position of the character.
	 * 
	 * @param xPos
	 *            the x-coordinate of the position
	 * @param yPos
	 *            the y-coordinate of the position
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setPos(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	/**
	 * Gets the y-coordinate of the character.
	 * 
	 * @return returns the y-coordinate of the character
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getYPos() {
		return yPos;
	}

	/**
	 * Gets the x-coordinate of the character.
	 * 
	 * @return returns the x-coordinate of the character
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getXPos() {
		return xPos;
	}

	/**
	 * Gets the game, in which the character exists.
	 * 
	 * @return returns the game in which the character exists
	 * @author Strohbuecker, Max, 5960738
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Sets the game, in which the character exists.
	 * 
	 * @param game
	 *            the game in which the character exists
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setGame(Game game) {
		this.game = game;
	}

}
