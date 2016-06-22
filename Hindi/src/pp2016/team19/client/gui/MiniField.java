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
import pp2016.team19.shared.Key;
import pp2016.team19.shared.Potion;
import pp2016.team19.shared.Wall;

// author: Felizia Langsdorf, Matr_Nr: 6002960

// minimap class, shown at the bottom of statusbar 
// with mistOn = true; without fireballs, monsters, key, potion 

public class MiniField extends JPanel {

	private static final long serialVersionUID = 1L;

	private Image floor, wall, doorOpen, doorClosed, key, potion; // fireball; not used anymore 
	private GameWindow window;
	// use a smaller tile for the minimap
	private int miniBox = 6;
	
	
	public MiniField (GameWindow window) {
		this.window = window;
		
		// Load the images
		try {
			floor = ImageIO.read(new File("img//floor.png"));
			wall = ImageIO.read(new File("img//wall.png"));
			doorClosed = ImageIO.read(new File("img//door.png"));
			doorOpen = ImageIO.read(new File("img//dooropen.png"));
		} catch (IOException e) {
			System.err.println("Error while loading one of the images.");
		}
	}
	
// not used yet
	public int getMiniBox(){
		return miniBox;
	}
	
	
	public void paint(Graphics g) {
		
		// First, everything is going to be overpainted while repainting
		//g.setColor(Color.BLACK);
		//g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// Draw every single field
		for (int i = 0; i < window.WIDTH; i++) {
			for (int j = 0; j < window.HEIGHT; j++) {
//				if (inRange(i,j)) {

					if (window.level[i][j] instanceof Wall) {
						// Here goes a wall
						g.drawImage(wall, i * miniBox, j * miniBox,
								null);
					} else if (window.level[i][j] instanceof Floor) {
						// This field is walkable
						g.drawImage(floor, i * miniBox,
								j * miniBox, null);
					} else if (window.level[i][j] instanceof Key) {
						// Here lies the key
						g.drawImage(floor, i * miniBox,
								j * miniBox, null);
						g.drawImage(key, i * miniBox, j
								* miniBox, null);
					} else if (window.level[i][j] instanceof Door) {
						// Here is the door
						g.drawImage(floor, i * miniBox,
								j * miniBox, null);
						if (((Door) window.level[i][j]).isOpen())
							g.drawImage(doorOpen, i * miniBox, j
									* miniBox, null);
						else
							g.drawImage(doorClosed, i * miniBox, j
									* miniBox, null);
					} else if (window.level[i][j] instanceof Potion) {
						// Here is the door
						g.drawImage(floor, i * miniBox,
								j * miniBox, null);
						// Here lies a potion
						g.drawImage(floor, i * miniBox,
								j * miniBox, null);
						g.drawImage(potion, i * miniBox, j
								* miniBox, null);
					}
				//}
			}
		}
		
		// Draw the player at its position
		// make scale dynamic
		g.drawImage(window.player.getImage().getScaledInstance(6, 6, 0), window.player.getXPos() //make x and y dynamic
				* miniBox, window.player.getYPos() * miniBox,
				null);
		
		if(window.gameLost){
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
			g.drawString("GAME OVER", getWidth()/2 - 120, getHeight()/2);
		}else{
			if(window.gameWon){
				g.setColor(Color.WHITE);
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
				g.drawString("YOU WIN", getWidth()/2 - 120, getHeight()/2);
			}
		}
	}
    
//	private boolean inRange(int i, int j) {
//		return (Math.sqrt(Math.pow(window.player.getXPos() - i, 2)
//				+ Math.pow(window.player.getYPos() - j, 2)) < 3 || window.mistOn);
//	}
//	
}
