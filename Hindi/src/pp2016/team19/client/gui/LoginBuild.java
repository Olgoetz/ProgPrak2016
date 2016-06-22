package pp2016.team19.client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

// author: Felizia Langsdorf, Matr_Nr.: 6002960
// login window, getText method of JTextfield is not working 

public class LoginBuild extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JTextField textField_1;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private JButton LogButton;
	private JButton RegButton;
	
//	private String username = "";

	public static final int BOX = 32;
	public static final int WIDTH = 16, HEIGHT = 16;

// die main muss noch weg, loginbuild soll in der hindibones main aufgerufen werden nicht mehr gamewindow! 	
 public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	public void run() {
	try {
			LoginBuild frame = new LoginBuild();
				frame.setVisible(true);
		} catch (Exception e) {
				e.printStackTrace();
			}
	}
	});
  }

// create the window (constructor)
// first without a registerpanel; just prepared it 
 
	public LoginBuild() {
		setResizable(false);
		setBounds(200, 200, 400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setTitle("Welcome!");
		this.setVisible(true);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);

		// create LoginPanel 
		JPanel LoginPanel = new JPanel();
		LoginPanel.setSize(new Dimension(100, 100));
		LoginPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.addTab("Login", null, LoginPanel, null);
		LoginPanel.setLayout(new GridLayout(5, 0, 5, 5)); //z Zeilen,s Spalten, abstaende
		getContentPane().add(LoginPanel);
		
		// add username label
		JLabel logLabel_1 = new JLabel("Username");
		logLabel_1.setBounds(10, 10, 169, 14);
		LoginPanel.add(logLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 25, 169, 20);
		LoginPanel.add(textField_1);
		//textField_1.setEditable(true);
		
		// add password label
		JLabel logLabel_2 = new JLabel("Password");
		logLabel_2.setBounds(10, 56, 169, 14);
		LoginPanel.add(logLabel_2);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(10, 70, 50, 20);
		LoginPanel.add(passwordField_1);
		
		// add login button
		LogButton = new JButton("Login");
		LogButton.setBounds(10, 185, 169, 23);
		LogButton.addActionListener(this); 	
		LoginPanel.add(LogButton);	

		// create RegisterPanel 
		JPanel RegisterPanel = new JPanel();
		tabbedPane.addTab("Register", null, RegisterPanel, null);
		RegisterPanel.setLayout(null);

		// add username 
		JLabel regLabel_1 = new JLabel("Username");
		regLabel_1.setBounds(10, 11, 169, 14);
		RegisterPanel.add(regLabel_1);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 25, 169, 20);
		RegisterPanel.add(textField_1);

		// add password
		JLabel regLabel_2 = new JLabel("Password");
		regLabel_2.setBounds(10, 56, 169, 20);
		RegisterPanel.add(regLabel_2);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(10, 74, 169, 20);
		RegisterPanel.add(passwordField_1);
		
		// adding confirm password in the RegisterPanel 
		JLabel lblPasswordConfirm = new JLabel("Confirm password");
		lblPasswordConfirm.setBounds(10, 101, 169, 14);
		RegisterPanel.add(lblPasswordConfirm);

		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(10, 115, 169, 20);
		RegisterPanel.add(passwordField_2);
		
		RegButton = new JButton("Register");
		RegButton.setBounds(10, 185, 169, 23);
		RegisterPanel.add(RegButton);		
	}

// plan: here implement method that login worked, after checking username and pw, then open the gamewindow automatically
// now just cheap System.out.println, checks that buttons works. 
// comparison in if clause is not working because getText() method is getting nothing! can't explain why, tried everything :(
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==LogButton){				
//	username = textField_1.getText();  //JTextfield
//	String password = passwordField_1.getText();		//JPasswordField
//	if(username.equals("user") && password.equals("123")) {
		System.out.println("login successful");
	}else{
//		JOptionPane.showMessageDialog(null, "Wrong Username or Password");
		System.out.println("button not working");
	}		
	}
}



// hier wird der neue Frame 
//	Newframe regFace =new Newframe();
//	regFace.setVisible(true);
//	dispose();
//	} else {
//
//	JOptionPane.showMessageDialog(null,"Wrong Password / Username");
//	txuser.setText("");
//	pass.setText("");
//	txuser.requestFocus();
//	}

//
//		if(name.equals("user") && paswd.equals("123")) {
//		
//		// hier wird der neue Frame: in unserem Fall das Spielfenster: GameWindow + starte neues spiel. bzw. da wo der spieler aufgehört hat
//		GameWindow gWindow = new GameWindow(BOX*WIDTH, BOX*HEIGHT, "Hindi Bones");
//		gWindow.setVisible(true);
//		dispose();
//		} else {
//
//		JOptionPane.showMessageDialog(null,"Wrong Password / Username");
//		textField_1.setText("");
//		passwordField_1.setText("");
//		textField_1.requestFocus();
//		}
//
//		}
//		});
//		}  

	


		
