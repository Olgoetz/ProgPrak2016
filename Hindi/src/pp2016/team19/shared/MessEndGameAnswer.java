package pp2016.team19.shared;

import java.io.Serializable;
/**
 * Sends if game is won or lost and score
 * 
 * @author Schrader, Tobias, 5637252
 */
public class MessEndGameAnswer extends Message implements Serializable {
	private static final long serialVersionUID = -2656858660899131260L;
	private boolean gameWon;
	private int score;
	public MessEndGameAnswer(boolean gameWon, int score, int type, int subType) {
		super(type, subType);
		this.setGameWon(gameWon);
		this.setScore(score);
	}
	/**
	 * 
	 * @return if the player won
	 * @author Schrader, Tobias, 5637252
	 */
	public boolean isGameWon() {
		return gameWon;
	}
	/**
	 * Set if won or lost
	 * @param gameWon a boolean
	 * @author Schrader, Tobias, 5637252
	 */
	public void setGameWon(boolean gameWon) {
		this.gameWon = gameWon;
	}
	/**
	 * 
	 * @return score achieved in the game
	 * @author Schrader, Tobias, 5637252
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Set the score
	 * @param score the score
	 * @author Schrader, Tobias, 5637252
	 */
	public void setScore(int score) {
		this.score = score;
	}

}
