package pp2016.team19.client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JFrame;

import pp2016.team19.shared.Door;
import pp2016.team19.shared.Floor;
import pp2016.team19.shared.GameObject;
import pp2016.team19.shared.Key;
import pp2016.team19.shared.Monster;
import pp2016.team19.shared.Player;
import pp2016.team19.shared.Potion;
import pp2016.team19.shared.Wall;

public class GameWindow extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;

	private GameField gamefield;
	private Statusbar statusbar;
	private Highscore highscore;
	private MenuBar menubar;
	private Controls controls;

	public LinkedList<Monster> monsterList;
	public Player player;
	public GameObject[][] level;

	public int currentLevel = 0;
	public boolean gameWon = false;
	public boolean gameLost = false;
	public long startTime;
	public int neededTime;
	public boolean mistOn = true;

	private boolean playerInHighscore = false;
	public boolean highscoreShown = false;

	public final int MAXLEVEL = 5;
	public final int WIDTH = 16;
	public final int HEIGHT = 16;
	public final int BOX = 32;

	public GameWindow(int width, int height, String title) {
		initializeJFrame(width, height, title);
		startNewGame();
	}

	public void initializeJFrame(int width, int height, String title) {
		// Layout of the window
		this.setLayout(new BorderLayout());
		// Create objects of the panels
		this.gamefield = new GameField(this);
		this.statusbar = new Statusbar(this);
		this.controls = new Controls();
		this.highscore = new Highscore();
		// Create menubar
		this.menubar = new MenuBar(this);
		// Setting the desired sizes
		gamefield.setPreferredSize(new Dimension(width, height));
		statusbar.setPreferredSize(new Dimension(width, BOX));
		controls.setPreferredSize(new Dimension(width, height + BOX));
		highscore.setPreferredSize(new Dimension(width, height + BOX));
		// Create the gamefield
		showGameField();
		// Center the window on the screen
		final Dimension d = this.getToolkit().getScreenSize();
		this.setLocation((int) ((d.getWidth() - this.getWidth()) / 2),
				(int) ((d.getHeight() - this.getHeight()) / 2));
		// Default setup
		this.addKeyListener(this);
		this.setResizable(false);
		this.setTitle(title);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void showGameField() {
		// Remove everything
		highscoreShown = false;
		this.remove(highscore);
		this.remove(controls);
		// Create the gamefield
		this.add(gamefield, BorderLayout.CENTER);
		this.add(statusbar, BorderLayout.SOUTH);
		this.add(menubar, BorderLayout.NORTH);
		// Activate the finished gamefield
		this.requestFocus();
		this.pack();
	}

	public void showHighscore() {
		// Remove everything
		highscoreShown = true;
		this.remove(gamefield);
		this.remove(statusbar);
		this.remove(controls);
		// Create the display of the highscore
		this.add(highscore, BorderLayout.CENTER);
		// Activate the display of the highscore
		this.requestFocus();
		this.pack();
		highscore.repaint();
	}

	public void showControls() {
		// Remove everything
		highscoreShown = false;
		this.remove(gamefield);
		this.remove(statusbar);
		this.remove(highscore);
		// Create the display of the controls
		this.add(controls, BorderLayout.CENTER);
		// Activate the display of the controls
		this.requestFocus();
		this.pack();
		controls.repaint();
	}

	// Getter for gamefield and statusbar
	public GameField getGameField() {return gamefield;}
	public Statusbar getStatusbar() {return statusbar;}
	public Highscore getHighscore() {return highscore;}

	// Methods of the KeyListener interface

	public void keyPressed(KeyEvent e) {
		// Current position of the player
		int xPos = player.getXPos();
		int yPos = player.getYPos();

		// Ask for the keyboard entrys of the arrow keys.
		// It is checked whether the next step is valid.
		// Does the character stay within the borders of
		// the arrays? If so: Is the following field walkable?
		// If both is true, walk this next step.
		if (!gameWon) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (yPos > 0 && !(level[xPos][yPos - 1] instanceof Wall))
					player.moveUp();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (yPos < HEIGHT - 1 && !(level[xPos][yPos + 1] instanceof Wall))
					player.moveDown();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (xPos > 0 && !(level[xPos - 1][yPos] instanceof Wall))
					player.moveLeft();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (xPos < WIDTH - 1 && !(level[xPos + 1][yPos] instanceof Wall))
					player.moveRight();
			} else if (e.getKeyCode() == KeyEvent.VK_Q) {
				Monster m = player.monsterToAttack();
				if (m != null)
					m.changeHealth(-BOX / 4);
			// Press B for 'Use potion'
			} else if (e.getKeyCode() == KeyEvent.VK_B){
				int change = player.usePotion();
				// Effect of the potion is increased, if new monsters spawn because of taking the key
				if (player.hasKey())
					player.changeHealth((int)(change*1.5));
				else
					player.changeHealth((int)(change*0.5));
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			// Take the key
			if (level[player.getXPos()][player.getYPos()] instanceof Key) {
				player.takeKey();
				level[player.getXPos()][player.getYPos()] = new Floor();
			}
			// Take a potion
			else if (level[player.getXPos()][player.getYPos()] instanceof Potion) {
				player.takePotion((Potion) level[player.getXPos()][player.getYPos()]);		
				level[player.getXPos()][player.getYPos()] = new Floor();
			}
			// Use the key
			if (level[player.getXPos()][player.getYPos()] instanceof Door) {
				if (!((Door) level[player.getXPos()][player.getYPos()]).isOpen() && player.hasKey()) {
					((Door) level[player.getXPos()][player.getYPos()]).setOpen();
					// After opening the door, the key has to be removed
					player.removeKey();
					if (currentLevel < MAXLEVEL)
						nextLevel();
					else {
						gameWon = true;
					}
				}
			}
		}

	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	public void resetGame() {

		player = new Player("img//player.png", this);
		monsterList = new LinkedList<Monster>();
		level = new GameObject[WIDTH][HEIGHT];

		currentLevel = 0;
		gameWon = false;
		gameLost = false;
		mistOn = true;
		nextLevel();
		playerInHighscore = false;
		startTime = System.currentTimeMillis();
	}

	// Gameloop
	public void startNewGame() {
		resetGame();

		do {

			if (!gameWon) {
				// Every 50ms the map is repainted
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {}

				getGameField().repaint();
				getStatusbar().repaint();

				if (player.getHealth() <= 0) {
					gameWon = true;
					gameLost = true;
				}
			} else {
				neededTime = (int) ((System.currentTimeMillis() - startTime) / 1000);

				if (!gameLost && !playerInHighscore) {
					getHighscore().addPlayerToHighScore(neededTime);
					getHighscore().repaint();
					playerInHighscore = true;
				} else {
					getGameField().repaint();
				}
			}

		} while (true);

	}

	public void nextLevel() {
		currentLevel++;

		Reader reader = new Reader("lvl//level" + currentLevel + ".txt", this);
		level = reader.getLevel();

	}

}
