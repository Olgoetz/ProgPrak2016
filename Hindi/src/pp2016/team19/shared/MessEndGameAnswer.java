package pp2016.team19.shared;

import java.io.Serializable;

public class MessEndGameAnswer extends Message implements Serializable {
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
