package pp2016.team19.shared;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import pp2016.team19.client.gui.GameWindow;
import pp2016.team19.server.engine.Game;

public class Player extends Character implements Serializable{


	private static final long serialVersionUID = -3344880853631540753L;


	private String name;
	private String password;

	private boolean hasKey;
	private int numberOfPotions;
	private int potionEffect;

	private LinkedList<Node> pathToPos = new LinkedList<Node>();
	private long lastStep = System.currentTimeMillis();
	private int cooldownWalk = 500;
	
	public Player(){
		super();
		setNumberOfPotions(0);
		setPos(0,0);		
		setHealth(100);
		setMaxHealth(getHealth());
		try {
			setImage(ImageIO.read(new File("img//player.png")));
		} catch (IOException e) {
			System.err.print("Error while loading the player image.");
		}
	}
	
	public Player(Game game){
		super(game);
		
		setNumberOfPotions(0);
		setPos(0,0);		
		setHealth(100);
		setMaxHealth(getHealth());
		setName("Hindi Bones");
		
		// Load the image of the player
		try {
			setImage(ImageIO.read(new File("img//player.png")));
		} catch (IOException e) {
			System.err.print("Error while loading the player image.");
		}
	}
	

	public boolean moveToPos(int xGoal, int yGoal) {
		if (pathToPos.isEmpty()) {
			pathToPos = AStarSearch(this.getXPos(), this.getYPos(), xGoal, yGoal);
			pathToPos.addLast(new Node(xGoal, yGoal));
		}
		
		boolean nextWalk = (System.currentTimeMillis() - lastStep) >= cooldownWalk;
		if (nextWalk) {
			changeDir(pathToPos);
			System.out.println("Actual Player Position: " + this.getXPos() + ", " + this.getYPos());
			lastStep = System.currentTimeMillis();
		}
		
		return pathToPos.isEmpty();
	}
	
	// Method to take the key
	public void takeKey(){
		hasKey = true;
	}
	
	// Method to remove the key
	public void removeKey(){
		hasKey = false;
	}	
	
	public int usePotion(){
		setNumberOfPotions(numberOfPotions-1);
		health = Math.max(health+potionEffect,getMaxHealth());
		return health;
	}
	
	public void takePotion(Potion t){
		numberOfPotions++;
		potionEffect = t.getEffect();
	}
	
	public void setNumberOfPotions(int quantity){
		if (quantity >= 0) numberOfPotions = quantity;
	}
	
	public int getNumberOfPotions(){
		return numberOfPotions;
	}
	
	// Does the player have the key?
	public boolean hasKey(){
		return hasKey;
	}
		
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString(){
		return "Player: " + this.getName() + ", PW: " + this.getPassword() + ", Pos: " + this.getXPos() + ", " + this.getYPos();
	}

	public Monster monsterToAttack(){
		for(int i = 0; i < getGame().getMonsters().size(); i++){
			Monster m = getGame().getMonsters().get(i);
						
			// Is the player able to attack?
			boolean ableToAttack = false;
			if (m.getType() == 0) ableToAttack = true; 
			if (m.getType() == 1) ableToAttack = hasKey;
			
			if((Math.sqrt(Math.pow(getXPos() - m.getXPos(), 2)+ Math.pow(getYPos() - m.getYPos(), 2)) < 2)&&ableToAttack){
				return m;
			}
		}
		
		return null;
	}
	

	
}
