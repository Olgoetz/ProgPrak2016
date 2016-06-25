package pp2016.team19.client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * 
 * @author Felizia Langsdorf, Matr_Nr: 6002960
 *
 */

// the frame of the whole application

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
	public Player player;
	public Tile[][] level;
	public ClientEngine engine;
	
	public int currentLevel = 0;
	public boolean gameWon = false;
	public boolean gameLost = false;
	public long startTime;
	public int neededTime;
	public boolean mistOn = true;
	
	private boolean playerInHighscore = false;
	public boolean highscoreShown = false;
	public boolean controlsShown = false;
	public boolean gamefieldShown = false;
	public boolean menuShown = false;
	public boolean loginShown = false;

	public final int MAXLEVEL = 5;
	public final int WIDTH = 16;
	public final int HEIGHT = 16;
	public final int BOX = 32;

	// constructor for the Gamewindow, is called in the main
	public GameWindow(ClientEngine engine, int width, int height, String title) {
		this.engine = engine;
		initializeJFrame(width, height, title);	
	}
	
	public void initializeJFrame(int width, int height, String title) {
		// Layout of the window
		this.setLayout(new BorderLayout());
		
		// Create objects of the panels
		this.loginpanel = new LoginPanel(this);
		this.menupanel = new MenuPanel(this);
		
		this.gamefield = new GameField(this);
		this.statusbar = new Statusbar(this);
		this.menubar = new MenuBar(this);
		
		this.controls = new Controls();
		this.highscore = new Highscore();
				
		// Setting the desired sizes
		gamefield.setPreferredSize(new Dimension(width, height));
		statusbar.setPreferredSize(new Dimension(5* BOX, height));
		controls.setPreferredSize(new Dimension(width, height + BOX));
		highscore.setPreferredSize(new Dimension(width, height + BOX));
		menupanel.setPreferredSize(new Dimension(width, height));
		loginpanel.setPreferredSize(new Dimension(width, height));
		
		// first the Loginpanel is on screen
		//showLogin();
		showMenu();
		// Center the window on the screen
		final Dimension d = this.getToolkit().getScreenSize();
		this.setLocation((int) ((d.getWidth() - this.getWidth()) / 2),
				(int) ((d.getHeight() - this.getHeight()) / 2));
		// Default setup
		// add KeyListener and MouseListener for control the game with mouse and buttons
		this.addKeyListener(this);
		gamefield.addMouseListener(this);
		this.setResizable(false);
		this.setTitle(title);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public void showLogin(){
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
	
	public void showMenu(){
		highscoreShown = false;
		controlsShown = false;
		gamefieldShown = false;
		loginShown = false;
		menuShown = true;
		this.remove(highscore);
		this.remove(controls);
		this.remove(gamefield);
		this.remove(menubar);
		this.remove(statusbar);
		this.remove(loginpanel);
		this.add(menupanel, BorderLayout.CENTER);
		this.requestFocus();
		this.pack();
		menupanel.repaint();
	}

	public void showGameField() {
		// Remove everything
		highscoreShown = false;
		controlsShown = false;
		menuShown= false;
		gamefieldShown = true;
		this.remove(highscore);
		this.remove(controls);
		this.remove(menupanel);
		this.remove(loginpanel);
		// Create the gamefield
		this.add(gamefield, BorderLayout.CENTER); 
		this.add(statusbar, BorderLayout.EAST);
		this.add(menubar, BorderLayout.NORTH);
		// Activate the finished gamefield
		this.requestFocus();
		this.pack();
	}

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

	public void showControls() {
		// Remove everything
		controlsShown= true;
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

	// Getter
	public GameField getGameField() {return gamefield;}
	public Statusbar getStatusbar() {return statusbar;}
	public Highscore getHighscore() {return highscore;}
	public Controls getControls(){return controls;}
	public MenuPanel getMenuPanel(){return menupanel;}
	public LoginPanel getLoginPanel(){return loginpanel;}

	

// control with mouse 
// compares the coordinates of the mouseclick with the player positon 
// if mouseclick higher than player position, player moves up (mouse-y smaller than player-y) 
// if mouseclick lower than player position, player moves down (mouse-y bigger than player-y) 
// etc.
	
	public void mouseClicked(MouseEvent m) {
	 
//	 int xPos = player.getXPos();
//	 int yPos = player.getYPos();
	 int mouseX = m.getX()/32;
	 int mouseY = m.getY()/32;
//	 System.out.println("Mouse at: " + mouseX + ", " + mouseY);
//	 System.out.println("Player at: " + xPos + ", " + yPos);
 if (!gameWon) {
	if (!level[mouseX][mouseY].isRock()){  //if click y is higher than playerposition y and theres no wall, player moveDown()  
			while(!player.moveToPos(mouseX, mouseY)){
//				System.out.println("Step made");
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {}
				
				getGameField().repaint();
				
			} // player moves to the clicked position with the a* algorithm, method from player
			
//		}else if (mouseY < yPos && !(level[xPos][yPos - 1] instanceof Wall)) { // if click y is lower than playerposition y and theres no wall, player moveUp()
//	 				player.moveUp();
//		}else if (mouseX < xPos && !(level[xPos - 1][yPos] instanceof Wall)) { // if click x left from playerposition x and leftside no wall, player moveLeft()
//					player.moveLeft();
//		}else if (mouseX > xPos && !(level[xPos + 1][yPos] instanceof Wall)) { // if click x right from playerposition x and rightside no wall, player moveRight()
//					player.moveRight();
		} 
 	}
}

// remaining methods of the MouseListener Interface which have to be implemented but not used
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	
	
	// control with arrow keys 
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
				if (yPos > 0 && !(level[xPos][yPos - 1].isRock()))
					player.moveUp();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (yPos < HEIGHT - 1 && !(level[xPos][yPos + 1].isRock()))
					player.moveDown();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (xPos > 0 && !(level[xPos - 1][yPos].isRock()))
					player.moveLeft();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (xPos < WIDTH - 1 && !(level[xPos + 1][yPos].isRock()))
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
			if (level[player.getXPos()][player.getYPos()].containsKey()) {
				player.takeKey();
				level[player.getXPos()][player.getYPos()].setContainsKey(false);
			}
			// Take a potion
			else if (level[player.getXPos()][player.getYPos()].containsPotion()) {
				player.takePotion(new Potion(20));		
				level[player.getXPos()][player.getYPos()].setContainsPotion(false);
			}
			// Use the key
			if (level[player.getXPos()][player.getYPos()].isExit()) {
				if (! (level[player.getXPos()][player.getYPos()]).exitUnlocked() && player.hasKey()) {
					level[player.getXPos()][player.getYPos()].setExitUnlocked(true);
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

// remaining methods of the KeyListner interface 
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	public void resetGame() {

		player = new Player("img//player.png", this);
		monsterList = new LinkedList<Monster>();
		level = new Tile[WIDTH][HEIGHT];

		currentLevel = 0;
		gameWon = false;
		gameLost = false;
		mistOn = true;
		nextLevel();
		playerInHighscore = false;
		startTime = System.currentTimeMillis();
	}
	
	
	
	// Gameloop
	public void run() {
		resetGame();

		do {

			if (!gameWon) {
				// Every 50ms the map is repainted
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {}

				getGameField().repaint();
				//getMiniField().repaint();
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
					//getMiniField().repaint();
				}
			}

		} while (true);

	}
	
	// is gonna change cause of level generating by chance
	public void nextLevel() {
		currentLevel++;

		level = engine.getLabyrinth();

	}


}
