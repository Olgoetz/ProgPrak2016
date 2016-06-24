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

	private ImageIcon picture = new ImageIcon("img//labyrinth.png");
	private JLabel lab;
	
	private GameWindow window;
	
	private JButton b1;
	private JButton b2;
	
	//constructor of the menupanel	
		public MenuPanel(GameWindow window){
		
		this.window = window;		
		this.setLayout(new FlowLayout());			
		
		lab = new JLabel(picture);
		lab.setPreferredSize(new Dimension(500,500));
		add(lab);
		
		b1 = new JButton("Start Game");
		b2 = new JButton("Logout");
		b1.setBounds(150, 150, 200, 80);
		b2.setBounds(150, 250 , 200, 80);
		lab.add(b1);
		lab.add(b2);
		b1.setBackground(new Color (205,179,139));
		b1.setOpaque(true);
		b2.setContentAreaFilled(false);
		b2.setBackground(new Color(205,179,139) );
		b2.setOpaque(true);
		b1.setVisible(true);
		b2.setVisible(true);
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

}




