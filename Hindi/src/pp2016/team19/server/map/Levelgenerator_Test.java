package pp2016.team19.server.map;



public class Levelgenerator_Test{
	
	
	public static void main(String[] args){
		
		// generates a labyrinth of the size 16x16 with 3 randomly placed monsters in it.
		// Legend:	Wall := "+"     Floor := "o"     Monster := "M"     Key := "K"     Potion := "P"     Exit := "Z"     Entry/Player := "S"		
		Labyrinth.generate(16,3);
		
		// Prints the labyrinth on the console, to check if the method works well.
		Labyrinth.PaintTest(16);
	
	}

}
