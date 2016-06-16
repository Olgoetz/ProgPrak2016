package pp2016.team19.shared;

public class Potion extends GameObject {
	private int effect;
	
	public Potion(int effect){
		this.effect = effect;
	}
	
	public int getEffect(){
		return effect;
	}
}
