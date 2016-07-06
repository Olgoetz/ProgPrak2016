package pp2016.team19.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * <h1> class for the welcome panel shows up after a successful login <h1>
 * 
 * @author Felizia Langsdorf, 6002960
 */

public class MenuPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private ImageIcon picture = new ImageIcon("img//labyrinth.png");//source:http://brandedinthe80s.com/wp-content/uploads/2014/03/2013-04-10-labyrinth.gif
	private JLabel lab;

	private GameWindow window;

	private JButton b1;
	private JButton b2;
	

	/**
	 * @author Felizia Langsdorf, Matr_Nr: 6002960
	 * @param window
	 *            window of the application
	 */
	public MenuPanel(GameWindow window) {

		this.window = window;
		this.setLayout(new FlowLayout());

		// add the label (picture) on the panel
		lab = new JLabel(picture);
		lab.setPreferredSize(new Dimension(500, 500));
		add(lab);
		// add the buttons
		b1 = new JButton("Start Game");
		b2 = new JButton("Logout");
		b1.setBounds(150, 150, 200, 80);
		b2.setBounds(150, 250, 200, 80);
		lab.add(b1);
		lab.add(b2);
		b1.setBackground(new Color(205, 179, 139));
		b1.setOpaque(true);
		b2.setContentAreaFilled(false);
		b2.setBackground(new Color(205, 179, 139));
		b2.setOpaque(true);
		b1.setVisible(true);
		b2.setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
		
	}

	/**
	 * @author Felizia Langsdorf, 6002960
	 * method of the interface actionListener: after clicking the buttons the
	 * game starts or logout is initiated
	 */

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b1) { // button start game
			window.initializeGameframe(32*16, 32*16, "Hindi Bones"); 
			window.getEngine().getThreadPool().execute(window);

		} else if (ae.getSource() == b2) {
			window.showLogin(); // here's going to be a proper method for
								// logging out
		}
	}

}
