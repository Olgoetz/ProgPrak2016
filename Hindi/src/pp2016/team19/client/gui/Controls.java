package pp2016.team19.client.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * 
 * @author Felizia Langsdorf, Matr_Nr: 6002960
 *
 */


// panel for explanation of the control, is showing after clicking on MenuItem "show control"

public class Controls extends JPanel {

	private static final long serialVersionUID = 1L;

	public void paint(Graphics g){
		Image img = null , control = null, arrows = null , right = null, walker = null, q = null , fire = null, leer = null, mouse = null, b = null;
		
		// loading the images
		try{
			img = ImageIO.read(new File("img//controller.png"));
			control = ImageIO.read(new File("img//Data-Control.png"));
			arrows = ImageIO.read(new File ("img//arrows.png"));
			right = ImageIO.read(new File ("img//right-arrow.png"));
			walker = ImageIO.read(new File ("img//walking.png"));
			q = ImageIO.read(new File ("img//Q_key.png"));
			b= ImageIO.read(new File("img//b-button.png"));
			fire = ImageIO.read(new File ("img//fire.png"));
			leer = ImageIO.read(new File ("img//Leertaste.png"));
			mouse = ImageIO.read(new File ("img//mouse.png"));
		}catch(IOException e){ }
		
		// filling the panel with the content, drawing on it, the images, strings, etc.
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.getWidth() , this.getHeight());
			
		g.drawImage(img, 200, 10, null);
		g.drawImage(control, 205, 15, null);
		g.setColor(Color.WHITE);
		g.drawLine(10, 120, 500, 120);
		g.fillRect(0, 120, this.getWidth(), this.getHeight());
		
		g.drawImage(arrows , 60, 120, null);
		g.drawImage(right, 180 , 140, null);
		g.drawImage(walker, 300 , 130, null);
		g.setColor(Color.BLACK);
		g.drawString("move", 400 , 150);
		
		g.drawImage(mouse, 40 , 220, null);
		g.drawImage(right, 180, 230, null);
		g.drawImage(walker, 300, 220, null);
		g.drawString("move", 400, 240);
		
		g.drawImage(q, 60, 320, null);
		g.drawImage(right, 180, 330, null);
		g.drawImage(fire, 300, 325, null);
		g.drawString("attack monster",400 ,350);
		
		g.drawImage(leer, 60, 410, null);
		g.drawImage(right, 180 , 410 , null);
		g.drawString("pick up item", 300, 425);
		
		g.drawImage(b,60, 460, null);
		g.drawImage(right, 180, 470, null);
		g.drawString("take the potion", 300, 500);
		
	}
	
}
