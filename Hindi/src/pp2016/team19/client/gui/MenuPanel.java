package pp2016.team19.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Felizia Langsdorf, Matr_Nr: 6002960
 *
 */

// class for the menu, shown after successful login
// problems occurred with the paint method, read the text below at the end of this class

public class MenuPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

//	private Image menu;
	private GameWindow window;
	public JPanel p;
	private JButton b1;
	private JButton b2;
	
	//constructor of the menupanel	
	public MenuPanel(GameWindow window){
		
		this.window = window;
//		try{
//			menu=ImageIO.read(new File("img//menu.png"));
//		} catch (IOException e) {
//			System.err.println("Error while loading one of the images.");
//		}
		
		// create panel, set its color and size, etc.
		p = new JPanel();
		p.setBackground(Color.LIGHT_GRAY);
		p.setPreferredSize(new Dimension(400, 500)); 	
		p.setLayout( new GridLayout( 2, 0 ));
		p.setVisible(true);
		// create the two buttons
		b1 = new JButton("Start Game");
		b2 = new JButton("Logout");
		// adding the panel to the gamewindow
		add(p, BorderLayout.CENTER);
		// adding the buttons to the panel
		p.add(b1);
		p.add(b2);
		// adding the Actionlisteners to the buttons
		b1.addActionListener(this); 	
		b2.addActionListener(this);			
	}

	
	// method of actionlistener; adding the actions after clicking on the buttons
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==b1){			//pressing button start game, then game starts 
			window.showGameField();
	}else if (ae.getSource() == b2)	{
		window.showLogin();      // here's going to be a proper method for logging out
	}	
}

//	public void paint(Graphics g){
//	//g.setColor(Color.GRAY);
//	//g.fillRect(0, 300, 300,100);
//	g.drawImage(menu, this.getWidth()/3, 300, null);	
//	}
	
// !!! logout button disappears if anything is being painted, after clicking somewhere randomly outside the window(JFrame) button shows up again. magic?? 
}




