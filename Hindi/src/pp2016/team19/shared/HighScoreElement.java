package pp2016.team19.shared;

import java.io.Serializable;

public class HighScoreElement implements Serializable {
	/**
	 * Saves name and score for a highscore entry
	 * @author Tobias Schrader 5637252
	 * @author ProgPrak-Team
	 */
	private static final long serialVersionUID = 5760526936235804401L;
	private String name;
	private int time;
	
	/**
	 * Constructor initializes the entry
	 * @param score - The score of the player
	 * @param name - The name of the player
	 * @author Tobias Schrader, 5637252
	 */
	public HighScoreElement(int score, String name){
		this.setName(name);
		this.setTime(score);
	}

	/**
	 * 
	 * @return score for this entry
	 * @author Tobias Schrader, 5637252
	 */
	public int getTime() {
		return time;
	}
/**
 * 
 * @param time - set score for this entry
 * @author Tobias Schrader, 5637252
 */
	public void setTime(int time) {
		this.time = time;
	}
/**
 * 
 * @return playername for this entry
 * @author Tobias Schrader, 5637252
 */
	public String getName() {
		return name;
	}
/**
 * 
 * @param name - set the playername for this entry
 * @author Tobias Schrader, 5637252
 */
	public void setName(String name) {
		this.name = name;
	}
}
