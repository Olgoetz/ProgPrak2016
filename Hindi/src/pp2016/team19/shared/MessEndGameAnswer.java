package pp2016.team19.shared;

import java.io.Serializable;
/**
 * Sends if game is won or lost and score
 * 
 * @author Tobias Schrader, 5637252
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
	public boolean isGameWon() {
		return gameWon;
	}
	public void setGameWon(boolean gameWon) {
		this.gameWon = gameWon;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

}
