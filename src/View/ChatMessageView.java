package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import Models.ChatMessage;
import net.miginfocom.swing.MigLayout;

public class ChatMessageView extends JPanel implements Observer {

	private JFrame frame;
	public JButton btnSendMessage;
	private JTextField messageBox;
	private JTextArea chatBox;
	private String currentPlayerUsername;

	public ChatMessageView() {
		createChatPanel();
		
		//currentPlayerUsername = "Jamam";
	}

	private void createChatPanel() {
		
		
		//this.setLayout(new MigLayout());
		
		setLayout(new BorderLayout());
		
		chatBox = new JTextArea(17,16);
		chatBox.setEditable(false);
		chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
		chatBox.setLineWrap(true);

		
		JScrollPane scroll = new JScrollPane(chatBox);
	    //scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel topContainer = new JPanel();
		topContainer.setLayout(new BorderLayout());
		topContainer.add(scroll, BorderLayout.CENTER);
	    
		add(topContainer, BorderLayout.CENTER);

		//this.add(chatBox, "wrap");

		
		messageBox = new JTextField(10);
		messageBox.requestFocusInWindow();
		
		//this.add(messageBox, BorderLayout.SOUTH);
		//this.add(btnSendMessage);
		
		btnSendMessage = new JButton("Verzend");
		
		JPanel bottomContainer = new JPanel();
		bottomContainer.setLayout(new BorderLayout());
		bottomContainer.add(messageBox, BorderLayout.CENTER);
		bottomContainer.add(btnSendMessage, BorderLayout.EAST);
		
		add(bottomContainer, BorderLayout.SOUTH);
	}

/*	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}*/

	public String getMessage(){
		return messageBox.getText();
	}
	
	public void clearMessageBox(){
		messageBox.setText("");
	}
	
	public void updateChatBehavior(ArrayList<ChatMessage> chatMessages)
	{
		//System.out.println("updateChatBehavior");
		
		chatBox.setText(null);
		
		for(ChatMessage message: chatMessages){
			
		chatBox.append("<" + message.getSenderName() + ">:  " + message.getMessage() + "\n");
		//messageBox.setText("");
			
		}
		//messageBox.requestFocusInWindow();
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
}
