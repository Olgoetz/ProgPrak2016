package pp2016.team19.shared;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * The message object, that receives linked list from the server.
 * It contains the usernames and the highscores.
 * 
 * @author Oliver
 *
 */
public class MessHighscoreAnswer extends Message implements Serializable {
	
	
	/**
	 * The linked list containing usernames and highscores.
	 * 
	 * @author Oliver Goetz, 5961343
	 */
	
	private static final long serialVersionUID = -6374026871196891954L;
	private LinkedList<HighScoreElement> highscore;
	
	
	/**
	 * Constructor for the class.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @param highscore the highscore list
	 * @param type the maintype
	 * @param subType the subtype
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