package pp2016.team19.shared;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import pp2016.team19.client.gui.GameWindow;

public class Monster extends Character {

	private long lastAttack;
	private long lastStep;
	private int cooldownAttack;
	private int cooldownWalk;
	
	private int dir; // Running direction: 0 North, 1 East, 2 South, 3 West
	private int type; // Present from beginning: 0, Appears later: 1
	
	private GameWindow window;
	private Player player;
	
	public Monster(int x, int y, GameWindow window, int type){
		this.window = window;
		this.player = window.player;
		this.type = type;
		setPos(x,y);
		setHealth(32);
		setMaxHealth(getHealth());
		lastAttack = System.currentTimeMillis();
		lastStep = System.currentTimeMillis();
		cooldownAttack = 500 - 10 * window.currentLevel; // ms
		cooldownWalk = 1000;
		
		
		
		setDamage(5 + window.currentLevel * 2);
		Random r = new Random();
		changeDir();
		
		// Load image for monster
		int i = r.nextInt(3) + 1;
		
		try {
			setImage(ImageIO.read(new File("img//dragon" + i + ".png")));
		} catch (IOException e) {
			System.err.print("Error while loading the image dragon" + i + ".png.");
		}
	}
	
	public boolean attackPlayer(boolean hasKey){
		// Is the player in range of the monster?
		boolean playerInRange = (Math.sqrt(Math.pow(player.getXPos() - getXPos(), 2)
				+ Math.pow(player.getYPos() - getYPos(), 2)) < 2);
		
		// Is the monster able to attack?
		boolean ableToAttack = false;
		if (type == 0) ableToAttack = ((System.currentTimeMillis() - lastAttack) >= cooldownAttack);
		if (type == 1) ableToAttack = (hasKey && ((System.currentTimeMillis() - lastAttack) >= cooldownAttack));
		
		if(playerInRange && ableToAttack){
			lastAttack = System.currentTimeMillis();
			player.changeHealth(-getDamage());
		}
		return playerInRange;
	}
	
	public void changeHealth(int change){
		super.changeHealth(change);
		if(getHealth() <= 0){
			window.level[getXPos()][getYPos()] = new Potion(30);
			window.monsterList.remove(this);
		}
	}
	
	public double cooldownRate(){		
		return 1.0*(System.currentTimeMillis() - lastAttack)/cooldownAttack;
	}
	
	// Move the monster
	public void move(){
		boolean nextWalk = (System.currentTimeMillis() - lastStep) >= cooldownWalk;
		if(valid()){
			if(nextWalk && AStarSearch()){	
				switch(dir){
					case 0 : moveUp(); break;
					case 1 : moveRight(); break;
					case 2 : moveDown(); break;
					case 3 : moveLeft(); break;
				}
				lastStep = System.currentTimeMillis();
			}
		}else{
			changeDir();			
		}
	}
	
	// Change the running direction of the monster
	public void changeDir(){
		Random random = new Random();		
		dir = random.nextInt(4);
	}
	
