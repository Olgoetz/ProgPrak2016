package pp2016.team19.server.map;

public class Content {
	
	public int Type;
	public static final int Key = 11;
	public static final int Potion = 12;
	public static final int Monster = 13;
	
	
		

	
	public void setType(int input){
		Type = input;
	}
	
	public boolean isKey(){
		if(Type == Key){
			return true;
		}
		return false;
	}
	
	public boolean isPotion(){
		if(Type == Potion){
			return true;
		}
		return false;
	}
	
	public boolean isMonster(){
		if(Type == Monster){
			return true;
		}
		return false;
	}


}
