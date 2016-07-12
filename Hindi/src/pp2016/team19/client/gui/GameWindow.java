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
import pp2016.team19.shared.Monster;
import pp2016.team19.shared.Player;
import pp2016.team19.shared.Tile;


/**
 * <h1> class for the window of the whole application. </h1>
 * @author Felizia Langsdorf, 6002960
 * @author Progprak-Team
 *
 */

public class GameWindow extends JFrame implements KeyListener, MouseListener, Runnable {

	private static final long serialVersionUID = 1L;
	
	//the different JPanels
	private LoginPanel loginpanel;
	private MenuPanel menupanel;

	private MenuBar menubar;
	private GameField gamefield;
	private Statusbar statusbar;

	private Highscore highscore = null;
	private Controls controls;
	
	//clientengine
	public ClientEngine engine;
	//game attributes
	public LinkedList<Monster> monsterList;
	public Player player = new Player();
	public Tile[][] level;
	public int currentLevel = 0;
	public boolean gameWon = false;
	public boolean gameLost = false;
	public long startTime;
	public int neededTime;
	
	//player boolean attributes
	private boolean playerCheck = false;
	private boolean playerInHighscore = false;
	//panel visibility boolean attributes
	public boolean minifieldShown = false;
	public boolean highscoreShown = false;
	public boolean controlsShown = false;
	public boolean gamefieldShown = false;
	public boolean menuShown = false;
	public boolean loginShown = false;
	//number of levels
	public final int MAXLEVEL = 5;
	//labyrinth width and height
	public final int WIDTH = 16;
	public final int HEIGHT = 16;
	//tile size: 94*94 pixel
	public final int BOX = 94;
	//measure variable for the statusbar
	public final int SBox = 32;

	/**
	 * constructor of the window
	 * @author Felizia Langsdorf, 6002960        
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
		this.player = this.engine.getMyPlayer(); 
		initializeJFrame(width, height, title);  
	}

	/**
	 * 
	 * initializes the frame where login, registration and welcome-menu is shown
	 * @author Felizia Langsdorf, 6002960 
	 * @param width
	 *            the width of the window
	 * @param height
	 *            the height of the window
	 * @param title
	 *            title of the window
	 *      
	 */
	
