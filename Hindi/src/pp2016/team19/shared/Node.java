package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>Class Node providing parent-Node, coordinates and three arts of costs.</h1>
 * 
 * The class Node is used in the AStarAlgorithm to build a path and store
 * information for the calculation. It is also used several other times to store
 * positions.
 * 
 * @author Strohbuecker, Max, 5960738
 */
public class Node implements Serializable {

	private static final long serialVersionUID = -6653427512107474773L;

	/**
	 * Attributes of class Node.
	 * 
	 * @param parent
	 *            parent-Node of this node
	 * @param xPos
	 *            x-coordinate of this node
	 * @param yPos
	 *            y-coordinate of this node
	 * @param costFromStart
	 *            the costs how expensive it is to go from the start to this
	 *            node
	 * @param costToGoal
	 *            heuristically calculated costs how expensive it will be from
	 *            this node to the goal
	 * @param totalCosts
	 *            the totalCosts this node has (= costFromStart + costToGoal)
	 * @author Strohbuecker, Max, 5960738
	 */
	private Node parent;
	private int xPos, yPos;
	private int costFromStart, costToGoal, totalCosts;

	/**
	 * Constructor for class Node. Sets the costs to 0 and the position.
	 * 
	 * @param _x
	 *            x-coordinate of this node
	 * @param _y
	 *            y-coordinate of this node
	 * @author Strohbuecker, Max, 5960738
	 */
	public Node(int _x, int _y) {
		this.xPos = _x;
		this.yPos = _y;
		this.costFromStart = 0;
		this.costToGoal = 0;
	}

	// GETTER AND SETTER
	
	/**
	 * Set the costs, what it actually takes from the start to this node.
	 * 
	 * @param _cost
	 *            costs, how expensive it is to go from the start to this node
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setCostFromStart(int _cost) {
		this.costFromStart = _cost;
	}

	/**
	 * Returns the costs, what it actually takes from the start to this node.
	 * 
	 * @return returns the costs, how expensive it is to go from the start to
	 *         this node
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getCostFromStart() {
		return costFromStart;
	}

	/**
	 * Set the costs, what it takes to get from this node to the goal
	 * (heuristically calculated).
	 * 
	 * @param _cost
	 *            costs, how expensive it will (heuristically calculated) be
	 *            from this node to the goal
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setCostToGoal(int _cost) {
		this.costToGoal = _cost;
	}

	/**
	 * Returns the costs, what it takes to get from this node to the goal
	 * (heuristically calculated).
	 * 
	 * @return returns the costs, how expensive it will (heuristically
	 *         calculated) be from this node to the goal
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getCostToGoal() {
		return costToGoal;
	}

	/**
	 * Calculates the total costs of this node (real costs from start +
	 * heuristic costs to goal).
	 * 
	 * @author Strohbuecker, Max, 5960738
	 */
	public void calculateCosts() {
		this.totalCosts = this.costFromStart + this.costToGoal;
	}

	/**
	 * Returns the calculated total costs.
	 * 
	 * @return returns the calculated costs (real costs from start + heuristic
	 *         costs to goal)
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getTotalCosts() {
		return totalCosts;
	}

	/**
	 * Returns the x-coordinate of this node.
	 * 
	 * @return returns the x-coordinate of this node
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getXPos() {
		return xPos;
	}

	/**
	 * Returns the y-coordinate of this node.
	 * 
	 * @return returns the y-coordinate of this node
	 * @author Strohbuecker, Max, 5960738
	 */
	public int getYPos() {
		return yPos;
	}

	/**
	 * Returns the parent-node of this node
	 * 
	 * @return returns the parent-node of this node
	 * @author Strohbuecker, Max, 5960738
	 */
	public Node getParent() {
		return this.parent;
	}

	/**
	 * Sets the parent-node of this node
	 * 
	 * @param _parent
	 *            node which should be the parent of this node
	 * @author Strohbuecker, Max, 5960738
	 */
	public void setParent(Node _parent) {
		this.parent = _parent;
	}
}
