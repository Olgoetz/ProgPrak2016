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

import pp2016.team19.shared.Door;
import pp2016.team19.shared.Floor;
import pp2016.team19.shared.Key;
import pp2016.team19.shared.Monster;
import pp2016.team19.shared.Player;
import pp2016.team19.shared.Potion;
import pp2016.team19.shared.Wall;

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
			player, monster;
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
			floor = ImageIO.read(new File("img//floor.png"));
			wall = ImageIO.read(new File("img//wall.png"));
			doorClosed = ImageIO.read(new File("img//doorClosed.png"));
			doorOpen = ImageIO.read(new File("img//dooropen.png"));
			key = ImageIO.read(new File("img//key.png"));
			potion = ImageIO.read(new File("img//potion.png"));
			fireball = ImageIO.read(new File("img//fireball.png"));
			player = ImageIO.read(new File("img//player.png"));
			monster = ImageIO.read(new File("img//dragon1.png"));
		} catch (IOException e) {
			System.err.println("Error while loading one of the images.");
		}
	}

	/**
	 * @author Felizia Langsdorf, 6002960 paint method, draws the labyrinth, the
	 *         player, the monsters, etc.
	 * 
	 */

	public void paint(Graphics g) {

		// First, everything is going to be overpainted while repainting
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// Draw every single field
		for (int i = 0; i < window.WIDTH; i++) {
			for (int j = 0; j < window.HEIGHT; j++) {
				if (inRange(i, j)) {

					if (window.getEngine().getLabyrinth()[i][j].isRock()) {
						// Here goes a wall
						g.drawImage(wall, i * window.BOX, j * window.BOX, null);
					} else if (window.getEngine().getLabyrinth()[i][j]
							.isFloor()) {
						// This field is walkable
						g.drawImage(floor, i * window.BOX, j * window.BOX, null);
						if (window.getEngine().getLabyrinth()[i][j]
								.containsKey()) {
							// Here lies the key
							g.drawImage(floor, i * window.BOX, j * window.BOX,
									null);
							g.drawImage(key, i * window.BOX, j * window.BOX,
									null);
						} else if (window.getEngine().getLabyrinth()[i][j]
								.containsPotion()) {
							// Here is the door
							g.drawImage(floor, i * window.BOX, j * window.BOX,
									null);
							// Here lies a potion
							g.drawImage(floor, i * window.BOX, j * window.BOX,
									null);
							g.drawImage(potion, i * window.BOX, j * window.BOX,
									null);
						}
					} else if (window.getEngine().getLabyrinth()[i][j]
							.isEntry()) {
						// Here is the door
						g.drawImage(floor, i * window.BOX, j * window.BOX, null);
						g.drawImage(doorOpen, i * window.BOX, j * window.BOX,
								null);
					} else if (window.getEngine().getLabyrinth()[i][j].isExit()) {
						if (window.getEngine().getLabyrinth()[i][j]
								.exitUnlocked())
							g.drawImage(doorOpen, i * window.BOX, j
									* window.BOX, null);
						else
							g.drawImage(doorClosed, i * window.BOX, j
									* window.BOX, null);
					}
				}
			}
		}

		 // Draw the monsters at their specific position
//		System.out.println("METHOD GameField.paint: Monster Größe" + window.getEngine().getMyMonster().size()); 
		LinkedList<Monster> monsterList = window.getEngine().getMyMonster();
		for (int i = 0; i < monsterList.size(); i++) {
		 Monster m = monsterList.get(i);
		 boolean event = window.getEngine().getMyPlayer().hasKey();
	
		 // At this point every monster is called. So an
		 // attacking order is called, if the player is
		 // in range. Otherwise the fsm is called to decide what the monster
		 // should do.
			if (event) {
//				m.move();
			} else {
				int box = window.BOX;
				Player s = window.player;

				double p = m.cooldownRate();
				g.setColor(Color.RED);
				g.drawImage(fireball, (int) (((1 - p) * m.getXPos() + (p) * s.getXPos()) * box) + box / 2,
						(int) (((1 - p) * m.getYPos() + (p) * s.getYPos()) * box) + box / 2, 8, 8, null);
			}
		
		 // Draw the monster, if it's present from the beginning on
		 if (m.getType() == 0)
		 drawMonster(g, m);
		 // Draw the monster, if it only appears after the event 'take key'
		 else if (event && m.getType() == 1)
		 drawMonster(g, m);
		
		 }
		
		// Draw the player at its position
		g.drawImage(player, window.getEngine().getMyPlayer().getXPos()
				* window.BOX, window.getEngine().getMyPlayer().getYPos()
				* window.BOX, null);


	
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
			g.drawImage(monster, m.getXPos() * window.BOX, m.getYPos()
					* window.BOX, null);
			
			// Monster Health Points
			g.setColor(Color.GREEN);
			g.fillRect(m.getXPos() * window.BOX, m.getYPos() * window.BOX - 2,
					m.getHealth(), 2);

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
				+ Math.pow(window.getEngine().getMyPlayer().getYPos() - j, 2)) < 4 || !window.mistOn);
	}

}
