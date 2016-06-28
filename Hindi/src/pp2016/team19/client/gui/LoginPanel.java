package pp2016.team19.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;


/**
 * class for the Panel which shows the login and the registration
 * interface ActionListener is implemented
 * @author Felizia Langsdorf, Matr_Nr: 6002960
 *
 */

public class LoginPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
		
	private GameWindow window;
	
	private JTextField textField_1;
	
	private JPasswordField passwordField_1;
	private JButton logButton;
	private String logUsername = "";
	private String userpassword ="";
	
	private JTextField textField_2;
	private JPasswordField passwordField_2;
	private JPasswordField passwordField_3;
	private JButton regButton;
	private JTextField message;
	private String regUsername = "";
	private String regPassword1 = "";
	private String regPassword2 = "";
	
	/**
	 *
	 * @author Felizia Langsdorf, Matr_Nr: 6002960
	 * @param window window of the application
	 */
	
	public LoginPanel (GameWindow window){		
		this.window = window;
		
		// the Loginpanel
		JPanel LoginPanel = new JPanel();
		LoginPanel.setPreferredSize(new Dimension(500,500)); 	
		//panel layout using gridlayout with 15 lines
		LoginPanel.setLayout(new GridLayout(15, 0, 5, 5)); 
		LoginPanel.setBackground(Color.LIGHT_GRAY);
		LoginPanel.setVisible(true);
			
		//heading login
		JLabel lHeading = new JLabel("LOGIN");
		lHeading.setFont(new Font("Monospaced", Font.BOLD, 20));
		LoginPanel.add(lHeading);	
		
		//username		
		JLabel logLabel_1 = new JLabel("Username");
		LoginPanel.add(logLabel_1);
		
		//textfield for the username
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		LoginPanel.add(textField_1);
		textField_1.setText("user");
		
		// password
		JLabel logLabel_2 = new JLabel("Password");
		LoginPanel.add(logLabel_2);
		
		// passwordfield for the password
		passwordField_1 = new JPasswordField();
		LoginPanel.add(passwordField_1);
		passwordField_1.setText("123");
				
		//login button
		logButton = new JButton("Login");
		logButton.addActionListener(this); 	
		LoginPanel.add(logButton);	
		
		//heading register
		JLabel rHeading = new JLabel("REGISTER");
		rHeading.setFont(new Font("Monospaced", Font.BOLD, 20));
		LoginPanel.add(rHeading);
		
		//username
		JLabel regLabel_1 = new JLabel("Username");
		LoginPanel.add(regLabel_1);
		
		//textfield for desired username
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		LoginPanel.add(textField_2);
		
		//registration password
		JLabel regLabel_2 = new JLabel("Password");
		LoginPanel.add(regLabel_2);
		
		//passwordfield for registration password
		passwordField_2 = new JPasswordField();
		passwordField_2.setColumns(10);
		LoginPanel.add(passwordField_2);
		
		//confirm password
		JLabel regLabel_3 = new JLabel("Confirm Password");
		LoginPanel.add(regLabel_3);
		
		// passwordfield for confirming the password
		passwordField_3= new JPasswordField();
		passwordField_3.setColumns(10);
		LoginPanel.add(passwordField_3);
		
		// register button
		regButton = new JButton("Register");
		LoginPanel.add(regButton);	
		regButton.addActionListener(this); 	
		
		//texfield for systemessages
		message = new JTextField();
		LoginPanel.add(message);
		
		//add the panel to gamewindow
		add(LoginPanel, BorderLayout.CENTER);				
		
	}

	// plan: here implement method that login worked, after checking username and pw, then open the gamewindow automatically
	// now just cheap System.out.println, checks that buttons works. 
	// for login tip: username: user and password: 123
	
	/**
	 * actionPerformed class for the buttons
	 * @author Felizia Langsdorf, Matr_Nr: 6002960
	 * @param ae ActionEvent of buttons
	 */
		public void actionPerformed(ActionEvent ae) {
			if(ae.getSource()==logButton){				
		logUsername = textField_1.getText();  //JTextfield
		userpassword = passwordField_1.getText();	//JPasswordField
			window.engine.serverSignInRequest(logUsername, userpassword);
			}else if(ae.getSource() == regButton){
				//regUsername speichern: Olli
				// achtung: namen der variablen haben sich geändert !!!
				// plus methode von olli fuer überprüfen ob passwörter gleich eingegben 
				// olli weißte ja dann bescheid, kannst dann alles unnötige wieder weg machen
				regPassword1 = passwordField_2.getText();
				regPassword2 = passwordField_3.getText();
				if(regPassword1 == regPassword2){
					System.out.println("user is registered");
				}								
			}

}

}
		

	

