package pp2016.team19.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;


public class LoginPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	
	private GameWindow window;
	private JTextField textField_1;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private JButton LogButton;
	private JButton RegButton;
	private String username = "";

	
	public LoginPanel (GameWindow window){
		
		this.window = window;
		
		JPanel LoginPanel = new JPanel();
		LoginPanel.setPreferredSize(new Dimension(400, 500)); 	
		//LoginPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		LoginPanel.setLayout(new GridLayout(5, 0, 5, 5)); 
		LoginPanel.setBackground(Color.LIGHT_GRAY);
		LoginPanel.setVisible(true);
		
		JLabel logLabel_1 = new JLabel("Username");
		logLabel_1.setBounds(10, 10, 169, 14);
		LoginPanel.add(logLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 25, 169, 20);
		LoginPanel.add(textField_1);
		textField_1.setText("user");
		
		// add password label
		JLabel logLabel_2 = new JLabel("Password");
		logLabel_2.setBounds(10, 56, 169, 14);
		LoginPanel.add(logLabel_2);
				
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(10, 70, 50, 20);
		LoginPanel.add(passwordField_1);
		passwordField_1.setText("123");
				
		// add login button
		LogButton = new JButton("Login");
		LogButton.setBounds(10, 185, 169, 23);
		LogButton.addActionListener(this); 	
		LoginPanel.add(LogButton);	
		
		
		add(LoginPanel, BorderLayout.CENTER);		
		
	}

	// plan: here implement method that login worked, after checking username and pw, then open the gamewindow automatically
	// now just cheap System.out.println, checks that buttons works. 
	// for login tip: username: user and password: 123
	
		public void actionPerformed(ActionEvent ae) {
			if(ae.getSource()==LogButton){				
		username = textField_1.getText();  //JTextfield
		String password = passwordField_1.getText();		//JPasswordField
		if(username.equals("user") && password.equals("123")) {
			System.out.println("login successful, welcome" + " " + username);
			window.showMenu();
		}else{
			JOptionPane.showMessageDialog(null, "Wrong Username or Password");
		}		
	}

}
		
}
	

