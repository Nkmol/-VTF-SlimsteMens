package View;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
		
		//frame = new JFrame();
		//frame.setSize(500, 555);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		//frame.getContentPane().setLayout(new MigLayout("", "[470px]", "[1px]"));
		//this.setLayout(new MigLayout());
		
		chatBox = new JTextArea();
		chatBox.setEditable(false);
		chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
		chatBox.setLineWrap(true);
		
		this.add(chatBox, "grow, pushx, wrap");

		messageBox = new JTextField(30);
		messageBox.requestFocusInWindow();
		
		
		this.add(messageBox);

		btnSendMessage = new JButton("Send");
		//btnSendMessage.addActionListener(e -> activateChatboxBehavior());

		
		this.add(btnSendMessage);

		//frame.getContentPane().add(this, "cell 0 0,growx,aligny top");
		//frame.setVisible(true);

	}

	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}

	public String getMessage(){
		return messageBox.getText();
	}
	
	public void updateChatBehavior(ArrayList<ChatMessage> chatMessages)
	{
		//System.out.println("updateChatBehavior");
		
		chatBox.setText(null);
		
		for(ChatMessage message: chatMessages){
			
		chatBox.append("<" + message.getSenderName() + ">:  " + message.getMessage() + "\n");
		messageBox.setText("");
			
		}
		messageBox.requestFocusInWindow();
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
}
