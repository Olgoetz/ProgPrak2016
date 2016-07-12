package pp2016.team19.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/** 
 * <h1> class for the panel which explains how to control the game </h1>
 * shows up after clicking on the MenuItem "show control"
 * @author Langsdorf, Felizia, 6002960
 * 
 */

public class Controls extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * paint method, draws the pictures and strings on the panel
	 * @author Langsdorf, Felizia, 6002960
	 * @param g graphics object 
	 */
	
	public void paint(Graphics g){
		
		Image arrows = null , right = null,q = null,leer = null, mouse = null, b = null;
		
		// loading the images
		try{
			arrows = ImageIO.read(new File ("img//arrows.png"));//source: http://www.schieb.de/wp-content/uploads/2014/09/tastatur-pfeiltasten.jpg
			right = ImageIO.read(new File ("img//right-arrow.png"));//source: http://icon-icons.com/icons2/38/PNG/512/uparrow_arriba_5806.png
			q = ImageIO.read(new File ("img//Q_key.png"));//source: http://vignette1.wikia.nocookie.net/devilmaycry/images/1/13/PC_Q_key.png/revision/latest?cb=20130406111054
			b= ImageIO.read(new File("img//b-button.png"));//source: https://somkcr.files.wordpress.com/2012/11/keyboard-b-button.jpg
			leer = ImageIO.read(new File ("img//spacekey.png"));//source: http://www.absatzsetzer.de/wp-content/uploads/2011/06/Erscheinungsbild-im-Bedienfeld-der-Optionsfeld-EigenschaftenLeertaste.png
			mouse = ImageIO.read(new File ("img//mouse.png"));//source: http://cliparts.co/cliparts/8iG/ErE/8iGErEbaT.png
		}catch(IOException e){
			System.err.println("Error while loading one of the images.");
		}
		
		// filling the panel with the content, drawing on it, the images, strings, etc.
		//first paint the whole panel gray
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.getWidth() , this.getHeight());
		//the heading "CONTROL"	
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.BOLD, 25));
		g.drawString("CONTROL", 200, 50);
		
		g.drawLine(10, 100, 500, 120);
		g.fillRect(0, 100, this.getWidth(), this.getHeight());
		//illustriation of the control 
		g.drawImage(arrows , 60, 120, null);
		g.drawImage(right, 180 , 140, null);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Monospaced",Font.PLAIN,12));
		g.drawString("move", 300 , 160);
		
		g.drawImage(mouse, 40 , 220, null);
		g.drawImage(right, 180, 230, null);
		g.drawString("move", 300, 250);
		
		g.drawImage(q, 60, 320, null);
		g.drawImage(right, 180, 330, null);
		g.drawString("attack monster",300 ,350);
		
		g.drawImage(leer, 60, 400, null);
		g.drawImage(right, 180 , 400 , null);
		g.drawString("pick up item", 300, 420);
		
		g.drawImage(b,60, 450, null);
		g.drawImage(right, 180, 465, null);
		g.drawString("take the potion", 300, 480);
		
	}
	
}
