package pp2016.team19.shared;

import java.io.Serializable;

/**
* Class Node is used in the AStar-algorithm. it has a parent-Node, coordinates and three arts of costs.
* @author Strohbuecker, Max, 5960738 */
public class Node implements Serializable {

	private static final long serialVersionUID = -6653427512107474773L;
	private Node parent;
	private int xPos, yPos;
	private int costFromStart, costToGoal, totalCosts;
	
	/**
	* Constructor for class Node
	* @author Strohbuecker, Max, 5960738 */
	public Node(int _x, int _y){
		this.xPos = _x;
		this.yPos = _y;
		this.costFromStart = 0;
		this.costToGoal = 0;
	}
	
	/**
	* Set the costs, what it actually takes from the start to this node.
	* @author Strohbuecker, Max, 5960738 */
	public void setCostFromStart(int _cost){
		this.costFromStart = _cost;
	}
	/**
	* Returns the costs, what it actually takes from the start to this node.
	* @author Strohbuecker, Max, 5960738 */
	public int getCostFromStart(){
		return costFromStart;
	}
	/**
	* Set the costs, what it takes to get from this node to the goal (heuristically calculated)
	* @author Strohbuecker, Max, 5960738 */
	public void setCostToGoal(int _cost){
		this.costToGoal = _cost;
	}
	/**
	* Returns the costs, what it takes to get from this node to the goal (heuristically calculated)
	* @author Strohbuecker, Max, 5960738 */
	public int getCostToGoal(){
		return costToGoal;
	}
	/**
	* Calculates the total costs of this node (real costs from start + heuristic costs to goal)
	* @author Strohbuecker, Max, 5960738 */
	public void calculateCosts(){
		this.totalCosts = this.costFromStart + this.costToGoal;
	}
	/**
	* Returns the calculated total costs
	* @author Strohbuecker, Max, 5960738 */
	public int getTotalCosts(){
		return totalCosts;
	}
	/**
	* Returns the x-coordinate of this node
	* @author Strohbuecker, Max, 5960738 */
	public int getXPos() {
		return xPos;
	}
	/**
	* Returns the y-coordinate of this node
	* @author Strohbuecker, Max, 5960738 */
	public int getYPos() {
		return yPos;
	}
	/**
	* Returns the parent-node of this node
	* @author Strohbuecker, Max, 5960738 */
	public Node getParent() {
		return this.parent;
	}
	/**
	* Set the parent-node of this node
	* @author Strohbuecker, Max, 5960738 */
	public void setParent(Node _parent) {
		this.parent = _parent;
	}
}
