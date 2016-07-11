package pp2016.team19.server.engine;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import pp2016.team19.shared.HighScoreElement;


public class Highscore {
private LinkedList<HighScoreElement> highScore;
	
	
	
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
			System.out.println("Error at reading Highscore");
		}
		while(highScore.size() < 10){
			highScore.add(new HighScoreElement(9999, "Anonymous"));	
		}
	}
	
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
	
	public LinkedList<HighScoreElement> getHighScore(){
		return highScore;
	}
}