	public void initializeJFrame(int width, int height, String title){
		//layout of the window
		this.setLayout(new BorderLayout());
		//loginpanel and menupanel
		this.loginpanel = new LoginPanel(this);
		this.menupanel = new MenuPanel(this);	
		//sets the sizes of the panels
		menupanel.setPreferredSize(new Dimension(width, height));
		loginpanel.setPreferredSize(new Dimension(width, height));
		//add the loginpanel in the middle of the window 
		this.add(loginpanel, BorderLayout.CENTER);	
		menuShown = false;
		loginShown = true;		
		this.requestFocus();
		this.pack();
		loginpanel.repaint();
		
		final Dimension d = this.getToolkit().getScreenSize();
		this.setLocation((int) ((d.getWidth() - this.getWidth()) / 2), (int) ((d.getHeight() - this.getHeight()) / 2));		
		this.setResizable(false);
		this.setTitle(title);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * 
	 * initializes the frame for the actual game 
	 * @author Felizia Langsdorf, 6002960
	 * @param width
	 *            the width of the window
	 * @param height
	 *            the height of the window
	 * @param title
	 *            title of the window
	 */

	public void initializeGameframe(int width, int height, String title) {
		//game request which starts the game
		this.engine.startGameRequest(this.engine.getPlayerID());
		// Layout of the window
		this.setLayout(new BorderLayout());
		//components of the game 
		this.gamefield = new GameField(this);
		this.statusbar = new Statusbar(this);
		this.menubar = new MenuBar(this);
		this.controls = new Controls();
		this.highscore = new Highscore(this);
		// Setting the desired sizes
		gamefield.setPreferredSize(new Dimension(5*BOX, 5*BOX));
		statusbar.setPreferredSize(new Dimension(5 * SBox, height));
		controls.setPreferredSize(new Dimension(width, height));
		highscore.setPreferredSize(new Dimension(width, height));
		//method which adds the game components
		showGameField();		
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
	 * sets the login panel in the window after logout
	 * 
	 */
	
	public void showLogin() {
		if(menuShown){ // if the user is in the menu 
		menuShown = false;
		loginShown = true;
		this.remove(menupanel); //remove menu 
		this.add(loginpanel, BorderLayout.CENTER); //add the loginpanel in the window
		this.requestFocus();
		this.pack();
		loginpanel.repaint(); 
		}else{ //if user is logging out after playing
			highscoreShown = false;
			controlsShown = false;
			gamefieldShown = false;
			menuShown = false;
			loginShown = true;
			//remove everything
			this.remove(highscore); 
			this.remove(controls);
			this.remove(gamefield);
			this.remove(menubar);
			this.remove(statusbar);
			this.remove(menupanel);
			this.add(loginpanel, BorderLayout.CENTER); //add the loginpanel in the window
			this.requestFocus();
			this.pack();
			loginpanel.repaint();			
		}		
	}	
	
	/**
	 * adds the menupanel in the window
	 * @author Felizia Langsdorf, 6002960 
	 */

	public void showMenu() {
		loginShown = false;
		menuShown = true;
		this.remove(loginpanel);
		this.add(menupanel, BorderLayout.CENTER);
		this.requestFocus();
		this.pack();
		menupanel.repaint();
	}

	/**
	 * adds the gamefield in the window, plus statusbar and menubar
	 * @author Felizia Langsdorf, 6002960 
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
	 * adds the highscore panel in the window, removes everything else
	 * @author Felizia Langsdorf, 6002960 
	 * 
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
	 * adds the control panel in the window, removes everything else
	 * @author Felizia Langsdorf, 6002960 
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
	 * gets the gamefield
	 * @author Felizia Langsdorf, 6002960 
	 * @return gamefield
	 */

	public GameField getGameField() {
		return gamefield;
	}
	
	/**
	 * gets the statusbar
	 * @author Felizia Langsdorf, 6002960 
	 * @return statusbar
	 */

	public Statusbar getStatusbar() {
		return statusbar;
	}

	/**
	 * gets the highscore
	 * @author Felizia Langsdorf, 6002960 
	 * @return highscore
	 */

	public Highscore getHighscore() {
		return highscore;
	}
	
	/**
	 * gets the controls
	 * @author Felizia Langsdorf, 6002960 
	 * @return controls
	 */

	public Controls getControls() {
		return controls;
	}
	
	/**
	 * gets the menupanel
	 * @author Felizia Langsdorf, 6002960 
	 * @return menupanel
	 * 
	 */

	public MenuPanel getMenuPanel() {
		return menupanel;
	}
	
	/**
	 * gets the loginpanel
	 * @author Felizia Langsdorf, 6002960 
	 * @return loginpanel
	 */

	public LoginPanel getLoginPanel() {
		return loginpanel;
	}


	/**
	 * method for controlling the player with the mouse; 
	 * player moves to clicked position with the A* algorithm
	 * @author Felizia Langsdorf, 6002960
	 * @param m
	 *            mouseEvent
	 */

	public void mouseClicked(MouseEvent m) {
		
		//position of the click
		int mouseX = m.getX() / this.BOX -2;
		int mouseY = m.getY() / this.BOX -2;
		//current position of the player
		int playerX = this.getEngine().getMyPlayer().getXPos();
		int playerY = this.getEngine().getMyPlayer().getYPos();
		
		if (!gameWon) {
			if (!this.getEngine().getLabyrinth()[playerX+mouseX][playerY+mouseY].isRock()) { 	//tile is not a wall											
				this.getEngine().aStarRequest(playerX+mouseX, playerY+mouseY); //player moves to clicked position with a*
				if (!this.getEngine().getLabyrinth()[playerX+mouseX][playerY+mouseY].containsMonster())
					this.getEngine().attackRequest(); // attack the monster
			} else {
				System.out.println("METOD GameWindow.mouseClicekd: Tile is NOT walkable!");
	
			}
		}
	}
	
	//remaining methods of the MouseListener Interface which have to be implemented 

	/**
	 * @author Felizia Langsdorf, 6002960
	 * @param arg0 argument
	 */

	public void mouseEntered(MouseEvent arg0) {
	}
	
	/**
	 * @author Felizia Langsdorf, 6002960
	 * @param arg0 argument
	 */
	public void mouseExited(MouseEvent arg0) {
	}
	
	/**
	 * @author Felizia Langsdorf, 6002960
	 * @param arg0 argument
	 */
	public void mousePressed(MouseEvent arg0) {
	}
	
	/**
	 * @author Felizia Langsdorf, 6002960
	 * @param arg0 argument
	 */
	public void mouseReleased(MouseEvent arg0) {
	}

	/**
	 * method for controlling the player movements and actions with the keyboard
	 * 
	 * @author Progprak-Team
	 * @param e
	 *            KeyEvent
	 */

	public void keyPressed(KeyEvent e) {

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

				//attack monster with Q
			} else if (e.getKeyCode() == KeyEvent.VK_Q) {
				this.getEngine().attackRequest();
				
				// Press B for 'Use potion'
			} else if (e.getKeyCode() == KeyEvent.VK_B) {
			
				this.getEngine().usePotionRequest();
				// press space for collecting the item
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {

			if (this.getEngine().getLabyrinth()[this.getEngine().getMyPlayer().getXPos()][this.getEngine().getMyPlayer().getYPos()].isFloor()) {
				this.engine.collectItemRequest();
			} else if  (this.getEngine().getLabyrinth()[this.getEngine().getMyPlayer().getXPos()][this.getEngine().getMyPlayer().getYPos()].isExit()) {
				this.engine.openDoorRequest();
			}
			
		}
		} 
	}

	/**
	 * remaining methods of the KeyListener Interface, have to be implemented
	 * but not used
	 * @author Felizia Langsdorf, 6002960
	 * @param e KeyEvent
	 * 
	 */

	public void keyReleased(KeyEvent e) {
	}
	
	/**
	 * @author Felizia Langsdorf, 6002960
	 * @param e KeyEvent
	 */
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * method for resetting the game 
	 * source: Progprak sample
	 * @author Felizia Langsdorf, 6002960
	 */

	public void resetGame() {
		//starts a new game
		this.getEngine().startGameRequest(this.getEngine().getPlayerID());
		level = this.engine.getLabyrinth();
		//sets the level to 0 again
		currentLevel = -1;
		gameWon = false;
		gameLost = false;
		minifieldShown = false;
		nextLevel();
		playerInHighscore = false;
		startTime = System.currentTimeMillis();		
	}

	/**
	 * method for starting the game and painting the gamefield every 50ms
	 * @author Progprak-Team
	 * 
	 */

	// Gameloop
	public void run() {

		resetGame();

		do {
			// game is not yet won 
			if (!gameWon) {
				// Every 50ms the map is repainted
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
				
				//repainting
				getGameField().repaint();
				getStatusbar().repaint();
				if (playerCheck == true) {
					this.player = this.getEngine().getMyPlayer();

				} 
				} else {
					getGameField().repaint();
				}


		} while (true);

	}

	/**
	 * method for counting up the level 
	 * @author Progprak-Team
	 * 
	 */

	public void nextLevel() {
		currentLevel++;
	}
	
	/**
	 * gets the clientengine
	 * @author Felizia Langsdorf, 6002960
	 * @return engine
	 * 
	 */
	public ClientEngine getEngine() {
		return this.engine;
	}
	
	/**
	 * gets the player
	 * @author Felizia Langsdorf, 6002960
	 * @return player
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * gets the level
	 * @author Felizia Langsdorf, 6002960
	 * @return level
	 */
	public Tile[][] getLevel() {
		return this.level;
	}

	/**
	 * sets the boolean playercheck
	 * @author Felizia Langsdorf, 6002960 
	 * @param playerCheck a boolean to check if a player object is available
	 * 
	 */
	public void setTest(boolean playerCheck) {
		this.playerCheck = playerCheck;
	}

}
