package pp2016.team19.server.map;

import pp2016.team19.shared.Labyrinth;

/**
 * A test class, for the "komponententest" of the Levelgenerator.
 * 
 * @author < Czernik, Christof, 5830621 >
 */

public class Levelgenerator_Test{
	
	// IF einbauen, die ANZAHL FLOOR STEINE zählt, und im Notfall FLOODFILL neu startet!
	
	public static void main(String[] args){
		
		// generates a labyrinth of the size 19 x 19 with 2 randomly placed monsters in it.
		// Legend:	Wall := "+"     Floor := "o"     Monster := "M"     Key := "K"     Potion := "P"     Exit := "Z"     Entry/Player := "S"		
		Labyrinth.generate(19,2);
		
		// Prints the labyrinth on the console, to check if the method works well.
		Labyrinth.PaintTest(19);
	
	}

}
