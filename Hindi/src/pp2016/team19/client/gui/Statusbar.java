package pp2016.team19.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import pp2016.team19.shared.Monster;
import pp2016.team19.shared.Tile;


/**
 * <h1>class for panel statusbar shown on the right side of the window while playing the game </h1>
 * contains: playername, healthbar, items, level, minimap, etc.
 * @author Felizia Langsdorf, 6002960
 *
 */

public class Statusbar extends JPanel {

	private static final long serialVersionUID = 1L;

	// images
	private Image key, potion;
	private Image floor1, wall1, playerImg;
	private Image red, black, beige;

	// the gamewindow
	private GameWindow window;
	// measure variables
	private int statBox = 32;
	// minibox is the tilesize of the minimap
	private int miniBox = 8;
	// shift variables to adjust the map at the bottom of the panel
	int var1 = 360;
	int var2 = 12;

	/**
	 * @author Felizia Langsdorf, 6002960
	 * @param window
	 *            gamewindow of the application
	 */

	public Statusbar(GameWindow window) {
		this.window = window;

		// loading the images
		try {
			
			key = ImageIO.read(new File("img//Goldkey2.png")); // source: http://rocketdock.com/images/screenshots/Gold-key.png
			potion = ImageIO.read(new File("img//potionSmall.png"));// source:http://www.rpguides.de/images/poe/icon_item64_potion_of_major_recovery.png

			// load the smaller images for the minimap
			floor1 = ImageIO.read(new File("img//floorKopie.png")); // source: adopted from sample
			wall1 = ImageIO.read(new File("img//lightgrey.png"));// source: http://static.webshopapp.com/shops/065293/files/025593597/pvc-matt-hellgrau.jpg
			playerImg = ImageIO.read(new File("img//warrior.png"));// source: https://cdn2.iconfinder.com/data/icons/fantasy-characters/512/knight1-512.png
			red = ImageIO.read(new File("img//red.png")); // source: http://www.brillen-sehhilfen.de/optik/image/rot-red.png
			black = ImageIO.read(new File("img//black.png"));// source: http://i.ytimg.com/vi/Zb4r7BcpveQ/maxresdefault.jpg
			beige = ImageIO.read(new File("img//beige.png"));// source: http://www.irisceramica.com/images/prodotti/made/colori/files/uni_beige.jpg
		} catch (IOException e) {
			System.err.println("Error while loading the images.");
		}
	}

