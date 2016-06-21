package pp2016.team19.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MenuBar extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	
	// JMenus 
    private JMenu game;
    private JMenu show;
	//private JMenu help;
	private JMenu logOut;

    //MenuItems 
    private JMenuItem newGame;
    private JMenuItem highscore;
    private JMenuItem quit;
    private JMenuItem showMap;
    private JMenuItem controls;
    private JMenuItem LogArrow;
    private ImageIcon arrow = new ImageIcon ("img//icon-arrow.png");
   
    private GameWindow window;
    
	public MenuBar(GameWindow window){
		this.window = window;
		
		game = new JMenu("Game");
		show = new JMenu("Show");
		//help = new JMenu("Help");
		logOut = new JMenu("LogOut");
        
        newGame = new JMenuItem("Start New Game");
        quit = new JMenuItem("Quit Game");
        highscore = new JMenuItem("Show Highscore");
        showMap = new JMenuItem("Show Map");
        controls = new JMenuItem("Show Control");
        LogArrow = new JMenuItem(arrow);
              
        newGame.addActionListener(this);
        highscore.addActionListener(this);
        quit.addActionListener(this);
        showMap.addActionListener(this);
        controls.addActionListener(this);
        LogArrow.addActionListener(this);
        
        //help.addActionListener(this);
        
        game.add(newGame);
        game.add(quit);
        show.add(highscore);
        show.add(showMap);
        show.add(controls);
        //help.add(controls);
        logOut.add(LogArrow);   
        
        this.add(game);
        this.add(show);
        //this.add(help);
        this.add(logOut);      
	}
	
// events after clicking on the menu items 
	
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
			}
			
		}else if(e.getSource() == showMap){
			if(window.mistOn){
				window.mistOn = false;
				showMap.setText("Hide Map");
			}else{
				window.mistOn = true;
				showMap.setText("Show Map");
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
		}
		}else if(e.getSource()== LogArrow){
			System.exit(0);                  // quit game and initiate logout???
		}
	}
	
	public JMenuItem getHighscore(){
		return highscore;
	}
	
}
