package pp2016.team19.client.gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class SystemMessages extends JFrame {
	
	private final static String line = "\n";	
	private JTextArea messages;
	private JPanel messages1;
	
	public SystemMessages(){
		this.setTitle("System Messages");
		this.setSize(500, 200);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		messages = new JTextArea();
		messages.setBounds(0, 0, 400, 300);
		messages.setLayout(new GridLayout(15,0,0,0));
		messages.setLineWrap(true);
		messages.setWrapStyleWord(true);
		messages.setEditable(false);
		messages.setVisible(true);
		messages.append("hallo 123 huhuuu juhulaekhröglhsargoihdfgohgä" + line);
		messages.append("zweite message hier bin ich"+ line);
		messages.append("blubiibibisaoishg"+ line);
		messages.append("grrr" + line);
		messages.append("blablallbaaa" + line);
		
		
		JScrollPane scroll = new JScrollPane(messages);
		scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setVisible(true);
		
		this.add(scroll);	
		
	}
	
	public static void main (String[] args){
		SystemMessages messagewindow = new SystemMessages();
		
	}

}
