package pp2016.team19.server.engine;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import pp2016.team19.shared.HighScoreElement;

/**
 * <h1>This class provides the methods to access the highscore file database.</h1>
 * @author Schrader, Tobias, 5637252
 * @author ProgPrak-Team
 */
public class HighscoreServer {
private LinkedList<HighScoreElement> highScore;
	
	
	/**
	 * Constructor reads out highscores from files into LinkedList (source ProgPrak-Team)
	 * @author Schrader, Tobias, 5637252
	 */
	public HighscoreServer(){

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
			System.out.println("Error at reading Highscore");
		}
		while(highScore.size() < 10){
			highScore.add(new HighScoreElement(9999, "Anonymous"));	
		}
	}
	/**
	 * Method to add a score to the list (source:ProgPrak-Team)
	 * @param player - the player who won
	 * @param time - needed time for victory
	 * @author Schrader, Tobias, 5637252
	 */
	public void addPlayerScore(String player, int time){
		for(int i = 0; i < highScore.size(); i++){
			if(highScore.get(i).getTime() > time){
				highScore.add(i, new HighScoreElement(time, player));
				i = highScore.size();
			}
		}
		
		try {
			FileWriter writer = new FileWriter(new File("highscore.txt"));
			for(int i = 0; i < 10; i++){
				writer.write(highScore.get(i).getTime() + "\t" + highScore.get(i).getName() + "\n");
			}			

			writer.close();
			
		} catch (IOException e) {
			System.out.println("Error at writing Highscore");
		}
		
	}
	/**
	 * 
	 * @return Highscores as a LinkedList
	 * @author Schrader, Tobias, 5637252
	 */
	public LinkedList<HighScoreElement> getHighScore(){
		return highScore;
	}
}

