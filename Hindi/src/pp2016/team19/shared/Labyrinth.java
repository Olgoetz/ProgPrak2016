package pp2016.team19.shared;

import java.io.Serializable;

/**
 * 	<h1>A class, that generates the random Labyrinth with Tile-Objects, and sets a choosen number of Monster randomly on the gameMap, also 
 * 	Potions, Exit, Entry and the Key.</h1>
 * 	<p>
 * 	It contains the floodfill algorithm, and also a lot of helpful other methods.
 * 	<p>
 * 	It has a fundamental method called "generate()", which generates the Labyrinth with the help of all methods.
 * 	<p>
 *
 * @author < Czernik, Christof Martin, 5830621 >
 */

public class Labyrinth implements Serializable {


	private static final long serialVersionUID = 5322398047502146239L;
	/**
	 * 7 Constants
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static final int ROCK = 1;
	public static final int FLOOR = 0;
	public static final int ENTRY = 2;
	public static final int EXIT = 3;

	public static final int KEY = 11;
	public static final int POTION = 12;
	public static final int MONSTER = 13;
	public static final int PLAYER = 14;
	
	/**
	 * 2-Dim Tile-Array called "gameMap"
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	private static Tile[][] gameMap;
	//private static int gameSize;
	//private static int monsterNumber;
	
	//public Labyrinth(int gameSize, int monsterNumber){
	//	this.gameSize = gameSize;
	//	this.monsterNumber = monsterNumber;
	//}
	
	
	/**
	 * Is an essential method, which gets the gameSize as Input and returns the gameMap.
	 * 
	 * @param gameSize Size of the GameMap, just a Size like 2k+1  (11,13,15,...).
	 * @param monsterNumber Amount of Monsters that will be created.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static Tile[][] generate(int gameSize, int monsterNumber) {

		gameMap = new Tile[gameSize][gameSize];

		// Creates a Map of ROCKS.
		restartGenerator(gameSize);
		

		// Starts to switch Rock-Tiles to Floor-Tiles, to make a maze.
		floodFill(1, gameSize - 2, gameSize);
		
		// Places an Entry.
		placeEntry(gameSize);

		// Places an Exit.
		placeExit(1, gameSize);

		// Places a Key
		placeKey(1, gameSize);

		// Places a Potion.
		placePotion(gameSize);

		// Places a number of Monster.
		placeMonster(gameSize,monsterNumber);

		return gameMap;
	}
	
	/**
	 * Creates an Object for every index, and sets the type to ROCK.
	 * 
	 * @param gameSize is the Size of the gameMap.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void createRockMap(int gameSize){
		
		for (int i = 0; i < gameSize; i++) {
			for (int j = 0; j < gameSize; j++) {
				gameMap[i][j] = new Tile();
				gameMap[i][j].setType(ROCK);

			}
		}
		
	}
	
	/**
	 * Sets the whole map back to Rock.
	 * 
	 * @param gameSize is the Size of the gameMap.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void restartGenerator(int gameSize){
		
		for (int i = 0; i < gameSize; i++) {
			for (int j = 0; j < gameSize; j++) {
				gameMap[i][j] = new Tile();
				gameMap[i][j].setType(ROCK);
				gameMap[i][j].setContainsPlayer(false);
				gameMap[i][j].setContainsPotion(false);
				gameMap[i][j].setContainsMonster(false);
				gameMap[i][j].setContainsKey(false);

			}
		}
		
	}
	
	/**
	 * A method, which helps to analyse the Labyrinth-Perfection.
	 * 
	 * @param gameSize is the Size of the gameMap.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static int mazePerfection(int gameSize){
		
		int counter = 0;
		
		for (int i = 0; i < gameSize; i++) {
			for (int j = 0; j < gameSize; j++) {
				if(gameMap[i][j].isFloor() || gameMap[i][j].isEntry() || gameMap[i][j].isExit()){
					counter = counter + 1;
				}
				
			}
		}
		return counter;
	}
	
	
	/**
	 * Checks if we are at the Upper-Edge, returns true when not.
	 * 
	 * @param x the x coordinate of Tile.
	 * @param y the y coordinate of Tile.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static boolean edgeCheckUp(int x, int y) {

		if (x - 2 == 0) {
			return false;
		}
		if (x - 2 > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if we are at the Right-Edge, returns true when not.
	 * 
	 * @param x the x coordinate of Tile.
	 * @param y the y coordiante of Tile.
	 * @param gameSize is the Size of the gameMap.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static boolean edgeCheckRight(int x, int y, int gameSize) {

		if (y + 2 == gameSize - 1) {
			return false;
		}
		if (y + 2 < gameSize - 1) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if we are at the Lower-Edge, returns true when not.
	 * 
	 * @param x the x coordinate of Tile.
	 * @param y the y coordiante of Tile.
	 * @param gameSize is the Size of the gameMap.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static boolean edgeCheckDown(int x, int y, int gameSize) {

		if (x + 2 == gameSize - 1) {
			return false;
		}
		if (x + 2 < gameSize - 1) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if we are at the Left-Edge, returns true when not.
	 * 
	 * @param x the x coordinate of Tile.
	 * @param y the y coordiante of Tile.
	 * @param gameSize is the Size of the gameMap.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static boolean edgeCheckLeft(int x, int y, int gameSize) {

		if (y - 2 == 0) {
			return false;
		}
		if (y - 2 > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Counts the Neighbors of a Tile in the directions: (left, right, up, down)
	 * 
	 * @param x the x coordinate of Tile.
	 * @param y the y coordiante of Tile.
	 * @param gameSize is the Size of the gameMap.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static int countNeighbors(int x, int y, int gameSize) {

		int counter = 0;

		if (gameMap[x + 1][y].isFloor()) {
			counter = counter + 1;
		}
		if (gameMap[x - 1][y].isFloor()) {
			counter = counter + 1;
		}
		if (gameMap[x][y + 1].isFloor()) {
			counter = counter + 1;
		}
		if (gameMap[x][y - 1].isFloor()) {
			counter = counter + 1;
		}

		return counter;
	}

	/**
	 * Checks if the diagonal Tile is connected with our current tile.
	 * 
	 * @param x the x coordinate of Tile.
	 * @param y the y coordiante of Tile.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static boolean diagNeighbors(int x, int y) {

		if (gameMap[x + 1][y + 1].isFloor()) {
			if (!gameMap[x + 1][y].isFloor() || !gameMap[x][y + 1].isFloor()) {
				return false;
			}
		}
		if (gameMap[x - 1][y + 1].isFloor()) {
			if (!gameMap[x - 1][y].isFloor() || !gameMap[x][y + 1].isFloor()) {
				return false;
			}
		}
		if (gameMap[x + 1][y - 1].isFloor()) {
			if (!gameMap[x + 1][y].isFloor() || !gameMap[x][y - 1].isFloor()) {
				return false;
			}
		}
		if (gameMap[x - 1][y + 1].isFloor()) {
			if (!gameMap[x - 1][y].isFloor() || !gameMap[x][y + 1].isFloor()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Searches for a Tile = Floor in a quadrant, to put a Potion at this Tile. (Recursiv)
	 * 
	 * @param gameSize is the Size of the gameMap.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void placePotion(int gameSize) {
		boolean br = false;
		for (int i = (gameSize - 2); i > ((gameSize - 2) / 2 + 1); i--) {
			if (gameMap[i][gameSize - 2].isFloor()) {

				gameMap[i][(gameSize - 2)].setContainsPotion(true);
				br = true;
				System.out.print("\nPotion placed randomly in a blind alley.");
				gameMap[i][(gameSize - 2)].isWalkable();
				break;

			}
		}
		if (gameSize < 2) {
			return;
		} else if (br == false) {
			placePotion((gameSize - 1));
		}

	}
	
	/**
	 * Searches for a random walkable Tile in a quadrant, to put a Monster at this Tile (Recursiv)
	 * 
	 * @param gameSize size of the gameMap
	 * @param monster number of all monsters
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void placeMonster(int gameSize, int monsterNumber){
		
		// create Vector, for possible monster Tiles
		int[] possibleMonsterPlaces = new int[gameSize*gameSize];
	
		// Counter-Variable
		int counter = 0;
		int x;
		int y;
		
		// put all Floor-Tiles indizes in an int Array
		for (int i = 1; i < gameSize; i++){
			for (int j = 1; j < gameSize; j++){
				if(gameMap[i][j].isFloor()){
					if(!gameMap[i][j].containsMonster() && !gameMap[i][j].containsPlayer()){
					possibleMonsterPlaces[counter] = i;
					possibleMonsterPlaces[counter + 1] = j;
					counter = counter + 2;
					}
				}	
			}
		}
		
		// For, to generate the number of Monster..
		for (int m = 1; m <= monsterNumber; m++){
		
			// Random number to get a random floor
			int number = (int) ((Math.random()) * counter + 1);
			
			if(number <= 1){
				number = number + 2;
			}
		
			// Gets the x and y Coordinate of gameMap
			if (number%2 == 0){
				x = possibleMonsterPlaces[number];
				y = possibleMonsterPlaces[number + 1];
			}
			else{
				y = possibleMonsterPlaces[number];
				x = possibleMonsterPlaces[number - 1];
			}
			
			if(gameMap[x][y].containsMonster()){
				m = m-1;
				continue;
			}
			// sets the Tile where the monster spawns True.
			gameMap[x][y].setContainsMonster(true);
		
		}
		
	}
	
	// NEU kommentieren!
	
	public static int monsterList_x(int gameSize, int monsterNumber, int monsterIndex){
		
		int [] x_cord = new int [monsterNumber];
		
		
		int counter = 0;
		
		for (int i = 0; i < gameSize; i++) {
			for (int j = 0; j < gameSize; j++) {
				if(gameMap[i][j].containsMonster()){
					x_cord[counter] = j;
					counter = counter + 1;
				}
				
			}
		}
		return (x_cord[monsterIndex]);
	}
	
	// NEU kommentieren!
	
	public static int monsterList_y(int gameSize, int monsterNumber, int monsterIndex){
		
		int [] y_cord = new int [monsterNumber];
		
		
		int counter = 0;
		
		for (int i = 0; i < gameSize; i++) {
			for (int j = 0; j < gameSize; j++) {
				if(gameMap[i][j].containsMonster()){
					y_cord[counter] = i;
					counter = counter + 1;
				}
				
			}
		}
		return (y_cord[monsterIndex]);
	}

	/**
	 * Searches for a blind alley in a quadrant, to put a Key at this blind alley. (Recursiv)
	 * 
	 * @param gameSize is the Size of the gameMap.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void placeKey(int i, int gameSize) {
		boolean br = false;
		for (int j = 1; j < gameSize; j++) {
			if (gameMap[i][j].isFloor()) {
				if(!gameMap[i+1][j].isExit() && !gameMap[i-1][j].isExit() && !gameMap[i][j+1].isExit() && !gameMap[i][j-1].isExit() && !gameMap[i+1][j].isEntry() && !gameMap[i-1][j].isEntry() && !gameMap[i][j+1].isEntry() && !gameMap[i][j-1].isEntry()){
					if (countNeighbors(i, j, gameSize) < 2) {
						if(!gameMap[i][j].isEntry() && !gameMap[i][j].isExit() && !gameMap[i][j].containsPotion() && !gameMap[i][j].containsMonster()){
						
							gameMap[i][j].setContainsKey(true);
							br = true;
							System.out.print("\nKey placed randomly in a blind alley.");
							gameMap[i][j].isWalkable();
							break;
						}
					}
				}
			}
		}

		if (br == false) {
			placeKey(i + 1, gameSize);
		}
	}

	/**
	 * Searches for a blind alley in the lower-Left quadrant, to put an exit at this blind alley. (Recursiv)
	 * 
	 * @param gameSize is the Size of the gameMap.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void placeExit(int j, int gameSize) {
		boolean br = false;
		
		for (int i = 1; i < gameSize; i++) {
			if (gameMap[i][j].isFloor()) {
				if (countNeighbors(i, j, gameSize) < 2) {
					if(!gameMap[i][j].containsKey() && !gameMap[i][j].containsMonster() && !gameMap[i][j].containsPlayer() && !gameMap[i][j].containsPotion() && !gameMap[i][j].isExit() && !gameMap[i][j].isEntry()){
							
						gameMap[i][j].setType(EXIT);
						gameMap[i][j].setExitUnlocked(false);
						br = true;
						break;
						
					}
				}
			}
		}

		if (br == false) {
			if (j < gameSize/2){
				placeExit(j+1, gameSize);
			}
			
			else{
				for (int i = (gameSize - 2); i > (gameSize/2 + 2); --i) {
					if (gameMap[i][j].isFloor()) {
						if (countNeighbors(i, j, gameSize) < 3) {
							gameMap[i][j].setType(EXIT);
							gameMap[i][j].setExitUnlocked(false);
							br = true;
							break;
							

						}
					}
				}
			}
		}
			
		
	}

	/**
	 * Places an entry at the beginning Spot of floodFill.
	 * 
	 * @param gameSize is the Size of the gameMap.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void placeEntry(int gameSize) {
		gameMap[1][gameSize - 2].setType(ENTRY);
		gameMap[1][gameSize - 2].setContainsPlayer(true);
	}
	
	/**
	 * Changes the Exit from locked to unlocked.
	 * 
	 * @param gameSize is the Size of the gameMap.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public void exitOpen(int gameSize) {
		for (int i = 0; i < gameSize; i++) {
			for (int j = 0; j < gameSize; j++) {
				if(gameMap[i][j].isExit() && !gameMap[i][j].exitUnlocked()){
					gameMap[i][j].setExitUnlocked(true);
				}

			}
		}
	}

	/**
	 * A Random FloodFill Algorithm, which generates a Way of Floors.
	 * 
	 * @param x the x coordinate of Tile.
	 * @param y the y coordinate of Tile.
	 * @param gameSize is the Size of the gameMap.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void floodFill(int x, int y, int gameSize) {

		int zahl = (int) ((Math.random()) * 4 + 1);

		int counter = 0;
		
		if(gameMap[x][y].isRock()){
			gameMap[x][y].setType(FLOOR);
		}
		
		switch (zahl) {

		case 1:
			if (edgeCheckUp(x, y)) {
				if (gameMap[x - 2][y].isRock()) {
					if (countNeighbors(x - 2, y, gameSize) < 2) {
						// if(diagNeighbours(x-1,y)){
						gameMap[x-1][y].setType(FLOOR);
						gameMap[x-2][y].setType(FLOOR);
						floodFill(x - 2, y, gameSize);
						// }

					}
				}
			} else {
				zahl = 2;
			}

		case 2:
			if (edgeCheckRight(x, y, gameSize)) {
				if (gameMap[x][y + 2].isRock()) {
					if (countNeighbors(x, y + 2, gameSize) < 2) {
						// if(diagNeighbours(x,y+1)){
						gameMap[x][y+1].setType(FLOOR);
						gameMap[x][y+2].setType(FLOOR);
						floodFill(x, y + 2, gameSize);
						// }
					}
				}
			} else {
				zahl = 3;
			}

		case 3:
			if (edgeCheckDown(x, y, gameSize)) {
				if (gameMap[x + 2][y].isRock()) {
					if (countNeighbors(x + 2, y, gameSize) < 2) {
						// if(diagNeighbours(x+1, y)){
						gameMap[x+1][y].setType(FLOOR);
						gameMap[x+2][y].setType(FLOOR);
						floodFill(x + 2, y, gameSize);
						// }
					}
				}
			} else{
				zahl = 4;
			}

		case 4:
			if (edgeCheckLeft(x, y, gameSize)) {
				if (gameMap[x][y - 2].isRock()) {
					if (countNeighbors(x, y - 2, gameSize) < 2) {
						// if(diagNeighbours(x,y-1)){
						gameMap[x][y-1].setType(FLOOR);
						gameMap[x][y-2].setType(FLOOR);
						floodFill(x, y - 2, gameSize);
						// }
					}
				}
			} else{
				zahl = 5;
			}

			
		case 5:
			if (edgeCheckUp(x, y)) {
				if (gameMap[x - 2][y].isRock()) {
					if (countNeighbors(x - 2, y, gameSize) < 2) {
						// if(diagNeighbours(x-1,y)){
						gameMap[x-1][y].setType(FLOOR);
						gameMap[x-2][y].setType(FLOOR);
						floodFill(x - 2, y, gameSize);
						// }

					}
				}
			} else {
				zahl = 6;
			}
			
		case 6:
			if (edgeCheckRight(x, y, gameSize)) {
				if (gameMap[x][y + 2].isRock()) {
					if (countNeighbors(x, y + 2, gameSize) < 2) {
						// if(diagNeighbours(x,y+1)){
						gameMap[x][y+1].setType(FLOOR);
						gameMap[x][y+2].setType(FLOOR);
						floodFill(x, y + 2, gameSize);
						// }
					}
				}
			} else {
				zahl = 7;
			}
			
		case 7:
			if (edgeCheckDown(x, y, gameSize)) {
				if (gameMap[x + 2][y].isRock()) {
					if (countNeighbors(x + 2, y, gameSize) < 2) {
						// if(diagNeighbours(x+1, y)){
						gameMap[x+1][y].setType(FLOOR);
						gameMap[x+2][y].setType(FLOOR);
						floodFill(x + 2, y, gameSize);
						// }
					}
				}
			} else{
				zahl = 8;
			}

		case 8:
			if (edgeCheckLeft(x, y, gameSize)) {
				if (gameMap[x][y - 2].isRock()) {
					if (countNeighbors(x, y - 2, gameSize) < 2) {
						// if(diagNeighbours(x,y-1)){
						gameMap[x][y-1].setType(FLOOR);
						gameMap[x][y-2].setType(FLOOR);
						floodFill(x, y - 2, gameSize);
						// }
					}
				}
			} else{
				zahl = 1;
			}

		}
	}

	/**
	 * Checks every Tile in the Array, and prints it on the console.
	 * 
	 * @param gameSize size of the gameMap.
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void PaintTest(int gameSize, int monsterNumber) {
		System.out.println("\n");
		for (int i = 0; i < gameSize; i++) {
			for (int j = 0; j < gameSize; j++) {

				if (gameMap[i][j].isEntry()) {
					System.out.print("S ");

				} else if (gameMap[i][j].isExit()) {
					System.out.print("Z ");

				} else if (gameMap[i][j].containsKey()) {
					System.out.print("K ");

				} else if (gameMap[i][j].containsPotion()) {
					System.out.print("P ");
					
				} else if (gameMap[i][j].containsMonster()) {
					System.out.print("M ");

				} else if (gameMap[i][j].isRock()) {
					System.out.print("+ ");

				} else if (gameMap[i][j].isFloor()) {
					System.out.print("o ");

				}
			}
			System.out.println("");
		}

	}
}
