package pp2016.team19.client.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	JButton start = new JButton("Start");
	JButton highscore = new JButton("Highscore");
	JButton quit = new JButton("Quit");
	JTextField name = new JTextField("Player One");
	
	GameWindow window;
	
	public MenuPanel(GameWindow w){
		this.window = w;
		
		setLayout(new GridLayout(5,1));
		
		
		JPanel p = new JPanel(){
			private static final long serialVersionUID = 1L;
			
			public void paint(Graphics g){
				Image img = null;
				
				try{
					img = ImageIO.read(new File("img//menu.png"));
				}catch(IOException e){ }
				
				g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
				g.setColor(Color.WHITE);
			}
			
		};
				
		add(p);
		
		// Start-New-Game-Button
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				window.showGameField();
				window.player.setName(name.getText());
			}	
		});
		//start.setPreferredSize(new Dimension(200,50));
		
		// Show-Highscore-Button
		highscore.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				window.showHighscore();
			}	
		});
		//highscore.setPreferredSize(new Dimension(200,50));
		
		// Quit-Button
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}	
		});
		//quit.setPreferredSize(new Dimension(200,50));

		name.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				window.showGameField();
				window.player.setName(name.getText());
			}	
		});
		//name.setPreferredSize(new Dimension(200,50));
		
		
		add(name);
		add(start);
		add(highscore);
		add(quit);
		
		p.repaint();

	}
	
}
