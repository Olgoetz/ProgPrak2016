package pp2016.team19.client.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Controls extends JPanel {

	private static final long serialVersionUID = 1L;

	public void paint(Graphics g){
		Image img = null , control = null, arrows = null , right = null, walker = null, q = null , fire = null, leer = null, mouse = null;
				
		try{
			img = ImageIO.read(new File("img//controller.png"));
			control = ImageIO.read(new File("img//Data-Control.png"));
			arrows = ImageIO.read(new File ("img//arrows.png"));
			right = ImageIO.read(new File ("img//right-arrow.png"));
			walker = ImageIO.read(new File ("img//walking.png"));
			q = ImageIO.read(new File ("img//Q_key.png"));
			fire = ImageIO.read(new File ("img//fire.png"));
			leer = ImageIO.read(new File ("img//Leertaste.png"));
			mouse = ImageIO.read(new File ("img//mouse.png"));
		}catch(IOException e){ }
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.getWidth() , this.getHeight());
		
		g.drawImage(img, 200, 10, null);
		g.drawImage(control, 205, 15, null);
		g.setColor(Color.WHITE);
		g.drawLine(10, 120, 500, 120);
		g.drawImage(arrows , 60, 150, null);
		g.drawImage(right, 180 , 160, null);
		g.setColor(Color.WHITE);
		g.drawImage(walker, 300 , 150, null);
		g.drawString("move", 400 , 180);
		g.drawImage(q, 60, 250, null);
		g.drawImage(right, 180, 260, null);
		g.drawImage(fire, 310, 260, null);
		g.drawString("attack monster",400 ,285);
		g.drawImage(leer, 60, 360, null);
		g.drawImage(right, 180 , 355 , null);
		g.drawString("pick up item", 310, 375);
		g.drawImage(mouse, 40 , 450, null);
		g.drawImage(right, 180, 460, null);
		g.drawImage(walker, 300, 450, null);
		g.drawString("move", 400, 480);
		
		
		
		//for(int i = 0; i < 16; i++){
			//for(int j = 0; j < 17; j++){
				
				//g.drawImage(floor, 32*i,32*j,null);
			//}
		//}
		
	}
	
}
