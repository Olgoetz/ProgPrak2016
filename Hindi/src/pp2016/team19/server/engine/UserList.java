package pp2016.team19.server.engine;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import pp2016.team19.shared.Player;

public class UserList {
	ServerEngine engine;
	ArrayList<Player> userList;
	
	public UserList(ServerEngine engine) {
		this.engine = engine;
		userList = new ArrayList<Player>();
	}
	public ArrayList<Player> readUserList() {
		userList.clear();
		try {
			FileReader reader = new FileReader(new File("userlist.txt"));
			int c;
			String line = "";
			
			
			while((c = reader.read()) != -1){				
				
				if(c == '\n'){
					String[] temp = line.split("\t");
					userList.add(new Player(temp[0], temp[1]));					
					line = "";
				}else{
					line += (char) c;
				}
			}
			
			reader.close();
			
		} catch (IOException e) {
			System.out.println("Error at reading userList");
		}
		return userList;
	}
	public void addPlayerToList(Player player) {
		//userList.add(player);
		System.out.println("UserList: #player: "+userList.size());
		try {
			FileWriter writer = new FileWriter(new File("userlist.txt"));
			for(int i = 0; i < userList.size(); i++){
				writer.write(userList.get(i).getName() + "\t" + userList.get(i).getPassword() + "\n");
			}			

			writer.close();
			
		} catch (IOException e) {
			System.out.println("Error at writing in userList");
		}
	}
}
