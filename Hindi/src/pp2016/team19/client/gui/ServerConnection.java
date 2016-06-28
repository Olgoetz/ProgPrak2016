package pp2016.team19.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Felizia Langsdorf, Matr_Nr: 6002960
 *
 */

public class ServerConnection extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private ImageIcon pic = new ImageIcon("img//Slide2.png");
	private JLabel server;
	
	private GameWindow window;
 
	private JTextField servername;
	private JTextField port;
	private JButton b;
	
	

		public ServerConnection(GameWindow window){		
		this.window = window;					
		
		server = new JLabel(pic);
		server.setBounds(0, 0, 500, 500); 
		add(server);
		
		servername = new JTextField();
		servername.setBounds(250, 130, 100, 25);
		server.add(servername);

		port = new JTextField();
		port.setBounds(250, 195, 100, 25);
		server.add(port);
			
		b = new JButton("Connect to Server");		
		b.setBounds(120, 270, 200, 40); 		
		server.add(b);	
		b.setOpaque(true);
		b.setVisible(true);
		b.addActionListener(this); 	
	}

	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==b){			
			window.showLogin();  // olli connect with server !!
	}else{
		      // here's going to be a proper method or message for server connection failed
		}	
	}

}


	
	
	


