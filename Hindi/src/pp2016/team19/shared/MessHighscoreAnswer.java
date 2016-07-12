package pp2016.team19.shared;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * <h1>The message object, that receives linked list from the server.</h1>
 * It contains the usernames and the highscores.
 * 
 * @author Oliver Goetz, 5961343
 *
 */
public class MessHighscoreAnswer extends Message implements Serializable {
	
	
	/**
	 * The attributes for the class.
	 * 
	 * @author Oliver Goetz, 5961343
	 */
	
	private static final long serialVersionUID = -6374026871196891954L;
	
	// The linked list containing usernames and highscores.
	private LinkedList<HighScoreElement> highscore;
	
	
	/**
	 * Constructor for the class.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param highscore the highscore list
	 * @param type the maintype (=2)
	 * @param subType the subtype (=9)
	 */
	
	public MessHighscoreAnswer(LinkedList<HighScoreElement> highscore, int type, int subType) {
		super(type, subType);
		this.highscore = highscore;
		
	}
	
	/**
	 * Sets the linked list.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param highscore the highscore list
	 */
	public void setHighscore(LinkedList<HighScoreElement> highscore) {
	this.highscore = highscore;
}
	
	/**
	 * Gets the linked list.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @return the highscore linked list
	 */
	
	public LinkedList<HighScoreElement> getHighscore() {
		return highscore;
	}
	
}