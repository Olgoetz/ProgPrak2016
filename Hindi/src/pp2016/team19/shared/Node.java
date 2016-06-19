package pp2016.team19.shared;

/**
* Class Node is used in the AStar-algorithm. it has a parent-Node, coordinates and three arts of costs.
* @author Strohbuecker, Max, 5960738 */
public class Node {
	private Node parent;
	private int xPos, yPos;
	private int costFromStart, costToGoal, calculatedCosts;
	
	public Node(int _x, int _y){
		this.xPos = _x;
		this.yPos = _y;
		this.costFromStart = 0;
		this.costToGoal = 0;
	}
	
	// Getter and Setter
	public void setCostFromStart(int _cost){
		this.costFromStart = _cost;
	}
	public int getCostFromStart(){
		return costFromStart;
	}
	public void setCostToGoal(int _cost){
		this.costToGoal = _cost;
	}
	public int getCostToGoal(){
		return costToGoal;
	}
	public void calculateCosts(){
		this.calculatedCosts = this.costFromStart + this.costToGoal;
	}
	public int getCalculatedCosts(){
		return calculatedCosts;
	}
	public int getXPos() {
		return xPos;
	}
	public int getYPos() {
		return yPos;
	}
	public Node getParent() {
		return this.parent;
	}
	public void setParent(Node _parent) {
		this.parent = _parent;
	}
}
