package pp2016.team19.server.map;
import pp2016.team19.server.map.*;

import java.util.Vector;


import java.util.*;

public class Tile extends Content{
		
		public int Type;
		public boolean ContainsKey = false;
		public boolean ContainsPotion = false;
		public boolean ContainsMonster = false;
		
		public static final int Rock = 1;
		public static final int Floor = 0;
		public static final int Entry = 2;
		public static final int Exit = 3;
		
		public static final int Key = 11;
		public static final int Potion = 12;
		public static final int Monster = 13;
		
		
		
		private Vector<Content> content = null;
		
	
		
		
		
		
		
		// Input = Rock, Floor, Door, 
		public void setType(int input){
			Type = input;
		}
		
		
		
		public boolean isRock(){
			
			if (Type == Rock){
				return true;
			}
			
			return false;
		}
		
		public boolean isFloor(){
			
			if (Type == Floor){
				return true;
			}
			
			return false;
		}
		
		public boolean isEntry(){
			
			if (Type == Entry){
				return true;
			}
			return false;
		}
		
		public boolean isExit(){
			
			if (Type == Exit){
				return true;
			}
			return false;
		}
		
		
		
		
		// Schlüssel Methoden:
		public void placeKeyT(){
			ContainsKey = true;
		}
		
		public void droppedKeyT(){
			ContainsKey = false;
		}
		
		public boolean containsKey(){
			if(ContainsKey == true){
				return true;
			}
			return false;
		}
		
		
		// Potion Methoden:
		public boolean containsPotion(){
			if(ContainsPotion == true){
				return true;
			}
			return false;
		}
		
		public void containsNoPotion(){
			ContainsPotion = false;
		}
		
		public void placePotionT(){
			ContainsPotion = true;
		}
		
		
		// Monster Methoden:
		public boolean containsMonster(){
			if(ContainsMonster == true){
				return true;
			}
			return false;
		}
		
		public void setContainsMonster(){
			ContainsMonster = true;
		}
		
		public void containsNoMonster(){
			ContainsMonster = false;
		}
		
		
		// Walkable Methoden lol

		public boolean isWalkable(){
			if(Type == Floor || Type == Entry || Type == Exit ){
				System.out.println("and is walkable");
				return true;
			}
			return false;
		}
		

	}
	
	
	

