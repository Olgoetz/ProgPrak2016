package pp2016.team19.server.map;



public class Labyrinth {
	
	// Tiles mit ihrem Zahlenwerten
		public static final int Rock = 1;
		public static final int Floor = 0;
		public static final int Entry = 2;
		public static final int Exit = 3;
		
		public static final int Key = 11;
		public static final int Potion = 12;
		public static final int Monster = 13;
		


		
		// 2 Dim. Array  vom DatenTyp Tile namens gameMap lol
		private static Tile[][] gameMap;
		
		

		// Konstruktor.. 
		public Labyrinth() {

		}

		// Methode generateLabyrinth
		public static Tile[][] generateLabyrinth(int gameSize) {

			
			
			gameMap = new Tile[gameSize][gameSize];

			// Erstellt ein gameMap Objekt vom DatenTyp Tile; 	Setzt jedes auf Typ Rock
			for (int i = 0; i < gameSize; i++) {
				for (int j = 0; j < gameSize; j++) {
					gameMap[i][j] = new Tile();
					gameMap[i][j].setType(Rock);

				}
			}

			// Wichtig! dritte Variable gameSize muss stimmen!
			floodFill(1, gameSize-2, gameSize);
			
			// Eingang wird platziert.(gameSize) (wo floodfill startet)
			placeEntry(gameSize);
			
			// Ausgang wird platziert (Startindex, gameSize)	(im unteren linken qudranten)
			placeExit(1,gameSize);
			
			// 
			placeKey(1,gameSize);
			
			//
			placePotion(gameSize);
			
			//
			//placeMonster();
			
			
			
				return gameMap;
		}
		


		// Prüft ob am oberen Rand:
		public static boolean edgeCheckUp(int x, int y, int GameSize) {

			if (x - 1 == 0) {
				return false;
			}
			if (x - 1 > 1) {
				return true;
			}
			return false;
		}

		// Prüft ob am rechten Rand:
		public static boolean edgeCheckRight(int x, int y, int gameSize) {

			if (y + 1 == gameSize - 1) {
				return false;
			}
			if (y + 1 < gameSize - 1) {
				return true;
			}
			return false;
		}

		// Prüft ob am unteren Rand:
		public static boolean edgeCheckDown(int x, int y, int gameSize) {

			if (x + 1 == gameSize - 1) {
				return false;
			}
			if (x + 1 < gameSize - 1) {
				return true;
			}
			return false;
		}

		// Prüft ob am linken Rand:
		public static boolean edgeCheckLeft(int x, int y, int gameSize) {

			if (y - 1 == 0) {
				return false;
			}
			if (y - 1 > 0) {
				return true;
			}
			return false;
		}

