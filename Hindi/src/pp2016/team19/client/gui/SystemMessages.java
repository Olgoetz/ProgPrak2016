package pp2016.team19.client.gui;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * <h1> system messages frame. </h1>
 * 
 * @author Langsdorf, Felizia, 6002960
 * @author Oliver Goetz, 5961343
 */


public class SystemMessages extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final static String line = "\n"; //break
	private JTextArea messages; 
	
	/**
	 * constructor
	 * @author Langsdorf, Felizia, 6002960
	 */

	public SystemMessages(){

		messages = new JTextArea(); // textarea for the messages
		messages.setBounds(0, 0, 300, 200);
		messages.setLayout(new GridLayout(15,0,0,0)); //15 rows
		messages.setLineWrap(true);
		messages.setWrapStyleWord(true);
		messages.setEditable(false); //not editable
		messages.setVisible(true);
		 
		
		JScrollPane scroll = new JScrollPane(messages); //set the textarea into a scroll pane
		scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // with a visible scrollbar on the right
		scroll.setVisible(true);	
		this.add(scroll);	//adding the scrollpane to the window		
	}
	
	/**
	 * Gets the JTextArea.
	 * 
	 * @author Oliver Goetz, 5961343
	 * @return messages the messages
	 */
	public JTextArea getMessArea() {
		return messages;
	}
	
}
