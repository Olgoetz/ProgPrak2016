package pp2016.team19.shared;

public class HighScoreElement {
	private String name;
	private int time;
	
	public HighScoreElement(int punkte, String name){
		this.setName(name);
		this.setTime(punkte);
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
