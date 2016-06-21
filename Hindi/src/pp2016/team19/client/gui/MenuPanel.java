package pp2016.team19.client.gui;

import java.awt.*;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class MenuPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
//	JButton start = new JButton("Start Game");
//	JButton highscore = new JButton("Show Highscore");
//	JButton quit = new JButton("Quit");
	private Image menu;
	
	private GameWindow window;
	
	private JPanel p;
	private JButton b1;
	private JButton b2;
	private JButton b3; 
	
	
	public MenuPanel(GameWindow window){
		this.window = window;
		try{
			menu=ImageIO.read(new File("img//menu.png"));
		} catch (IOException e) {
			System.err.println("Error while loading one of the images.");
		}
		
		p=new JPanel();
		p.setBackground(Color.YELLOW);
		p.setLayout( new GridLayout( 3, 0 ));
		 
		b1 = new JButton("Button1");
		b2 = new JButton("Button2");
	
		b3 = new JButton("Button3");
		
		p.add(b1);
		p.add(b2);
		p.add(b3);
		
	}
}

//	public void paint(Graphics g){
//		this.add(start);
//		this.add(highscore);
//		this.add(quit);
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
