package datastructure;

import java.awt.Image;

public abstract class Character {

	private int xPos, yPos;
	private Image image;
	
	private int health;
	private int damage;
	
	private int maxHealth;
		
	
	// Getter and Setter
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public void setMaxHealth(int maxHealth){
		this.maxHealth = maxHealth;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void changeHealth(int change){
		health = Math.min(health + change, getMaxHealth());
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public int getHealth(){
		return health;
	}
	
	public Image getImage(){
		return image;
	}
	
	public void setImage(Image img){
		image = img;
	}
	
	public void setPos(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public int getYPos(){
		return yPos;
	}
	
	public int getXPos(){
		return xPos;
	}
	
	public void moveUp(){
		yPos--;
	}
	
	public void moveDown(){
		yPos++;
	}
	
	public void moveLeft(){
		xPos--;
	}
	
	public void moveRight(){
		xPos++;
	}
}
