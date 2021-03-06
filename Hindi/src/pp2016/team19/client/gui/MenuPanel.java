package pp2016.team19.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * <h1>class for the welcome panel shows up after a successful login.</h1>
 * @author Langsdorf, Felizia, 6002960
 */

public class MenuPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	// background image
	private ImageIcon picture = new ImageIcon("img//labyrinth.png");// source:http://brandedinthe80s.com/wp-content/uploads/2014/03/2013-04-10-labyrinth.gif
	private JLabel lab;
	// gamewindow JFrame
	private GameWindow window;
	// two buttons
	private JButton b1;
	private JButton b2;

	/**
	 * @author Langsdorf, Felizia, 6002960
	 * @param window
	 *            gamewindow of the application
	 */
	public MenuPanel(GameWindow window) {

		this.window = window;
		// layout of the panel
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
		// adding the actionlistener to the buttons
		b1.addActionListener(this);
		b2.addActionListener(this);

	}

	/**
	 * method of the interface actionListener: after clicking the buttons the
	 * game starts or logout is initiated
	 * 
	 * @author Langsdorf, Felizia, 6002960
	 * @param ae
	 *            ActionEvent
	 * 
	 */

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b1) { // button start game
			window.initializeGameframe(32 * 16, 32 * 16, "Hindi Bones"); // initializes the window for playing the game																			
			window.getEngine().getThreadPool().execute(window);
		} else if (ae.getSource() == b2) {
			window.engine.serverSignOutRequest(); // logout
		}
	}

}
