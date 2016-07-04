package pp2016.team19.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import pp2016.team19.shared.Monster;
import pp2016.team19.shared.Player;


/**
 * class for the panel on which the gamefield is drawn
 * 
 * @author Felizia Langsdorf, Matr_Nr.: 6002960
 *
 */

// panel for the gamefield

public class GameField extends JPanel {

	private static final long serialVersionUID = 1L;

	private Image floor, wall, doorOpen, doorClosed, key, potion, fireball,
			hindi, monster;
	
	private GameWindow window;

	/**
	 * @author Felizia Langsdorf, 6002960
	 * @param window
	 *            the gamewindow JFrame
	 * 
	 */

	public GameField(GameWindow window) {
		this.window = window;
		// Load the images
		try {
			floor = ImageIO.read(new File("img//greyK.png")); //source: http://www.tierrfino.de/imageresize/duro_gomera-grau.jpg
			wall = ImageIO.read(new File("img//bricks.png"));	//source:https://www.wallpaper-gratis.eu/texturen/steinmauer/steinmauer009_1024x768.jpg
			doorClosed = ImageIO.read(new File("img//Gartentor.png")); //source:http://www.bauanleitung.org/wp-content/uploads/Bauanleitung-Gartentor-e1408705808615.jpg
			doorOpen = ImageIO.read(new File("img//torK.png")); //source: http://www.decowoerner.com/images/contentUploads/pictures/600_699/646/www/10354/646_635_00-1-5-00.jpg
			key = ImageIO.read(new File("img//Goldkey.png")); //source: http://rocketdock.com/images/screenshots/Gold-key.png

			potion = ImageIO.read(new File("img//potionK.png")); // source:http://www.rpguides.de/images/poe/icon_item64_potion_of_major_recovery.png
			fireball = ImageIO.read(new File("img//fireK.png")); //adopted
			hindi = ImageIO.read(new File("img/warrior.png")); //source: https://cdn2.iconfinder.com/data/icons/fantasy-characters/512/knight1-512.png

			Random r = new Random();
			// Load image for monster
			int i = r.nextInt(3) + 1;
				monster = ImageIO.read(new File("img//drache" + i + ".png")); //adopted
			
		} catch (IOException e) {
			System.err.println("Error while loading one of the images.");
		}
	}
	
	// shift variables for the labyrinth
	int v1;
	int v2;

	/**
	 * @author Felizia Langsdorf, 6002960 paint method, draws the labyrinth, the
	 *         player, the monsters, etc.
	 * 
	 */

