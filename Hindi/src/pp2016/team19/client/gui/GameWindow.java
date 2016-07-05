package pp2016.team19.client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.JFrame;
import pp2016.team19.client.engine.ClientEngine;
import pp2016.team19.shared.Door;
import pp2016.team19.shared.Floor;
import pp2016.team19.shared.GameObject;
import pp2016.team19.shared.Key;
import pp2016.team19.shared.Monster;
import pp2016.team19.shared.Player;
import pp2016.team19.shared.Potion;
import pp2016.team19.shared.Tile;
import pp2016.team19.shared.Wall;

/**
 * <h1> class for the window of the whole application <h1>
 * 
 * @author Felizia Langsdorf, Matr_Nr: 6002960
 *
 */

public class GameWindow extends JFrame implements KeyListener, MouseListener, Runnable {

	private static final long serialVersionUID = 1L;
	
	private LoginPanel loginpanel;
	private MenuPanel menupanel;

	private MenuBar menubar;
	private GameField gamefield;
	private Statusbar statusbar;

	private Highscore highscore;
	private Controls controls;

	public LinkedList<Monster> monsterList;
	public Player player = new Player();
	public Tile[][] level;
	public ClientEngine engine;

	public int currentLevel = 0;
	public boolean gameWon = false;
	public boolean gameLost = false;
	public long startTime;
	public int neededTime;
	public boolean minifieldShown = false;

	private boolean playerCheck = false;

	private boolean playerInHighscore = false;
	public boolean highscoreShown = false;
	public boolean controlsShown = false;
	public boolean gamefieldShown = false;
	public boolean menuShown = false;
	public boolean loginShown = false;
	
	public final int MAXLEVEL = 5;
	public final int WIDTH = 16;
	public final int HEIGHT = 16;
	public final int BOX = 94;
	public final int SBox = 32;

	/**
	 * @author Felizia Langsdorf, 6002960
	 * 
	 *         constructor of the window
	 * @param engine
	 *            engine of the client
	 * @param width
	 *            the width of the window
	 * @param height
	 *            the height of the window
	 * @param title
	 *            title of the window 
	 *            
	 */

	public GameWindow(ClientEngine engine, int width, int height, String title) {
		this.engine = engine;
		this.engine.startGameRequest(this.engine.getPlayerID());
		this.player = this.engine.getMyPlayer();

		// try {
		// this.player.setImage(ImageIO.read(new File("img//player.png")));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		initializeJFrame(width, height, title);

	}

	/**
	 * @author Felizia Langsdorf, 6002960 initializes the Frame
	 * @param width
	 *            the width of the window
	 * @param height
	 *            the height of the window
	 * @param title
	 *            title of the window
	 */
	
