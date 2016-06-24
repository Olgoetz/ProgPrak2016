package pp2016.team19.client.gui;

import java.awt.Color;
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
import pp2016.team19.shared.Wall;

/**
 * 
 * @author Felizia Langsdorf, Matr_Nr.: 6002960
 *
 */

// statusbar for the game, shown on the right side while playing
public class Statusbar extends JPanel {

private static final long serialVersionUID = 1L;
	
	private Image background, key, potion;	
	private Image floor1, wall1, doorOpen1, doorClosed1; 
	
	private GameWindow window;
	private int miniBox = 8;
	
 
	public Statusbar(GameWindow window){
		this.window = window;
		
		try {
			background = ImageIO.read(new File("img//status.png"));
			key = ImageIO.read(new File("img//key.png"));
			potion = ImageIO.read(new File("img//potion.png"));
			
			//load the smaller images for the minimap
			floor1 = ImageIO.read(new File("img//floorKopie.png"));
			wall1 = ImageIO.read(new File("img//wallKopie.png"));
			doorClosed1 = ImageIO.read(new File("img//doorClosedKopie.png"));
			doorOpen1 = ImageIO.read(new File("img//dooropenKopie.png"));
			
		} catch (IOException e) {
			System.err.println("Error while loading the images.");
		}
	}
	
	public void paint(Graphics g) {
		
		// draw statusbar
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth() , this.getHeight());
		
		// 5 is the new width of the statusbar and (window.height-6) is the height , make space for minimap
		for(int i = 0; i < 20; i++){
			for(int j= 0; j < window.HEIGHT- 6; j++){
			g.drawImage(background, i*window.BOX, j*window.BOX, null);
			}
		}		

		g.drawImage(window.player.getImage(), 4, 4, window.BOX - 8, window.BOX - 8, null);
		
		if(window.player.hasKey()){
			g.drawImage(key, 5, window.BOX * (window.HEIGHT - 11), null);
		}
		
		g.setColor(Color.WHITE);	
		g.drawString(window.player.getName(), window.BOX + 5, 20);
		g.drawLine( 5, 35, 155, 35);
		g.drawString("Time: " + (System.currentTimeMillis() - window.startTime)/1000, 5,  window.BOX * (window.HEIGHT - 14));
		g.drawString("Level " + window.currentLevel + "/" + window.MAXLEVEL, 5,  window.BOX * (window.HEIGHT - 13));
		
		
		// Display of the potions
		int numberOfPotions = window.player.getNumberOfPotions();
		String s;
		if (numberOfPotions < 10) s ="" +numberOfPotions;
		else s = String.valueOf(numberOfPotions);
		g.drawString(s, 5, window.BOX * (window.HEIGHT - 12));
		g.drawImage(potion,7,(window.BOX * (window.HEIGHT - 12))- window.BOX/2,null);

		GameObject field = window.level[window.player.getXPos()][window.player.getYPos()];
		
		if(field instanceof Key){
			g.drawString("Press Space to take key", 5, window.BOX * (window.HEIGHT - 11));
		}else if(field instanceof Door){
			if(!((Door) field).isOpen()){
				if(window.player.hasKey())
					g.drawString("Press Space: open door", 5, window.BOX * (window.HEIGHT - 11));
				else
					g.drawString("Door closed!", 5, window.BOX * (window.HEIGHT - 11));
			}			
		}else if(field instanceof Potion){
			g.drawString("Press Space: take potion", 5, window.BOX * (window.HEIGHT - 11));
		}
		
		
		g.setColor(Color.WHITE);
		g.drawString("Player's Health: ", 5, window.BOX * (window.HEIGHT - 10) + 20);
		
		// players health bar
		g.setColor(Color.RED);
		g.fillRect(5,window.BOX * (window.HEIGHT - 9), window.player.getMaxHealth(), 5);
		g.setColor(Color.GREEN);
		g.fillRect(5, window.BOX * (window.HEIGHT - 9), window.player.getHealth(), 5);
		
		// show text "minimap" on the statusbar
		g.setColor(Color.WHITE);
		g.drawString( "Minimap  ", 5,  window.BOX * (window.HEIGHT - 7));
		g.drawLine( 5, window.BOX * (window.HEIGHT - 7) + 10 , 155, window.BOX * (window.HEIGHT - 7) + 10);
		
		g.drawLine(2, 0, 2, window.BOX * (window.HEIGHT));

	// draw minimap 
	// variables to set the map at the bottom of the panel	
	int var1=350;
	int var2 = 10;
	
	for (int i = 0; i < window.WIDTH ; i++) {
		for (int j = 0; j < window.HEIGHT; j++) {
//			if (inRange(i,j)) {
				if (window.level[i][j] instanceof Wall) {
					// Here goes a wall
					g.drawImage(wall1, (i * miniBox) + var2 , (j * miniBox) + var1 ,null);
				} else if (window.level[i][j] instanceof Floor) {
					// This field is walkable
					g.drawImage(floor1, (i * miniBox) + var2,
							(j * miniBox) + var1 , null);
				} else if (window.level[i][j] instanceof Door) {
					// Here is the door
					g.drawImage(floor1, (i * miniBox ) + var2,
							(j * miniBox) + var1, null);
					if (((Door) window.level[i][j]).isOpen())
						g.drawImage(doorOpen1, (i * miniBox) + var2, (j
								* miniBox) + var1, null);
					else
						g.drawImage(doorClosed1, (i * miniBox) + var2, (j
								* miniBox) + var1, null);
//				} 
				
		}
	}	
		
				g.drawImage(window.player.getImage().getScaledInstance(6, 6, 0),( window.player.getXPos() //make x and y dynamic
						* miniBox) + var2, (window.player.getYPos() * miniBox) + var1 ,null);			
					}
			}
		
		private boolean inRange(int i, int j) {
			return (Math.sqrt(Math.pow(window.player.getXPos() - i, 2)
					+ Math.pow(window.player.getYPos() - j, 2)) < 3 || !window.mistOn);
		}
	
	}
	

