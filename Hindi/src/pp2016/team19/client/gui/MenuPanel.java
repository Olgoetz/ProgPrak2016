package pp2016.team19.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

// author: Felizia Langsdorf, Matr_Nr: 6002960
// class for the menu, shown after successful login

public class MenuPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Image menu;
	private GameWindow window;
	
	public JPanel p;
	private JButton b1;
	private JButton b2;
	 
	//constructor of the menupanel
	
	public MenuPanel(GameWindow window){
		
		this.window = window;
		try{
			menu=ImageIO.read(new File("img//menu.png"));
		} catch (IOException e) {
			System.err.println("Error while loading one of the images.");
		}
		
		p = new JPanel();
		p.setLayout(null);
		p.setBackground(Color.LIGHT_GRAY);
		//p.setSize(400, 300);
		
		p.setLayout( new GridLayout( 2, 0 ));
		//p.setBounds(0, 0, 400, 600);
		p.setVisible(true);
		 
		b1 = new JButton("Start Game");
		b2 = new JButton("Logout");
//		b1.setBounds(0, 0, 200, 100);
//		b2.setBounds(0, 0, 200, 100);

		// adding the panel to the gamewindow
		add(p);
		// adding the buttons to the panel
		p.add(b1);
		p.add(b2);
		// adding the Actionlisteners to the buttons
		b1.addActionListener(this); 	
		b2.addActionListener(this);			
	}
	
	
	// man endet nach klicken der buttons leider in einer sackgasse, man kommt nicht zurück, idee menubar dazu machen kooootz
	
	// method of actionlistener; adding the actions after clicking on the buttons
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==b1){	
			window.showGameField();
	}else if (ae.getSource() == b2)	{
		System.exit(0);      //ordentliches ausloggen kommt noch :) jetzt erst einfach mal anwendung schließen
	}
	
}
	
// logout button verschwindet, nachdem außerhalb des frames klickt, taucht er auf wtf???
//	public void paint(Graphics g){
//	//g.setColor(Color.GRAY);
//	//g.fillRect(0, 300, 300,100);
//	g.drawImage(menu, this.getWidth()/3, 300, null);	
//	}
}

	


