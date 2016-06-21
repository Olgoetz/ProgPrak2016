package pp2016.team19.client.gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MenuBuild extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private JButton b1;
	private JButton b2;
	private JButton b3;
	
	public static final int BOX = 32;
	public static final int WIDTH = 16, HEIGHT = 16;
	
	 
	
	public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
			public void run() {
			try {
					MenuBuild frame = new MenuBuild();
						frame.setVisible(true);
				} catch (Exception e) {
						e.printStackTrace();
					}
			}
			});
		  }
	public MenuBuild() {
		setResizable(false);
		setBounds(200, 200, 400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(3, 0));
		setTitle("Welcome!");
		
		b1 = new JButton("Start Game");
		b2 = new JButton("Show Highscore");	
		b3 = new JButton ("Logout");
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		
		this.add(b1);
		this.add(b2);
		this.add(b3);		
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==b1){	
		System.out.println("start juhu");  // showGamefield, open hindi bones main??
		}else if(ae.getSource() == b2){
		System.out.println("highscore juhu");  //showHighscore()
		}else if(ae.getSource()== b3){
		System.out.println("byeee");      //quitGame, besser später richtiges ausloggen
	}		
	}
	
	
}



