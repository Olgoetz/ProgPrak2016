package pp2016.team19.server.map;
import pp2016.team19.server.map.*;

import java.util.Vector;


import java.util.*;

import java.util.Vector;


import java.util.*;

public class Tile{
		
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
		public void setContainsKey(boolean input){
			ContainsKey = input;
		}
		
		public boolean containsKey(){
			return ContainsKey;
		}
		
		
		// Potion Methoden:
		public boolean containsPotion(){
			return ContainsPotion;
		}
		
		public void setContainsPotion(boolean input){
			ContainsPotion = input;
		}
		
		
		
		
		// Monster Methoden:
		public boolean containsMonster(){
			
			return ContainsMonster;
		}
		
		public void setContainsMonster(boolean input){
			ContainsMonster = input;
		}
		
		
		
		// Walkable Methoden

		public boolean isWalkable(){
			if(Type == Floor || Type == Entry || Type == Exit ){
				System.out.println("and is walkable");
				return true;
			}
			return false;
		}
		

	}
	
	
	


