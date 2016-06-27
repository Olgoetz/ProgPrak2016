package pp2016.team19.client.gui;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import pp2016.team19.shared.Door;
import pp2016.team19.shared.Floor;
import pp2016.team19.shared.GameObject;
import pp2016.team19.shared.Key;
import pp2016.team19.shared.Monster;
import pp2016.team19.shared.Wall;


// this class was provided
//public class Reader {
//
//	private String filename;
//	private GameObject[][] map;
//	
//	private GameWindow window;
//	
//	public Reader(String fileName, GameWindow window){
//		this.window = window;
//		this.filename = fileName;
//		
//		readLevel();
//	}
//	
//	private void readLevel(){
//		try {
//			FileReader reader = new FileReader(filename);
//		
//			int i = 0, j = 0, c;
//			
//			map = new GameObject[window.WIDTH][window.HEIGHT];
//			window.monsterList = new LinkedList<Monster>();		
//			
//			while((c = reader.read()) != -1){	
//				if(c == '\n'){
//					i=0;
//					j++;
//				}else{
//					if(c >= '0' && c <= '9'){
//						
//						switch(c){
//							case '0': map[i][j] = new Wall(); break;
//							case '1': map[i][j] = new Floor(); break;
//							case '2': map[i][j] = new Key(); break;
//							case '3': map[i][j] = new Door(false); break;
//							case '4': map[i][j] = new Door(true); window.player.setPos(i, j); break;
//							case '5': map[i][j] = new Floor(); window.monsterList.add(new Monster(i,j, window, 0)); break;
//							// Monster which spawns after taking the key
//							case '6': map[i][j] = new Floor(); window.monsterList.add(new Monster(i,j, window, 1)); break;
//						}	
//						i++;
//					}
//					
//				}
//				
//					
//			}
//			
//			reader.close();
//			
//		} catch (IOException e) {
//			System.err.println(e);
//		}
//	}
//	
//	public GameObject[][] getLevel(){
//		return map;
//	}
//}
