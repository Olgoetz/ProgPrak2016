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


public class Highscore extends JPanel {

	private static final long serialVersionUID = 1L;
	private LinkedList<HighScoreElement> highScore;
	
	
	
	// konstruktor fuer Highscore
	
		public Highscore(){

				highScore = new LinkedList<HighScoreElement>();		
				try {
					FileReader reader = new FileReader(new File("highscore.txt"));
					int c;
					String line = "";
				
					while((c = reader.read()) != -1){						
					if(c == '\n'){
						String[] temp = line.split("\t");
						highScore.add(new HighScoreElement(Integer.parseInt(temp[0].trim()), temp[1].trim()));					
						line = "";
					}else{
						line += (char) c;
					}
				}
				
				reader.close();
				
			} catch (IOException e) {
				System.out.println("Error while reading Highscore");
			}
			while(highScore.size() < 10){
				highScore.add(new HighScoreElement(1000, "Anonymous"));	
			}
		}
		
		public void addPlayerToHighScore(int time){
			String name = JOptionPane.showInputDialog("Please enter your name:");
			for(int i = 0; i < highScore.size(); i++){
				if(highScore.get(i).time > time){
					highScore.add(i, new HighScoreElement(time, name));
					i = highScore.size();
				}
			}
			
			try {
				FileWriter writer = new FileWriter(new File("highscore.txt"));
				for(int i = 0; i < 10; i++){
					writer.write(highScore.get(i).time + "\t" + highScore.get(i).name + "\n");
				}			

				writer.close();
				
			} catch (IOException e) {
				System.out.println("Error while writing Highscore");
			}
			
		}
		
		public LinkedList<HighScoreElement> getHighScore(){
			return highScore;
		}
		
		public void paint(Graphics g){
			Image img = null; // floor = null;
			
			try{
				img = ImageIO.read(new File("img//highscores.png"));
				//floor = ImageIO.read(new File("img//status.png"));
			}catch(IOException e){ }
			
			
			for(int i = 0; i < 16; i++){
				for(int j = 0; j < 17; j++){
					//g.drawImage(floor, 32*i,32*j,null);
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(0, 0, this.getWidth() , this.getHeight());
				}
			}
			g.drawImage(img, 20, 40, null);
			g.setColor(Color.WHITE);
			
			for (int i = 0; i < 10; i++) {
				String name = highScore.get(i).name;
				int time = highScore.get(i).time;

				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
				g.drawString((i + 1) + ".  " + name, 80, 150 + 30 * (i + 1));
				g.drawString("" + time, 400, 150 + 30 * (i + 1));
			}
		}
	}

	class HighScoreElement {
		
		String name;
		int time;
		
		public HighScoreElement(int points, String name){
			this.name = name;
			this.time = points;
		}
		
	}
