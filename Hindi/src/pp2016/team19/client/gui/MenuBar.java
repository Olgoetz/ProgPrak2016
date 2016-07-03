package pp2016.team19.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * class for the menubar shown atop
 * @author Felizia Langsdorf, Matr_Nr: 6002960
 *
 */
public class MenuBar extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 1L;
		
	// JMenus 
    private JMenu game;
    private JMenu show;
	private JMenu logOut;

    //MenuItems 
    private JMenuItem newGame;
    private JMenuItem highscore;
    private JMenuItem quit;
    private JMenuItem showMap;
    private JMenuItem controls;
    private JMenuItem LogArrow;
    private ImageIcon arrow = new ImageIcon ("img//icon-arrow.png"); // arrow icon for logout
   
    private GameWindow window;
    
    /**
     *
     * @author Felizia Langsdorf, Matr_Nr: 6002960
     * @param window window of the application
     */
    
	public MenuBar(GameWindow window){
		this.window = window;
		
		//shown at the bar at the top of the window 
		game = new JMenu("Game");
		show = new JMenu("Show");
		logOut = new JMenu("LogOut");
        
        newGame = new JMenuItem("Start New Game");
        quit = new JMenuItem("Quit Game");
        highscore = new JMenuItem("Show Highscore");
        showMap = new JMenuItem("Show Mini-Map");
        controls = new JMenuItem("Show Control");
        LogArrow = new JMenuItem(arrow);
              
        newGame.addActionListener(this);
        highscore.addActionListener(this);
        quit.addActionListener(this);
        showMap.addActionListener(this);
        controls.addActionListener(this);
        LogArrow.addActionListener(this);
        
        
        game.add(newGame);
        game.add(quit);
        show.add(highscore);
        show.add(showMap);
        show.add(controls);
        logOut.add(LogArrow);   
        
        this.add(game);
        this.add(show);
        this.add(logOut);      
	}
	
// events after clicking on the menu items 
	/**
	 * actionPerformed method for the menuItems
	 * @author Felizia Langsdorf, Matr_Nr: 6002960
	 *
	 */
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == newGame){
			window.resetGame();
			window.showGameField();
		}else if(e.getSource() == highscore){
			if(window.highscoreShown){
				window.showGameField();
				highscore.setText("Show Highscore");
			}else{
				window.showHighscore();
				highscore.setText("Show Gamefield");
				controls.setText("Show Control");
			}
			
		}else if(e.getSource() == showMap){
			if(window.minifieldShown){
				showMap.setText("Show Mini-Map");
				window.minifieldShown = false;
			}else{
				window.minifieldShown = true;
				showMap.setText("Hide Mini-Map");
			}		
		}else if(e.getSource() == quit){
			System.exit(0);
		}else if(e.getSource() == controls){
			if(window.controlsShown){
			window.showGameField();
			controls.setText("Show Control");
		}else{
			window.showControls();
			controls.setText("Show Gamefield");
			highscore.setText("Show Highscore");
		}
		}else if(e.getSource()== LogArrow){
			window.showLogin();                  // shows simply the loginpanel again, method for proper logout is going to implemented
		}
	}
	
	/**
	 * 
	 * @author Felizia Langsdorf, Matr_Nr: 6002960
	 * @return highscore !!!!!xxxx ???
	 */
	public JMenuItem getHighscore(){
		return highscore;
	}
	
}
