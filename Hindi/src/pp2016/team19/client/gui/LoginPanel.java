package pp2016.team19.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



/**
 * <h1> class for the Panel which shows the login and the registration. <h1>
 * interface ActionListener is implemented
 * @author Felizia Langsdorf, 6002960
 *
 */

public class LoginPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	//the gamewindow JFrame
	private GameWindow window;
	
	//components 
	private JTextField textField_1;
	private JPasswordField passwordField_1;
	private JButton logButton;
	private String logUsername = "";
	private String userpassword ="";
	
	private JTextField textField_2;
	private JPasswordField passwordField_2;
	private JPasswordField passwordField_3;
	private JButton regButton;
	private String regUsername = "";
	private String regPassword1 = "";
	private String regPassword2 = "";
	
	/**
	 *
	 * @author Felizia Langsdorf, 6002960
	 * @param window gamewindow of the application
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
				
		// password
		JLabel logLabel_2 = new JLabel("Password");
		LoginPanel.add(logLabel_2);
		
		// passwordfield for the password
		passwordField_1 = new JPasswordField();
		LoginPanel.add(passwordField_1);
	
				
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
		
		//add the panel to gamewindow
		add(LoginPanel, BorderLayout.CENTER);				
		
	}

	
	/**
	 * actionPerformed class for the buttons
	 * @author Felizia Langsdorf, 6002960
	 * @param ae ActionEvent of buttons
	 */
		public void actionPerformed(ActionEvent ae) {
			if(ae.getSource()==logButton){				
		logUsername = textField_1.getText();  // getting the text from the JTextfield
		userpassword = passwordField_1.getText();	//getting the content of the JPasswordField
			window.engine.serverSignInRequest(logUsername, userpassword); //server checks the data
			}else if(ae.getSource() == regButton){
				regUsername = textField_2.getText();
				regPassword1 = passwordField_2.getText();
				regPassword2 = passwordField_3.getText();
				window.gamefieldShown = false;
				window.engine.serverSignUpRequest(regUsername, regPassword1, regPassword2);							
			}

}

}
		

	

