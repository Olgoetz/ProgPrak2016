package pp2016.team19.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import pp2016.team19.shared.Door;
import pp2016.team19.shared.Floor;
import pp2016.team19.shared.GameObject;
import pp2016.team19.shared.Key;
import pp2016.team19.shared.Potion;
import pp2016.team19.shared.Tile;
import pp2016.team19.shared.Wall;

/**
 * class for panel statusbar shown on the right side of the window while playing the game
 * @author Felizia Langsdorf, Matr_Nr.: 6002960
 *
 */

public class Statusbar extends JPanel {

private static final long serialVersionUID = 1L;
	
	private Image background, key, potion;	
	private Image floor1, wall1, playerImg; 
	private Image red, black, beige;
	
	private GameWindow window;
	private int statBox = 32;
	private int miniBox = 8;
	
	/**
	 * @author Felizia Langsdorf, Matr_Nr.: 6002960
	 * @param window window of the application
	 */
	public Statusbar(GameWindow window){
		this.window = window;
		
		//loading the images
		try {
			background = ImageIO.read(new File("img//statusgrau.png"));
			key = ImageIO.read(new File("img//key.png"));
			potion = ImageIO.read(new File("img//potionSmall.png"));
			
			//load the smaller images for the minimap
			floor1 = ImageIO.read(new File("img//floorKopie.png"));
			wall1 = ImageIO.read(new File("img//wallKopie.png"));
			playerImg = ImageIO.read(new File("img//warrior.png"));
			red = ImageIO.read(new File ("img//rot.png"));
			black = ImageIO.read(new File("img//schwarz.png"));
			beige = ImageIO.read(new File ("img//beige.png"));
			
		} catch (IOException e) {
			System.err.println("Error while loading the images.");
		}
	}
	/**
	 * paint method
	 * @author Felizia Langsdorf, Matr_Nr.: 6002960
	 *
	 */
	public void paint(Graphics g) {
		
		// first draw the panel black
		g.setFont(new Font("Avenir Heavy", Font.PLAIN, 12));
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, this.getWidth() , this.getHeight());
		
		
		//draws the background with pictures, at the bottom it leaves space for the minimap
		for(int i = 0; i < 20; i++){
			for(int j= 0; j < window.HEIGHT- 6; j++){
			g.drawImage(background, i*statBox, j*statBox, null);
			}
		}		
	
		
		g.drawImage(playerImg, 4, 4, statBox - 8, statBox - 8, null);
		
		g.setColor(new Color (245,245,220));	
		g.drawString(window.getEngine().getMyPlayer().getName(), statBox + 5, 20);
		g.drawLine( 5, 35, 155, 35);
		g.drawString("Player's Health: ", 5, 60);
		
		g.setColor(Color.RED);
		g.fillRect(5,70, window.getEngine().getMyPlayer().getMaxHealth(), 5);
		g.setColor(Color.GREEN);
		g.fillRect(5, 70 , window.getEngine().getMyPlayer().getHealth(), 5);
		
		g.setColor(new Color (245,245,220));
		g.drawString("Level " + window.currentLevel + "/" + window.MAXLEVEL, 5, 100);
		
		// Display of the potions
		int numberOfPotions = window.getEngine().getMyPlayer().getNumberOfPotions();
		String s;
		if (numberOfPotions < 10) s =" " +numberOfPotions;
		else s = String.valueOf(numberOfPotions);
		g.drawString(s, 5, 135);
		g.drawImage(potion,12,(statBox * (window.HEIGHT - 12))- statBox/2,null);
			
		// draw the key if the player picked one up
		if(window.getEngine().getMyPlayer().hasKey()){
			g.drawImage(key, 50, 120, null);
			}
			
		g.drawString("Time: " + (System.currentTimeMillis() - window.startTime)/1000, 5, 170);	
		
	
		Tile field = window.getEngine().getLabyrinth()[window.getEngine().getMyPlayer().getXPos()][window.getEngine().getMyPlayer().getYPos()];
		
		//draw the instructions
		if(field.containsKey()){
			g.drawString("Press Space to take key", 5, 190);
		}else if(field.isExit()){
			if(!field.exitUnlocked()){
				if(window.getEngine().getMyPlayer().hasKey())
					g.drawString("Press Space: open door", 5, 190);
				else
					g.drawString("Door closed!", 5, 190);
			}			
		}else if(field.containsPotion()){
			g.drawString("Press Space: take potion", 5, 190);
		}
		//g.setColor(Color.WHITE);
		g.drawLine(5,200,155,200);
		g.drawString("System Messages: ", 5 , 215);
		
		// !!!!!!!
		//System messages: 
		// for( i=0; i < SystemMessages.List.size(); i++ ) {
		// g.drawString("...." , 5, 225)
		// ......
		g.drawLine(5, 325, 155, 325);
		
		
		// show text "minimap" on the statusbar	
		g.drawString( "Minimap  ", 5, 340);
		g.drawLine( 5, 345, 155, 345);
		
		g.drawLine(2, 0, 2, statBox * (window.HEIGHT));

	
	// draw minimap 
	// variables to adjust the map at the bottom of the panel	
	int var1= 360;
	int var2 = 12;
	
	// draw every single mini tile
	if ( window.minifieldShown){
	for (int i = 0; i < window.WIDTH ; i++) {
		for (int j = 0; j < window.HEIGHT; j++) {
//			if (inRange(i,j)) {
				if (window.getEngine().getLabyrinth()[i][j].isRock()) {
					// Here goes a wall
					g.drawImage(wall1, (i * miniBox) + var2 , (j * miniBox) + var1 ,null);
				} else if (window.getEngine().getLabyrinth()[i][j].isFloor()) {
					// This field is walkable
					g.drawImage(floor1, (i * miniBox) + var2,
							(j * miniBox) + var1 , null);
				
				}else if (window.getEngine().getLabyrinth()[i][j].isEntry()) {
						// Here is the door
						g.drawImage(black, (i * miniBox) +var2, (j * miniBox) + var1, null);
						
					} else if (window.getEngine().getLabyrinth()[i][j].isExit()) {													
							g.drawImage(beige, (i * miniBox) + var2, (j * miniBox) + var1, null);												
					} 
				} 
				
		}
	
		
				g.drawImage(red,( window.getEngine().getMyPlayer().getXPos() //make x and y dynamic
						* miniBox) + var2, (window.getEngine().getMyPlayer().getYPos() * miniBox) + var1 ,null);			
					}
	}
		
//		private boolean inRange(int i, int j) {
//			return (Math.sqrt(Math.pow(window.player.getXPos() - i, 2)
//					+ Math.pow(window.player.getYPos() - j, 2)) < 3 || !window.mistOn);
//		}
//	
	}
	

