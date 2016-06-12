package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Controls extends JPanel {

	private static final long serialVersionUID = 1L;

	
	
	public void paint(Graphics g){
		Image img = null, floor = null;
				
		try{
			img = ImageIO.read(new File("img//controls.png"));
			floor = ImageIO.read(new File("img//status.png"));
		}catch(IOException e){ }
		
		for(int i = 0; i < 16; i++){
			for(int j = 0; j < 17; j++){
				g.drawImage(floor, 32*i,32*j,null);
			}
		}
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		
	}
	
}
