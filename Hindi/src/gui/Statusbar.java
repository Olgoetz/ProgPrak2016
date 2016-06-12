package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import datastructure.Door;
import datastructure.GameObject;
import datastructure.Key;
import datastructure.Potion;

public class Statusbar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Image background, key, potion;
	
	private GameWindow window;
	
	public Statusbar(GameWindow window){
		this.window = window;
		
		try {
			background = ImageIO.read(new File("img//status.png"));
			key = ImageIO.read(new File("img//key.png"));
			potion = ImageIO.read(new File("img//potion.png"));
		} catch (IOException e) {
			System.err.println("Error while loading the background image.");
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		for(int i = 0; i < window.WIDTH; i++){
			g.drawImage(background, i*window.BOX, 0, null);
		}		

		g.drawImage(window.player.getImage(), 4, 4, window.BOX - 8, window.BOX - 8, null);
		
		if(window.player.hasKey()){
			g.drawImage(key, window.BOX * (window.WIDTH - 1), 0, null);
		}
		
		g.setColor(Color.WHITE);	
		g.drawString(window.player.getName(), window.BOX + 5, 20);
		g.drawString("Time: " + (System.currentTimeMillis() - window.startTime)/1000, window.BOX * (window.WIDTH - 6), 20);
		g.drawString("Level " + window.currentLevel + "/" + window.MAXLEVEL, window.BOX * (window.WIDTH - 4)-5, 20);
		
		// Display of the potions
		int numberOfPotions = window.player.getNumberOfPotions();
		String s;
		if (numberOfPotions < 10) s = "  "+numberOfPotions;
		else s = String.valueOf(numberOfPotions);
		g.drawString(s, window.BOX*(window.WIDTH-2)-8, 20);
		g.drawImage(potion,window.BOX * (window.WIDTH-2),0,null);

		GameObject field = window.level[window.player.getXPos()][window.player.getYPos()];
		
		if(field instanceof Key){
			g.drawString("Press Space to take key", window.BOX * (window.WIDTH - 11) - 5, 20);
		}else if(field instanceof Door){
			if(!((Door) field).isOpen()){
				if(window.player.hasKey())
					g.drawString("Press Space to open door", window.BOX * (window.WIDTH - 11) - 5, 20);
				else
					g.drawString("Door closed!", window.BOX * (window.WIDTH - 11) - 5, 20);
			}			
		}else if(field instanceof Potion){
			g.drawString("Press Space to take potion", window.BOX * (window.WIDTH - 11) - 5, 20);
		}
		
		g.setColor(Color.RED);
		g.fillRect((window.WIDTH / 2) * window.BOX - 80, getHeight() - 8, window.player.getMaxHealth(), 5);
		g.setColor(Color.GREEN);
		g.fillRect((window.WIDTH / 2) * window.BOX - 80, getHeight() - 8, window.player.getHealth(), 5);
	
	}
	
}
