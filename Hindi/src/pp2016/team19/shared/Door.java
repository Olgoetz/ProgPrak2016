package pp2016.team19.shared;

public class Door extends GameObject {
	private boolean open;
	
	public Door(boolean open){
		this.open = open;
	}
	
	public void setOpen(){
		open = true;
	}
	
	public void setClosed(){
		open = false;
	}
	
	public boolean isOpen(){
		return open;
	}
}