	public void initializeJFrame(int width, int height, String title){
		this.setLayout(new BorderLayout());
		this.loginpanel = new LoginPanel(this);
		this.menupanel = new MenuPanel(this);
		
		menupanel.setPreferredSize(new Dimension(width, height));
		loginpanel.setPreferredSize(new Dimension(width, height));
		this.add(loginpanel, BorderLayout.CENTER);
		
		menuShown = false;
		loginShown = true;
		
		this.requestFocus();
		this.pack();
		loginpanel.repaint();
		
		final Dimension d = this.getToolkit().getScreenSize();
		this.setLocation((int) ((d.getWidth() - this.getWidth()) / 2), (int) ((d.getHeight() - this.getHeight()) / 2));
		this.addKeyListener(this);
		
		this.setResizable(false);
		this.setTitle(title);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void initializeGameframe(int width, int height, String title) {
		// Layout of the window
		this.engine.startGameRequest(this.engine.getPlayerID());
		this.setLayout(new BorderLayout());
		
		// Create objects of the panels
		// this.connectpanel = new ServerConnection(this);
//		this.loginpanel = new LoginPanel(this);
//		this.menupanel = new MenuPanel(this);

		this.gamefield = new GameField(this);
		this.statusbar = new Statusbar(this);
		this.menubar = new MenuBar(this);
		this.controls = new Controls();
		this.highscore = new Highscore();

		// Setting the desired sizes
		gamefield.setPreferredSize(new Dimension(5*BOX, 5*BOX));
		statusbar.setPreferredSize(new Dimension(5 * SBox, height));
		controls.setPreferredSize(new Dimension(width, height));
		highscore.setPreferredSize(new Dimension(width, height));
//		menupanel.setPreferredSize(new Dimension(width, height));
//		loginpanel.setPreferredSize(new Dimension(width, height));
		showGameField();

		// first the Loginpanel is on screen
		//showLogin();
		
		// Center the window on the screen
		final Dimension d = this.getToolkit().getScreenSize();
		this.setLocation((int) ((d.getWidth() - this.getWidth()) / 2), (int) ((d.getHeight() - this.getHeight()) / 2));
		// add KeyListener and MouseListener for control the game with mouse and keyboard
		this.addKeyListener(this);
		gamefield.addMouseListener(this);
	
		this.setResizable(false);
		this.setTitle(title);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


	/**
	 * @author Felizia Langsdorf, 6002960 
	 * sets the login panel in the window
	 * 
	 */

	public void showLogin() {
		highscoreShown = false;
		controlsShown = false;
		gamefieldShown = false;
		menuShown = false;
		loginShown = true;
		this.remove(highscore);
		this.remove(controls);
		this.remove(gamefield);
		this.remove(menubar);
		this.remove(statusbar);
		this.remove(menupanel);
		this.add(loginpanel, BorderLayout.CENTER);
		this.requestFocus();
		this.pack();
		loginpanel.repaint();
	}

	/**
	 * @author Felizia Langsdorf, 6002960 
	 * sets the menu panel in the window
	 * 
	 */

	public void showMenu() {
//		highscoreShown = false;
//		controlsShown = false;
//		gamefieldShown = false;
		loginShown = false;
		menuShown = true;
//		this.remove(highscore);
//		this.remove(controls);
//		this.remove(gamefield);
//		this.remove(menubar);
//		this.remove(statusbar);
		this.remove(loginpanel);
		this.add(menupanel, BorderLayout.CENTER);
		this.requestFocus();
		this.pack();
		menupanel.repaint();
	}

	/**
	 * @author Felizia Langsdorf, 6002960 
	 * sets the gamefield in the window,
	 * which includes the statusbar and the menubar
	 * 
	 */

	public void showGameField() {
		// Remove everything
		highscoreShown = false;
		controlsShown = false;
		menuShown = false;
		gamefieldShown = true;
		this.remove(highscore);
		this.remove(controls);
		this.remove(menupanel);
		this.remove(loginpanel);
		// Create the gamefield, statusbar and menubar
		this.add(gamefield, BorderLayout.CENTER);
		this.add(statusbar, BorderLayout.EAST);
		this.add(menubar, BorderLayout.NORTH);
		// Activate the finished gamefield
		this.requestFocus();
		this.pack();
	}

	/**
	 * @author Felizia Langsdorf, 6002960 
	 * sets the highscore panel in the window
	 *         removes everything else
	 */

	public void showHighscore() {
		// Remove everything
		highscoreShown = true;
		controlsShown = false;
		menuShown = false;
		gamefieldShown = false;
		this.remove(gamefield);
		this.remove(statusbar);
		this.remove(controls);
		this.remove(menupanel);
		this.remove(loginpanel);
		// Create the display of the highscore
		this.add(highscore, BorderLayout.CENTER);
		// Activate the display of the highscore
		this.requestFocus();
		this.pack();
		highscore.repaint();
	}

	/**
	 * @author Felizia Langsdorf, 6002960 
	 * sets the control panel in the window
	 *         removes everything else
	 */

	public void showControls() {
		// Remove everything
		controlsShown = true;
		highscoreShown = false;
		this.remove(gamefield);
		this.remove(statusbar);
		this.remove(highscore);
		this.remove(menupanel);
		this.remove(loginpanel);
		// Create the display of the controls
		this.add(controls, BorderLayout.CENTER);
		// Activate the display of the controls
		this.requestFocus();
		this.pack();
		controls.repaint();
	}

	/**
	 * @author Felizia Langsdorf, 6002960 
	 * getter for the panels: gamefield,
	 * statusbar, highscore, control, menu, login
	 * 
	 */

	public GameField getGameField() {
		return gamefield;
	}

	public Statusbar getStatusbar() {
		return statusbar;
	}

	public Highscore getHighscore() {
		return highscore;
	}

	public Controls getControls() {
		return controls;
	}

	public MenuPanel getMenuPanel() {
		return menupanel;
	}

	public LoginPanel getLoginPanel() {
		return loginpanel;
	}


	/**
	 * @author Felizia Langsdorf, 6002960
	 * @param m
	 *            mouseEvent
	 * method for controlling the player with the mouse; 
	 * player moves to clicked position with the A* algorithm
	 * 
	 */

	public void mouseClicked(MouseEvent m) {

		int mouseX = m.getX() / this.BOX -2;
		int mouseY = m.getY() / this.BOX -2;
		int playerX = this.getEngine().getMyPlayer().getXPos();
		int playerY = this.getEngine().getMyPlayer().getYPos();
//		System.out.println("Mouse at: " + mouseX + ", " + mouseY);
//		System.out.println("Player at: " + playerX + ", " + playerY);
		if (!gameWon) {
			if (!this.getEngine().getLabyrinth()[playerX+mouseX][playerY+mouseY].isRock()) { 												
				this.getEngine().aStarRequest(playerX+mouseX, playerY+mouseY);			
			} else {
				System.out.println("METOD GameWindow.mouseClicekd: Tile is NOT walkable!");
	
			}
		}
	}

	/**
	 * remaining methods of the MouseListener Interface which have to be
	 * implemented 
	 * 
	 * @author Felizia Langsdorf, 6002960
	 * 
	 */

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	/**
	 * method for controlling the player movements and actions with the keyboard
	 * 
	 * @author Felizia Langsdorf, 6002960
	 * @param e
	 *            KeyEvent
	 */

	public void keyPressed(KeyEvent e) {
		// Current position of the player

		// System.out.println("OLDPosition of Player in game:" +
		// this.engine.getMyPlayer().getXPos() + " " +
		// this.engine.getMyPlayer().getYPos());
		// Ask for the keyboard entrys of the arrow keys.
		// It is checked whether the next step is valid.
		// Does the character stay within the borders of
		// the arrays? If so: Is the following field walkable?
		// If both is true, walk this next step.
		if (!gameWon) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {

				this.engine.moveCharacterRequest(this.engine.getMyPlayer().getXPos(),
						this.engine.getMyPlayer().getYPos(), 0);

			} else if (e.getKeyCode() == KeyEvent.VK_UP) {

				this.engine.moveCharacterRequest(this.engine.getMyPlayer().getXPos(),
						this.engine.getMyPlayer().getYPos(), 1);

			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

				this.engine.moveCharacterRequest(this.engine.getMyPlayer().getXPos(),
						this.engine.getMyPlayer().getYPos(), 2);

			
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

				this.engine.moveCharacterRequest(this.engine.getMyPlayer().getXPos(),
						this.engine.getMyPlayer().getYPos(), 3);

			
			} else if (e.getKeyCode() == KeyEvent.VK_Q) {
				this.getEngine().attackRequest();
				
				// Press B for 'Use potion'
			} else if (e.getKeyCode() == KeyEvent.VK_B) {
			
				this.getEngine().usePotionRequest();
				
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {

			if (this.getEngine().getLabyrinth()[this.getPlayer().getXPos()][this.getPlayer().getYPos()].isFloor()) {
				this.engine.collectItemRequest();
			} else if  (this.getEngine().getLabyrinth()[this.getPlayer().getXPos()][this.getPlayer().getYPos()].isExit()) {
				this.engine.openDoorRequest();
			}
			
		}
		} // end if gamewon
	}

	/**
	 * remaining methods of the KeyListener Interface, have to be implemented
	 * but not used
	 * 
	 * @author Felizia Langsdorf, 6002960
	 * 
	 */

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	/**
	 * method for reset the game
	 * 
	 * @author Felizia Langsdorf, 6002960
	 * 
	 */

	public void resetGame() {

		// TODO: Server Request f�r GameRestart
		// player = new Player("img//player.png", this);
		this.engine.startGameRequest(this.engine.getPlayerID());
		// spiel zurücksetzen, was muss alles ausgeführt werden, welche
		// variablen gesetzt werden :)

		// das war im alten code drin! hier wurde auch das level "übergeben"!!!

		// spieler = new Spieler("img//warrior.png", this);
		// monsterListe = new LinkedList<Monster>();
		// level = new Spielelement[16][16];
		// this.player = this.engine.getMyPlayer();
		// System.out.println("METHOD GameWindow.resetGame:" +
		// this.player.toString());

		level = this.engine.getLabyrinth();

		currentLevel = 0;
		gameWon = false;
		gameLost = false;
		minifieldShown = false;
		nextLevel();
		playerInHighscore = false;
		startTime = System.currentTimeMillis();

	}

	/**
	 * method for starting the game and painting the gamefield every 50ms
	 * 
	 * @author Felizia Langsdorf, 6002960
	 * 
	 */

	// Gameloop
	public void run() {

		resetGame();

		do {

			if (!gameWon) {
				// Every 50ms the map is repainted
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}

				getGameField().repaint();
				getStatusbar().repaint();
				if (playerCheck == true) {
					this.player = this.getEngine().getMyPlayer();

				} // ende testt
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

	/**
	 * method for counting up the level 
	 * @author Felizia Langsdorf, 6002960
	 * 
	 */

	public void nextLevel() {
		currentLevel++;
	}
	
	/**
	 * method for counting up the level 
	 * @author Felizia Langsdorf, 6002960
	 * 
	 */
	public ClientEngine getEngine() {
		return this.engine;
	}
	
	/**
	 * @author Felizia Langsdorf, 6002960
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * @author Felizia Langsdorf, 6002960 
	 */
	public Tile[][] getLevel() {
		return this.level;
	}

	/**
	 * @author Felizia Langsdorf, 6002960 
	 */
	public void setTest(boolean playerCheck) {
		this.playerCheck = playerCheck;
	}

}