	// A-Star-Algorithm to search for the player
	public boolean AStarSearch (){
		ArrayList<Node> openList = new ArrayList<Node>();
		ArrayList<Node> closedList = new ArrayList<Node>();
		Node monster = new Node(getXPos(),getYPos());
		monster.calculateCosts();
		
		openList.add(monster);
		boolean playerFound = false;
		while (!openList.isEmpty() && !playerFound){
			// Find the cheapest node in the openList
			Node cheapest = openList.get(0);
			for(Node node : openList){
				if (node.getCalculatedCosts() < cheapest.getCalculatedCosts()){
					cheapest = node;
				}
			}
			openList.remove(cheapest);
			
			// Create the four neighbors (up,down,left,right)
			Node[] neighbors = new Node[4];
			int actX = cheapest.getXPos();
			int actY = cheapest.getYPos();
			
			// Up-Neighbor valid?
			if (!(window.level[actX][actY-1] instanceof Wall) && 
					!(window.level[actX][actY-1] instanceof Door) && 
					!(window.level[actX][actY-1] instanceof Key))
				neighbors[0] = new Node(actX, actY-1); // up
			else
				neighbors[0] = null;
			
			// Right-Neighbor valid?
			if (!(window.level[actX+1][actY] instanceof Wall) && 
					!(window.level[actX+1][actY] instanceof Door) && 
					!(window.level[actX+1][actY] instanceof Key))
				neighbors[1] = new Node(actX+1, actY); // right
			else
				neighbors[1] = null;
			
			// Down-Neighbor valid?
			if (!(window.level[actX][actY+1] instanceof Wall) && 
					!(window.level[actX][actY+1] instanceof Door) && 
					!(window.level[actX][actY+1] instanceof Key))
				neighbors[2] = new Node(actX, actY+1); // down
			else
				neighbors[2] = null;
			
			// Left-Neighbor valid?
			if (!(window.level[actX-1][actY] instanceof Wall) && 
					!(window.level[actX-1][actY] instanceof Door) && 
					!(window.level[actX-1][actY] instanceof Key))
				neighbors[3] = new Node(actX-1, actY); // left
			else
				neighbors[3] = null;
			
			// Check all Neighbors
			for (int k=0; k<4; k++){
				if (neighbors[k] == null)
					continue;
				if (neighbors[k].getXPos() == player.getXPos() && neighbors[k].getYPos() == player.getYPos()){
					// Current Neighbor is the goal / player
					playerFound = true;
				}
				else {
					// Calculate costs of neighbor
					neighbors[k].setCostFromStart(cheapest.getCostFromStart() + 1);
					int xDiff = player.getXPos() - neighbors[k].getXPos();
					int yDiff = player.getYPos() - neighbors[k].getYPos();
					neighbors[k].setCostToGoal((int) Math.sqrt(xDiff*xDiff + yDiff*yDiff));
					neighbors[k].calculateCosts();
					
					boolean skipNode = false;
					for (Node checkOpen : openList){
						if (checkOpen.getXPos() == neighbors[k].getXPos() && checkOpen.getYPos() == neighbors[k].getYPos()){
							// Neighbor already in the openList
							if(checkOpen.getCalculatedCosts() <= neighbors[k].getCalculatedCosts())
								skipNode = true;
						}
					}
					for (Node checkClosed : closedList){
						if (checkClosed.getXPos() == neighbors[k].getXPos() && checkClosed.getYPos() == neighbors[k].getYPos()){
							// Neighbor already in the closedList
							if(checkClosed.getCalculatedCosts() <= neighbors[k].getCalculatedCosts())
								skipNode = true;
						}
					}
					if (!skipNode)
						openList.add(neighbors[k]);
				}
			}// for-neighbors
			closedList.add(cheapest);
		}// while
		
		// Change this.dir
		Node nextStep = closedList.get(1);
		if (nextStep.getXPos() == this.getXPos() && nextStep.getYPos() == this.getYPos()-1)
			this.dir = 0; // go up
		else if (nextStep.getXPos() == this.getXPos()+1 && nextStep.getYPos() == this.getYPos())
			this.dir = 1; // go right
		else if (nextStep.getXPos() == this.getXPos() && nextStep.getYPos() == this.getYPos()+1)
			this.dir = 2; // go down
		else if (nextStep.getXPos() == this.getXPos()-1 && nextStep.getYPos() == this.getYPos())
			this.dir = 3; // go left
		else
			return false;
		
		return true;
	}
	
	public int getType(){
		return type;
	}
	
	// Check whether the next step is valid
	private boolean valid(){
		if(dir == -1) return true;
		
		if(dir == 0 && getYPos()-1 > 0){
			return !(window.level[getXPos()][getYPos()-1] instanceof Wall) &&
				   !(window.level[getXPos()][getYPos()-1] instanceof Door) &&
				   !(window.level[getXPos()][getYPos()-1] instanceof Key);
		}else if(dir == 1 && getXPos()+1 < window.WIDTH){
			return !(window.level[getXPos()+1][getYPos()] instanceof Wall) &&
				   !(window.level[getXPos()+1][getYPos()] instanceof Door) &&
				   !(window.level[getXPos()+1][getYPos()] instanceof Key);
		}else if(dir == 2 && getYPos()+1 < window.HEIGHT){
			return !(window.level[getXPos()][getYPos()+1] instanceof Wall) &&
				   !(window.level[getXPos()][getYPos()+1] instanceof Door) &&
				   !(window.level[getXPos()][getYPos()+1] instanceof Key);
		}else if(dir == 3 && getXPos() > 0 ){
			return !(window.level[getXPos()-1][getYPos()] instanceof Wall) &&
				   !(window.level[getXPos()-1][getYPos()] instanceof Door) &&
				   !(window.level[getXPos()-1][getYPos()] instanceof Key);
		}
		else return false;
	}
	
}
