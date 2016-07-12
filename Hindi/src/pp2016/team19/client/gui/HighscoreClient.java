package pp2016.team19.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;



/**
 * <h1> class for the highscore panel </h1>
 * @author Langsdorf, Felizia, 6002960
 *
 */

public class HighscoreClient extends JPanel {

	private static final long serialVersionUID = 1L;
	//JFrame gamewindow
	private GameWindow window;
	
	/**
	 * constructor 
	 * @author Langsdorf, Felizia, 6002960
	 * @param window the gamewindow
	 *
	 */
	public HighscoreClient(GameWindow window){
		this.window = window;
	}

		
		/**
		 * paint method (source: Progprak-Team) 
		 * @author Langsdorf, Felizia, 6002960
		 * @param g graphics object
		 * 
		 */
		
		public void paint(Graphics g){
			Image img = null; 
			
			try{
				//the heading
				img = ImageIO.read(new File("img//highscores.png")); //source: http://javascriptgamer.com/brickslayer/sprites/highscores.png
			}catch(IOException e){ 
				System.err.println("Error while loading one of the images.");
			}
			
			//paint the background gray
			for(int i = 0; i < 16; i++){
				for(int j = 0; j < 17; j++){
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(0, 0, this.getWidth() , this.getHeight());
				}
			}
			//draw the heading
			g.drawImage(img, 20, 40, null);
			g.setColor(Color.WHITE);
			
			//run through the monsterlist and get the names and times 
			for (int i = 0; i < this.window.getEngine().getHighscore().size(); i++) {
				String name = this.window.getEngine().getHighscore().get(i).getName(); //name of the player
				int time = this.window.getEngine().getHighscore().get(i).getTime(); //playtime of the player
				
				//paint the name and the time
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
				g.drawString((i + 1) + ".  " + name, 80, 150 + 30 * (i + 1));
				g.drawString("" + time, 400, 150 + 30 * (i + 1));
			}
		}
	}
	
