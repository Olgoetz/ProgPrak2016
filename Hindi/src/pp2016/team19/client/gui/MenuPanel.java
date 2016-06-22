package pp2016.team19.client.gui;

import java.awt.*;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class MenuPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	private Image menu;
	
	private GameWindow window;
	
	private JPanel p;
	private JButton b1;
	private JButton b2;
	private JButton b3; 
	
	
	public MenuPanel(GameWindow window){
		
		this.window = window;
//		try{
//			menu=ImageIO.read(new File("img//menu.png"));
//		} catch (IOException e) {
//			System.err.println("Error while loading one of the images.");
//		}
		
		p=new JPanel();
		p.setBackground(Color.LIGHT_GRAY);
		
		p.setLayout( new GridLayout( 3, 0 ));
		p.setBounds(20, 100, 400, 600);
		p.setVisible(true);
		 
		b1 = new JButton("Start Game");
		b2 = new JButton("Show Highscore");
		b3 = new JButton("Logout");
		
		p.add(b1);
		p.add(b2);
		p.add(b3);
		this.add(p);
		
	}
}

//	public void paint(Graphics g){
//		
//		g.setColor(Color.GRAY);
//		g.fillRect(0, 0, 400,200);
//
//		g.drawImage(menu, 20, 20, null);	
			
	// Start-New-Game-Button
//		start.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				window.showGameField();
////				window.player.setName(name.getText());
//			}	
//		});
//		
//		// Show-Highscore-Button
//		highscore.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				window.showHighscore();
//			}	
//		});
//			
//		// Quit-Button
//		quit.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				System.exit(0);
//			}	
//		});
//		
//	
//
//	}
//	
//}
