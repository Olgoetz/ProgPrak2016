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
 * <h1> class for panel statusbar shown on the right side of the window while playing the game <h1> 
 * contains: playername, healthbar, items, level, minimap, etc.
 * 
 * @author Felizia Langsdorf, 6002960
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
			background = ImageIO.read(new File("img//statusgrau.png"));//source: http://www.tierrfino.de/imageresize/duro_gomera-grau.jpg
			key = ImageIO.read(new File("img//Goldkey2.png")); //source: http://rocketdock.com/images/screenshots/Gold-key.png
			potion = ImageIO.read(new File("img//potionSmall.png"));//source:http://www.rpguides.de/images/poe/icon_item64_potion_of_major_recovery.png
			
			//load the smaller images for the minimap
			floor1 = ImageIO.read(new File("img//floorKopie.png")); // adopted
			wall1 = ImageIO.read(new File("img//hellgrau.png"));//source: http://static.webshopapp.com/shops/065293/files/025593597/pvc-matt-hellgrau.jpg
			playerImg = ImageIO.read(new File("img//warrior.png"));// source: https://cdn2.iconfinder.com/data/icons/fantasy-characters/512/knight1-512.png
			red = ImageIO.read(new File ("img//rot.png")); // source: http://www.brillen-sehhilfen.de/optik/image/rot-red.png
			black = ImageIO.read(new File("img//schwarz.png"));//source: http://i.ytimg.com/vi/Zb4r7BcpveQ/maxresdefault.jpg
			beige = ImageIO.read(new File ("img//beige.png"));//source: http://www.irisceramica.com/images/prodotti/made/colori/files/uni_beige.jpg
			
		} catch (IOException e) {
			System.err.println("Error while loading the images.");
		}
	}
	/**
	 * paint method
	 * @author Felizia Langsdorf, 6002960 
	 */
	public void paint(Graphics g) {
		
		// set font style 
		g.setFont(new Font("Avenir Heavy", Font.PLAIN, 12));
		//first overpaint everything gray
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, this.getWidth() , this.getHeight());
			
		//draw pictures in the panel, at the bottom it leaves space for the minimap
		for(int i = 0; i < 20; i++){
			for(int j= 0; j < window.HEIGHT- 6; j++){
			g.drawImage(background, i*statBox, j*statBox, null);
			}
		}		
	
		//draws the miniplayer image
		g.drawImage(playerImg, 4, 4, statBox - 8, statBox - 8, null);
		
		g.setColor(new Color (245,245,220));	
		// display the name
		g.drawString(window.getEngine().getMyPlayer().getName(), statBox + 5, 20);
		g.drawLine( 5, 35, 155, 35);
		g.drawString("Player's Health: ", 5, 60);
		//display the healthbar of the player
		g.setColor(Color.RED);
		g.fillRect(5,70, window.getEngine().getMyPlayer().getMaxHealth(), 5);
		g.setColor(Color.GREEN);
		g.fillRect(5, 70 , window.getEngine().getMyPlayer().getHealth(), 5);
		
		//display the level
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
			g.drawImage(key, 55, 120, null);
			}
		//display the past time
		g.drawString("Time: " + (System.currentTimeMillis() - window.startTime)/1000, 5, 200);		
	
		Tile field = window.getEngine().getLabyrinth()[window.getEngine().getMyPlayer().getXPos()][window.getEngine().getMyPlayer().getYPos()];
		
		// the instructions
		if(field.containsKey()){
			g.drawString("Press Space to take key", 5, 230);
		}else if(field.isExit()){
			if(!field.exitUnlocked()){
				if(window.getEngine().getMyPlayer().hasKey())
					g.drawString("Press Space: open door", 5, 230);
				else
					g.drawString("Door closed!", 5, 230);
			}			
		}else if(field.containsPotion()){
			g.drawString("Press Space: take potion", 5, 230);
		}	
		
		// show text "minimap" on the statusbar	
		g.drawString( "Minimap  ", 5, 340);
		g.drawLine( 5, 345, 155, 345);		
		g.drawLine(2, 0, 2, statBox * (window.HEIGHT));

	
	// draw minimap 
	// variables to adjust the map at the bottom of the panel	
	int var1= 360;
	int var2 = 12;
	
	// if in the menubar "show minimap" was clicked
	// draw every single mini tile, 8x8 pixel
	if ( window.minifieldShown){
	for (int i = 0; i < window.WIDTH ; i++) {
		for (int j = 0; j < window.HEIGHT; j++) {
				if (window.getEngine().getLabyrinth()[i][j].isRock()) {
					// Here goes a wall
					g.drawImage(wall1, (i * miniBox) + var2 , (j * miniBox) + var1 ,null);
				} else if (window.getEngine().getLabyrinth()[i][j].isFloor()) {
					//here goes floor
					g.drawImage(floor1, (i * miniBox) + var2, (j * miniBox) + var1 , null);		
				}else if (window.getEngine().getLabyrinth()[i][j].isEntry()) {
						// Here is the door
						g.drawImage(black, (i * miniBox) +var2, (j * miniBox) + var1, null);
						//here is the exit
					} else if (window.getEngine().getLabyrinth()[i][j].isExit()) {													
							g.drawImage(beige, (i * miniBox) + var2, (j * miniBox) + var1, null);												
					} 
				} 
				
		}
	
		     // the player as a red point
				g.drawImage(red,( window.getEngine().getMyPlayer().getXPos() //make x and y dynamic
						* miniBox) + var2, (window.getEngine().getMyPlayer().getYPos() * miniBox) + var1 ,null);			
					}
		}
		
	}
	