	public void paint(Graphics g) {

		// First, everything is going to be overpainted while repainting
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		for (int i = 0; i < window.WIDTH; i++){
			for (int j=0; j < window.HEIGHT; j++){
				g.drawImage(wall, i*window.BOX , j * window.BOX, null); // box 94 !
			}
		}
		
		int startX = 1; 
		int startY = 14; 
		
		int playerX = window.getEngine().getMyPlayer().getXPos();
		int playerY = window.getEngine().getMyPlayer().getYPos();
		
		v1 = window.BOX;
		v2 = -12 * window.BOX; 
		

		// Draw every single field
		for (int i = 0; i < window.WIDTH; i++) {
			for (int j = 0; j < window.HEIGHT; j++) {
					
					if(playerY > startY){
						v2 -= window.BOX;
						startY+=1;		
					}else if (playerX>startX){
						v1-= window.BOX;
						startX+=1;
					}else if(playerY < startY){
						v2 += window.BOX;
						startY -= 1;
					}else if(playerX < startX){
						v1 += window.BOX;
						startX -= 1;
					}
					

					if (window.getEngine().getLabyrinth()[i][j].isRock()) {
						// Here goes a wall
						g.drawImage(wall, (i * window.BOX) + v1, (j * window.BOX) + v2, null);
					} else if (window.getEngine().getLabyrinth()[i][j].isFloor()) {
						// This field is walkable
						g.drawImage(floor, (i * window.BOX) + v1, (j * window.BOX) + v2, null);
						if (window.getEngine().getLabyrinth()[i][j].containsKey()) {
							// Here lies the key
							g.drawImage(floor, (i * window.BOX) + v1, (j * window.BOX) + v2, null);
							g.drawImage(key, (i * window.BOX) + v1 + 15 , (j * window.BOX) + v2 + 20, null);
						} else if (window.getEngine().getLabyrinth()[i][j].containsPotion()) {
							// Here is the door
							g.drawImage(floor, (i * window.BOX) + v1, (j * window.BOX) + v2, null);
							// Here lies a potion
							g.drawImage(floor, (i * window.BOX) + v1, (j * window.BOX) + v2,null);
							g.drawImage(potion, (i * window.BOX) + v1 + 10, (j * window.BOX) + v2 + 10,null);
						}
					} else if (window.getEngine().getLabyrinth()[i][j].isEntry()) {
						// Here is the door
						g.drawImage(floor, (i * window.BOX) + v1, (j * window.BOX) + v2, null);
						g.drawImage(doorOpen, (i * window.BOX) + v1, (j * window.BOX) + v2 ,null);
					} else if (window.getEngine().getLabyrinth()[i][j].isExit()) {
						if (window.getEngine().getLabyrinth()[i][j].exitUnlocked())
							g.drawImage(doorOpen, (i * window.BOX) + v1 , (j* window.BOX) + v2, null);
						else
							g.drawImage(doorClosed, (i * window.BOX) + v1, (j* window.BOX) + v2, null);
					}
			}
			
			
			// Draw the monsters at their specific position
//			System.out.println("METHOD GameField.paint: Monster Gr��e" + window.getEngine().getMyMonster().size()); 
			LinkedList<Monster> monsterList = window.getEngine().getMyMonster();
			for (int x = 0; x < monsterList.size(); x++) {
			 Monster m = monsterList.get(x);
			 boolean event = window.getEngine().getMyPlayer().hasKey();
		
			 // At this point every monster is called. So an
			 // attacking order is called, if the player is
			 // in range. Otherwise the fsm is called to decide what the monster
			 // should do.
				if (m.justAttacked()) {
					int box = window.BOX;
					Player s = window.getEngine().getMyPlayer();

					double p = m.cooldownRate();
					g.setColor(Color.RED);
					g.drawImage(fireball, (int) (((1 - p) * m.getXPos() + (p) * s.getXPos()) * box) + box/2 + v1 ,
							(int) (((1 - p) * m.getYPos() + (p) * s.getYPos()) * box) + box/2 + v2, 8, 8, null);
				}
			
			 // Draw the monster, if it's present from the beginning on
			 if (m.getType() == 0)
			 drawMonster(g, m);
			 // Draw the monster, if it only appears after the event 'take key'
			 else if (event && m.getType() == 1)
			 drawMonster(g, m);
			 }
			
//		 Draw the player at its position
		g.drawImage(hindi, (window.getEngine().getMyPlayer().getXPos()
				* window.BOX) + v1, (window.getEngine().getMyPlayer().getYPos() * window.BOX) + v2, null);

		// game over is showing up if the game is lost
		if(window.gameLost)

	{
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		g.drawString("GAME OVER", getWidth() / 2 - 120, getHeight() / 2);
	}else
	{
		// you win is showing up on screen if the game is won
		if (window.gameWon) {
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
			g.drawString("YOU WIN", getWidth() / 2 - 120, getHeight() / 2);
		}
	}					
}
	 	
	}

	/**
	 * paint method, draws the monsters and their health points
	 * 
	 * @author Felizia Langsdorf, 6002960
	 * @param g
	 * @param m
	 *            monster object?
	 * 
	 */

	private void drawMonster(Graphics g, Monster m) {
		
		if (inRange(m.getXPos(), m.getYPos())) {
			g.drawImage(monster, (m.getXPos() * window.BOX) + v1, (m.getYPos()
					* window.BOX) + v2, null);
			
			// Monster Health Points
			g.setColor(Color.GREEN);
			long monsterHP = (long) m.getHealth();
			long monsterMaxHP = (long) m.getMaxHealth();
			int monsterHealthRect = (int) ((window.BOX-30) * monsterHP/monsterMaxHP);
			g.fillRect(((m.getXPos() * window.BOX) + v1) + 15, (m.getYPos() * window.BOX - 2) + v2 + 4, monsterHealthRect, 2);
		}
	}

	/**
	 * @author Felizia Langsdorf, 6002960 method need to understand that !!!
	 *         xxxx
	 * 
	 */

	private boolean inRange(int i, int j) {
		return (Math.sqrt(Math.pow(window.getEngine().getMyPlayer().getXPos()
				- i, 2)
				+ Math.pow(window.getEngine().getMyPlayer().getYPos() - j, 2)) < 4);
	}

}
