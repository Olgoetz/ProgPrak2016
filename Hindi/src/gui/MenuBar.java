package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 1L;

    private JMenu game;
    private JMenu display;
	private JMenu help;
    	
    private JMenuItem newGame;
    private JMenuItem highscore;
    private JMenuItem quit;
    private JMenuItem showMap;
    private JMenuItem controls;

    private GameWindow window;
    
	public MenuBar(GameWindow window){
		this.window = window;
		
		game = new JMenu("Game");
		display = new JMenu("Display");
		help = new JMenu("Help");
        
        newGame = new JMenuItem("Start New Game");
        highscore = new JMenuItem("Show Highscore");
        quit = new JMenuItem("Quit Game");
        showMap = new JMenuItem("Show Map");
        controls = new JMenuItem("Controls");
        
        newGame.addActionListener(this);
        highscore.addActionListener(this);
        quit.addActionListener(this);
        showMap.addActionListener(this);
        controls.addActionListener(this);
        help.addActionListener(this);
        
        
        game.add(newGame);
        game.add(quit);
        display.add(highscore);
        display.add(showMap);
        help.add(controls);
        
        this.add(game);
        this.add(display);
        this.add(help);
	}
	
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
			window.showControls();
		}
	}
	
	public JMenuItem getHighscore(){
		return highscore;
	}
	
}
