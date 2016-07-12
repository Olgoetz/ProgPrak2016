package pp2016.team19.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * <h1> class for the menubar shown at the top of the window while playing. </h1>
 * @author Langsdorf, Felizia, 6002960
 *
 */
public class MenuBar extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 1L;
		
	// JMenus 
    private JMenu game;
    private JMenu extras;
    private JMenu logOut;
	

    //MenuItems 
    private JMenuItem newGame;
    private JMenuItem highscore;
    private JMenuItem quit;
    private JMenuItem showMap;
    private JMenuItem controls;
    private JMenuItem cheats;
    private JMenuItem LogArrow;
    private ImageIcon arrow = new ImageIcon ("img//icon-arrow.png"); //source: https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-arrow-right-b-128.png
    
    private GameWindow window;
    
    /**
     * @author Langsdorf, Felizia, 6002960
     * @param window window of the application
     */
    
	public MenuBar(GameWindow window){
		this.window = window;
		
		//shown at the bar at the top of the window 
		game = new JMenu("Game");
		extras = new JMenu("Extras");
		logOut = new JMenu("LogOut");
        
        newGame = new JMenuItem("Start New Game");
        quit = new JMenuItem("Quit Game");
        highscore = new JMenuItem("Show Highscore");
        showMap = new JMenuItem("Show Mini-Map");
        controls = new JMenuItem("Show Control");
        cheats = new JMenuItem("Cheats");
        LogArrow = new JMenuItem(arrow);
              
        newGame.addActionListener(this);
        highscore.addActionListener(this);
        quit.addActionListener(this);
        showMap.addActionListener(this);
        controls.addActionListener(this);
        cheats.addActionListener(this);
        LogArrow.addActionListener(this);
        
        
        game.add(newGame);
        game.add(quit);
        extras.add(highscore);
        extras.add(showMap);
        extras.add(controls);
        extras.add(cheats);
        logOut.add(LogArrow);   
        
        this.add(game);
        this.add(extras);
        this.add(logOut);      
	}
	

	/**
	 * actionPerformed method for the menuItems
	 * events after clicking on the items
	 * @author Langsdorf, Felizia, Matr_Nr: 6002960
	 *
	 */
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == newGame){
			window.resetGame(); //new Game initialized
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
			
		}else if(e.getSource() == showMap){ //then the minimap will appear 
			if(window.minifieldShown){
				showMap.setText("Show Mini-Map");
				window.minifieldShown = false;
			}else{
				window.minifieldShown = true;
				showMap.setText("Hide Mini-Map");
			}
		}else if(e.getSource() == cheats){
			JFrame input = new JFrame();
			String cheatMes = JOptionPane.showInputDialog(input, "Enter Cheat Code", "");
			if (cheatMes != null)
				window.getEngine().cheatRequest(cheatMes);
			//window.getEngine().getMyPlayer().makeCheater(true); !!!!!
		}else if(e.getSource() == quit){
			window.getEngine().quitGameRequest();
			System.exit(0); // shuts down the whole application
		}else if(e.getSource() == controls){
			if(window.controlsShown){ 
			window.showGameField();
			controls.setText("Show Control");
		}else{
			window.showControls();//shows how to play the game
			controls.setText("Show Gamefield");
			highscore.setText("Show Highscore");
		}
		}else if(e.getSource()== LogArrow){
			window.getEngine().serverSignOutRequest(); //logout
		}
	}
	
	/**
	 * @author Langsdorf, Felizia,6002960
	 * @return highscore 
	 */
	public JMenuItem getHighscore(){
		return highscore;
	}
	
}
