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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;


public class LoginBuild extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_1;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private JButton LogButton;
	private JButton RegButton;
	
	public static final int BOX = 32;
	public static final int WIDTH = 16, HEIGHT = 16;
	/**
	 * Launch the application.
	 */
	
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

	/**
	 * Create Login Window
	 */
	public LoginBuild() {
		setResizable(false);
		setBounds(200, 200, 400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setTitle("Welcome!");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);

		// LoginPanel erstellt	
		JPanel LoginPanel = new JPanel();
		LoginPanel.setSize(new Dimension(100, 100));
		LoginPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.addTab("Login", null, LoginPanel, null);
		LoginPanel.setLayout(new GridLayout(5, 0, 5, 5)); //z Zeilen,s Spalten, abstaende
		// LoginPanel.setBackground( Color.RED );
		
		
		// add username label
		JLabel logLabel_1 = new JLabel("Username");
		logLabel_1.setBounds(10, 10, 169, 14);
		LoginPanel.add(logLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 25, 169, 20);
		LoginPanel.add(textField_1);
		
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
		LoginPanel.add(LogButton);
		

		// RegisterPanel erstellt
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
		
		// fuegt confirm password in den RegisterPanel ein
		JLabel lblPasswordConfirm = new JLabel("Confirm password");
		lblPasswordConfirm.setBounds(10, 101, 169, 14);
		RegisterPanel.add(lblPasswordConfirm);

		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(10, 115, 169, 20);
		RegisterPanel.add(passwordField_2);
		
		RegButton = new JButton("Register");
		RegButton.setBounds(10, 185, 169, 23);
		RegisterPanel.add(RegButton);
				
		// add ButtonListener object to buttons 
		ButtonListener bL = new ButtonListener(textField_1, passwordField_1);
		LogButton.addActionListener(bL);
		RegButtonListener rL = new RegButtonListener(textField_1, passwordField_1, passwordField_2);
		RegButton.addActionListener(rL);	
	}
	 
	}
	
	// plan: here implement method that login worked, after checking username and pw, then open the gamewindow automatically 
	
		// ButtonListener class within: what happens after clicking the button login 
		class ButtonListener implements ActionListener {
		

		private String user;
		private String pw;
	LoginBuild	lg= new LoginBuild();
		public ButtonListener(JTextField text, JPasswordField pw){
			
			
			this.user = text.getText();
			this.pw = pw.getText();
		}
			
			public void actionPerformed(ActionEvent ae){
				
			if(ae.getSource()==lg.LogButton){	
			System.out.println("login success");
			if(user.equals("user")&& pw.equals("123")){   // data for test 
				System.out.println("Login erfolgreich!");
			}else{
//				JOptionPane.showMessageDialog(null, "Wrong Username or Password");
				System.out.println("Falsche eingabe");
			}
			}}
		}
			
		class RegButtonListener implements ActionListener {
			private String user2;
			private String pw1;
			private String pw2;
			
			public RegButtonListener (JTextField text2, JPasswordField pw1, JPasswordField pw2){
				this.user2 =text2.getText();
				this.pw1 = pw1.getText();
				this.pw2 = pw2.getText();
			}
			public void actionPerformed(ActionEvent ae){
				System.out.println("register success");
				
			}
		
		
	
	/*public void actionlogin(){
		LogButton.addActionListener(new ActionListener() { //kann nicht auf die variable zugreifen; wie löse ich das?
		public void actionPerformed(ActionEvent ae) {
		  //JTextfield
		String paswd = passwordField_1.getText();		//JPasswordField
		if(name.equals("user") && paswd.equals("123")) {
		
		// hier wird der neue Frame: in unserem Fall das Spielfenster: GameWindow + starte neues spiel. bzw. da wo der spieler aufgehört hat
		GameWindow gWindow = new GameWindow(BOX*WIDTH, BOX*HEIGHT, "Hindi Bones");
		gWindow.setVisible(true);
		dispose();
		} else {

		JOptionPane.showMessageDialog(null,"Wrong Password / Username");
		textField_1.setText("");
		passwordField_1.setText("");
		textField_1.requestFocus();
		}

		}
		});
		}  
		*/
	
	// method for register. senden der daten an die engine olli 
		
	/* public void actionregister () {
	 * 
	 */


		}
