package pp2016.team19.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pp2016.team19.shared.HighScoreElement;

/**
 * <h1> class for the panel showing the highscore <h1>
 * @author Felizia Langsdorf, Matr_Nr: 6002960
 *
 */

public class Highscore extends JPanel {

	private static final long serialVersionUID = 1L;
	private GameWindow window;
	
	public Highscore(GameWindow window){
		this.window = window;
	}

		
		/**
		 * paint method
		 * @author Felizia Langsdorf, Matr_Nr: 6002960
		 *
		 */
		
		public void paint(Graphics g){
			Image img = null; 
			
			try{
				img = ImageIO.read(new File("img//highscores.png")); //source: http://javascriptgamer.com/brickslayer/sprites/highscores.png
			}catch(IOException e){ 
				System.err.println("Error while loading one of the images.");
			}
			
			
			for(int i = 0; i < 16; i++){
				for(int j = 0; j < 17; j++){
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(0, 0, this.getWidth() , this.getHeight());
				}
			}
			g.drawImage(img, 20, 40, null);
			g.setColor(Color.WHITE);
			
			for (int i = 0; i < this.window.getEngine().getHighscore().size(); i++) {
				String name = this.window.getEngine().getHighscore().get(i).getName();
				int time = this.window.getEngine().getHighscore().get(i).getTime();

				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
				g.drawString((i + 1) + ".  " + name, 80, 150 + 30 * (i + 1));
				g.drawString("" + time, 400, 150 + 30 * (i + 1));
			}
		}
	}
	
