package pp2016.team19.shared;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pp2016.team19.client.gui.GameWindow;

public class Player extends Character {

	private String name;


	private boolean hasKey;
	private int numberOfPotions;
	private int potionEffect;
	
	
	private GameWindow window;
	
	public Player(String imgFile, GameWindow window){
		this.window = window;
		
		setNumberOfPotions(0);
		setPos(0,0);		
		setHealth(100);
		setMaxHealth(getHealth());
		setName("Hindi Bones");
		
		// Load the image of the player
		try {
			setImage(ImageIO.read(new File(imgFile)));
		} catch (IOException e) {
			System.err.print("Error while loading the image "+ imgFile + ".");
		}
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
		return potionEffect;
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
	
	public Monster monsterToAttack(){
		for(int i = 0; i < window.monsterList.size(); i++){
			Monster m = window.monsterList.get(i);
						
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
