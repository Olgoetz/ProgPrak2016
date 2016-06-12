package datastructure;

import gui.GameWindow;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

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
			if(nextWalk){	
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
