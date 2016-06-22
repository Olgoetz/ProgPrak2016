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
import pp2016.team19.shared.Monster;
import pp2016.team19.shared.Player;
import pp2016.team19.shared.Potion;
import pp2016.team19.shared.Wall;

public class GameField extends JPanel {

	private static final long serialVersionUID = 1L;

	private Image floor, wall, doorOpen, doorClosed, key, potion, fireball;
	private GameWindow window;

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
		} catch (IOException e) {
			System.err.println("Error while loading one of the images.");
		}
	}

	public void paint(Graphics g) {

		// First, everything is going to be overpainted while repainting
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// Draw every single field
		for (int i = 0; i < window.WIDTH; i++) {
			for (int j = 0; j < window.HEIGHT; j++) {
				if (inRange(i, j)) {

					if (window.level[i][j] instanceof Wall) {
						// Here goes a wall
						g.drawImage(wall, i * window.BOX, j * window.BOX, null);
					} else if (window.level[i][j] instanceof Floor) {
						// This field is walkable
						g.drawImage(floor, i * window.BOX, j * window.BOX, null);
					} else if (window.level[i][j] instanceof Key) {
						// Here lies the key
						g.drawImage(floor, i * window.BOX, j * window.BOX, null);
						g.drawImage(key, i * window.BOX, j * window.BOX, null);
					} else if (window.level[i][j] instanceof Door) {
						// Here is the door
						g.drawImage(floor, i * window.BOX, j * window.BOX, null);
						if (((Door) window.level[i][j]).isOpen())
							g.drawImage(doorOpen, i * window.BOX, j
									* window.BOX, null);
						else
							g.drawImage(doorClosed, i * window.BOX, j
									* window.BOX, null);
					} else if (window.level[i][j] instanceof Potion) {
						// Here is the door
						g.drawImage(floor, i * window.BOX, j * window.BOX, null);
						// Here lies a potion
						g.drawImage(floor, i * window.BOX, j * window.BOX, null);
						g.drawImage(potion, i * window.BOX, j * window.BOX,
								null);
					}
				}
			}
		}

		// Draw the monsters at their specific position
		for (int i = 0; i < window.monsterList.size(); i++) {
			Monster m = window.monsterList.get(i);
			boolean event = window.player.hasKey();

			// At this point every monster is called. So an
			// attacking order is called, if the player is
			// in range. Otherwise the fsm is called to decide what the monster
			// should do.
			if (!m.attackPlayer(event)) {
				m.move();
			} else {
				int box = window.BOX;
				Player s = window.player;

				double p = m.cooldownRate();
				g.setColor(Color.RED);
				g.drawImage(
						fireball,
						(int) (((1 - p) * m.getXPos() + (p) * s.getXPos()) * box)
								+ box / 2, (int) (((1 - p) * m.getYPos() + (p)
								* s.getYPos()) * box)
								+ box / 2, 8, 8, null);
			}

			// Draw the monster, if it's present from the beginning on
			if (m.getType() == 0)
				drawMonster(g, m);
			// Draw the monster, if it only appears after the event 'take key'
			else if (event && m.getType() == 1)
				drawMonster(g, m);

		}

		// Draw the player at its position
		g.drawImage(window.player.getImage(), window.player.getXPos()
				* window.BOX, window.player.getYPos() * window.BOX, null);

		if (window.gameLost) {
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
			g.drawString("GAME OVER", getWidth() / 2 - 120, getHeight() / 2);
		} else {
			if (window.gameWon) {
				g.setColor(Color.WHITE);
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
				g.drawString("YOU WIN", getWidth() / 2 - 120, getHeight() / 2);
			}
		}
	}

	private void drawMonster(Graphics g, Monster m) {
		// Monster Health Points
		if (inRange(m.getXPos(), m.getYPos())) {
			g.drawImage(m.getImage(), m.getXPos() * window.BOX, m.getYPos()
					* window.BOX, null);
			g.setColor(Color.GREEN);
			g.fillRect(m.getXPos() * window.BOX, m.getYPos() * window.BOX - 2,
					m.getHealth(), 2);

		}
	}

	private boolean inRange(int i, int j) {
		return (Math.sqrt(Math.pow(window.player.getXPos() - i, 2)
				+ Math.pow(window.player.getYPos() - j, 2)) < 4 || !window.mistOn);
	}

}
