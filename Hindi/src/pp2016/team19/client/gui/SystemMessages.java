package pp2016.team19.client.gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * <h1> system messages frame. <h1>
 * 
 * @author Felizia Langsdorf, 6002960
 */


public class SystemMessages extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final static String line = "\n"; //break
	private JTextArea messages;
	
	/**
	 * constructor
	 * @author Felizia Langsdorf, 6002960
	 */

	public SystemMessages(){
		this.setTitle("System Messages"); // title of the window
		this.setSize(500, 200); //size
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		messages = new JTextArea(); // textarea for the messages
		messages.setBounds(0, 0, 400, 300);
		messages.setLayout(new GridLayout(15,0,0,0)); //15 rows
		messages.setLineWrap(true);
		messages.setWrapStyleWord(true);
		messages.setEditable(false); //not editable
		messages.setVisible(true);
		
		// for(i=0; i < 11; i++ ) {
//		messages.append( strings aus der schlange + line);
	
//	}
		messages.append("hallo 123 huhuuu juhulaekhröglhsargoihdfgohgä" + line);
		messages.append("zweite message hier bin ich"+ line);
		messages.append("blubiibibisaoishg"+ line);
		messages.append("grrr" + line);
		messages.append("blablallbaaa" + line);
		
		
		JScrollPane scroll = new JScrollPane(messages); //set the textarea into a scroll pane
		scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // with a visible scrollbar on the right
		scroll.setVisible(true);	
		this.add(scroll);	//adding the scrollpane to the window		
	}
	
	/**
	 * main method
	 * @author Felizia Langsdorf, 6002960
	 */

	public static void main (String[] args){
		SystemMessages messagewindow = new SystemMessages();
		
	}

}