	/**
	 * paint method (source: Progprak sample)
	 * @author Felizia Langsdorf, 6002960
	 * @param g
	 *            graphics object
	 */
	public void paint(Graphics g) {

		// set font style
		g.setFont(new Font("Avenir Heavy", Font.PLAIN, 12));
		// first overpaint everything gray
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// draws the miniplayer image
		g.drawImage(playerImg, 4, 4, statBox - 8, statBox - 8, null);

		g.setColor(new Color(245, 245, 220));
		// display the name
		g.drawString(window.getEngine().getMyPlayer().getName(), statBox + 5, 20);
		g.drawLine(5, 35, 155, 35);
		g.drawString("Player's Health: ", 5, 60);
		// display the healthbar of the player
		g.setColor(Color.RED);
		g.fillRect(5, 70, window.getEngine().getMyPlayer().getMaxHealth(), 5);
		g.setColor(Color.GREEN);
		g.fillRect(5, 70, window.getEngine().getMyPlayer().getHealth(), 5);

		// display the level
		g.setColor(new Color(245, 245, 220));
		g.drawString("Level " + window.currentLevel + "/" + window.MAXLEVEL, 5, 100);

		// Display of the potions
		int numberOfPotions = window.getEngine().getMyPlayer().getNumberOfPotions();
		String s;
		if (numberOfPotions < 10)
			s = " " + numberOfPotions;
		else
			s = String.valueOf(numberOfPotions);
		g.drawString(s, 5, 135);
		g.drawImage(potion, 12, (statBox * (window.HEIGHT - 12)) - statBox / 2, null);

		// draw the key if the player picked one up
		if (window.getEngine().getMyPlayer().hasKey()) {
			g.drawImage(key, 55, 120, null);
		}
		// display the past time
		g.drawString("Time: " + (System.currentTimeMillis() - window.startTime) / 1000, 5, 200);
		//gets the labyrinth information from the engine
		Tile field = window.getEngine().getLabyrinth()[window.getEngine().getMyPlayer().getXPos()][window.getEngine()
				.getMyPlayer().getYPos()];

		// the instructions
		if (field.containsKey()) {
			g.drawString("Press Space to take key", 5, 230);
		} else if (field.isExit()) {
			if (!field.exitUnlocked()) {
				if (window.getEngine().getMyPlayer().hasKey())
					g.drawString("Press Space: open door", 5, 230);
				else
					g.drawString("Door closed!", 5, 230);
			}
		} else if (field.containsPotion()) {
			g.drawString("Press Space: take potion", 5, 230);
		}

		// show text "minimap" on the statusbar
		g.drawString("Minimap  ", 5, 340);
		g.drawLine(5, 345, 155, 345);
		g.drawLine(2, 0, 2, statBox * (window.HEIGHT));

		// draw minimap

		// if in the menubar "show minimap" was clicked
		// draw every single mini tile, 8x8 pixel
		if (window.minifieldShown) {
			for (int i = 0; i < window.WIDTH; i++) {
				for (int j = 0; j < window.HEIGHT; j++) {
					if (window.getEngine().getLabyrinth()[i][j].isRock()) {
						// Here goes a wall
						g.drawImage(wall1, (i * miniBox) + var2, (j * miniBox) + var1, null);
					} else if (window.getEngine().getLabyrinth()[i][j].isFloor()) {
						// here goes floor
						g.drawImage(floor1, (i * miniBox) + var2, (j * miniBox) + var1, null);
						if (window.getEngine().getLabyrinth()[i][j].containsKey()) {
							g.setColor(Color.YELLOW);
							g.fillRect((i * miniBox) + var2, (j * miniBox) + var1, miniBox, miniBox);
						} else if (window.getEngine().getLabyrinth()[i][j].containsPotion()) {
							g.setColor(Color.BLUE);
							g.fillRect((i * miniBox) + var2, (j * miniBox) + var1, miniBox, miniBox);
						}
					} else if (window.getEngine().getLabyrinth()[i][j].isEntry()) {
						// Here is the door
						g.drawImage(black, (i * miniBox) + var2, (j * miniBox) + var1, null);
						// here is the exit
					} else if (window.getEngine().getLabyrinth()[i][j].isExit()) {
						g.drawImage(beige, (i * miniBox) + var2, (j * miniBox) + var1, null);
					}
				}
				// declares a monsterlist and receives the information from the
				// client engine
				LinkedList<Monster> monsterList = window.getEngine().getMyMonster();
				for (int x = 0; x < monsterList.size(); x++) {
					Monster m = monsterList.get(x);
					boolean event = window.getEngine().getMyPlayer().hasKey(); // player has a key or not										

					// Draw the monster, if it's present from the beginning on
					if (m.getType() == 0)
						drawMonster(g, m);
					// Draw the monster, if it only appears after the event
					// 'take key'
					else if (event && m.getType() == 1)
						drawMonster(g, m);
				}

				// paint the player as a red point
				g.drawImage(red, (window.getEngine().getMyPlayer().getXPos() 
						* miniBox) + var2, (window.getEngine().getMyPlayer().getYPos() * miniBox) + var1, null);
			}
		}
	}

	/**
	 * paint method for the monsters
	 * 
	 * @author Felizia Langsdorf, 6002960
	 * @param g
	 *            graphics object
	 * @param m
	 *            monster object
	 */

	private void drawMonster(Graphics g, Monster m) {
		// paints the monsters as a green rectangle in the minimap
		g.setColor(Color.GREEN);
		g.fillRect((m.getXPos() * miniBox) + var2, (m.getYPos() * miniBox) + var1, miniBox, miniBox);

	}

}