		// Gibt anzahl Nachbarn eines Punktes aus, die vom Typ Floor sind. (Links, Rechts, Oben, Unten)
		public static int countNeighbours(int x, int y) {

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

		// Gibt Anzahl Nachbarn eines Punktes aus, die vom Typ Floor sind. (Obenlinks, Obenrechts, Untenlinks, Untenrechts)
		public static int diagNeighbours(int x, int y) {

			int counter = 0;

			if (gameMap[x + 1][y + 1].isFloor()) {
				counter = counter + 1;
			}
			if (gameMap[x - 1][y + 1].isFloor()) {
				counter = counter + 1;
			}
			if (gameMap[x + 1][y - 1].isFloor()) {
				counter = counter + 1;
			}
			if (gameMap[x - 1][y + 1].isFloor()) {
				counter = counter + 1;
			}
			return counter;
		}
		
		// gibt maximale Anzahl Tiles aus:
		public static int wholeNeighbours(int x, int y){
			return (diagNeighbours(x,y) + countNeighbours(x,y));
		}
		
		// Platziert einen Heiltrank im unteren rechten Viertel:
		public static void placePotion(int gameSize){
			boolean br = false;
			for (int i = (gameSize-2); i > ((gameSize-2)/2 + 1); i--){
				if (gameMap[i][gameSize-2].isFloor()){
					
						gameMap[i][(gameSize-2)].placePotionT();
						br = true;
						System.out.print("\nPotion placed...");
						gameMap[i][(gameSize-2)].isWalkable();
						break;
						
					
				}//if isFloor
			}//for Innen
			if(gameSize < 2){
				return;
			}
			else if (br == false){
				placePotion((gameSize-1));
			}
			
		}
		
		
		// Platziert den Schlüssel im oberen linken Viertel
		public static void placeKey(int j, int gameSize){
			boolean br = false;
			for (int i = 1; i < (gameSize/2 + 1); i++){
				if (gameMap[i][j].isFloor()){
					if(countNeighbours(i, j)< 2){
						gameMap[i][j].placeKeyT();
						br = true;
						System.out.print("\nKey placed...");
						gameMap[i][j].isWalkable();
						break;
						
					}//if countN
				}//if isFloor
			}//for Innen
			
			if (br == false){
				placeKey(j+1, gameSize);
			}
		}
		
		// Platziere mögliche Tiles für Ausgang. (Unterer, linker Quadrant)
		public static void placeExit(int j, int gameSize){
			boolean br = false;
				for (int i = (gameSize-2); i > (gameSize/2 + 1); --i){
					if (gameMap[i][j].isFloor()){
						if(countNeighbours(i, j)< 2){
							gameMap[i][j].setType(Exit);
							br = true;
							System.out.println("\nExit placed in lower left Corner\n");
							break;
							
						}//if countN
					}//if isFloor
				}//for Innen
				
				if (br == false){
					placeExit(j+1, gameSize);
				}
		}// Methoden Ende
		

		// Methode zum platzieren der Türen:
		public static void placeEntry(int gameSize) {
			gameMap[1][gameSize - 2].setType(2);
			System.out.println("Entry placed in upper right Corner");
		}

		// floodFill der Random ein Labyrinth erstellt.
		public static void floodFill(int x, int y, int gameSize) {

			// Zufallszahl:

			int zahl = (int) ((Math.random()) * 4 + 1);

			int counter = 0;

			gameMap[x][y].setType(Floor);

			switch (zahl) {

			case 1:
				if (edgeCheckUp(x, y, gameSize)) {
					if (gameMap[x - 1][y].isRock()) {
						if (countNeighbours(x - 1, y) < 2) {
							 //if(diagNeighbours(x-1,y) < 3){
							floodFill(x - 1, y, gameSize);
							 //}

						}
					}
				} else if (counter < 4) {
					counter = counter + 1;
					zahl = 2;
				} else if (counter == 4) {
					break;
				}

			case 2:
				if (edgeCheckRight(x, y, gameSize)) {
					if (gameMap[x][y + 1].isRock()) {
						if (countNeighbours(x, y + 1) < 2) {
							 //if(diagNeighbours(x,y+1) < 3){
							floodFill(x, y + 1, gameSize);
							 //}
						}
					}
				} else if (counter < 4) {
					counter = counter + 1;
					zahl = 3;
				} else if (counter == 4) {
					break;
				}

			case 3:
				if (edgeCheckDown(x, y, gameSize)) {
					if (gameMap[x + 1][y].isRock()) {
						if (countNeighbours(x + 1, y) < 2) {
							 //if(diagNeighbours(x+1,y) < 3){
							floodFill(x + 1, y, gameSize);
							 // }
						}
					}
				} else if (counter < 4) {
					counter = counter + 1;
					zahl = 4;
				} else if (counter == 4) {
					break;
				}

			case 4:
				if (edgeCheckLeft(x, y, gameSize)) {
					if (gameMap[x][y - 1].isRock()) {
						if (countNeighbours(x, y - 1) < 2) {
							 //if(diagNeighbours(x,y-1) < 3){
							floodFill(x, y - 1, gameSize);
							 //}
						}
					}
				} else if (counter < 4) {
					counter = counter + 1;
					zahl = 1;
				} else if (counter == 4) {
					break;
				}

			}
		}

		// Test, wie das Labyrinth aussehen würde:.
		public static void PaintTest(int gameSize) {
			System.out.println("");
			for (int i = 0; i < gameSize; i++) {
				for (int j = 0; j < gameSize; j++) {

					if (gameMap[i][j].isEntry()) {
						System.out.print("S ");
						
					} else if (gameMap[i][j].isExit()) {
						System.out.print("Z ");
						
					} else if (gameMap[i][j].containsKey())	{
						System.out.print("K ");
						
					} else if (gameMap[i][j].containsPotion()) {
						System.out.print("P ");
						
					} else if (gameMap[i][j].isRock()) {
						System.out.print("+ ");
						
					} else if (gameMap[i][j].isFloor()) {
						System.out.print("o ");
						
					}

				}
				System.out.println("");
			}

		}

		public static void main(String[] args) {

			System.out.println("Test\n");

			generateLabyrinth(31);
			PaintTest(31);

			// Setze Manuell Start und Ziel

		}

	}


