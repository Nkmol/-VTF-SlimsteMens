package View;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

public class ChatPanel extends JPanel implements Observer {

	private JFrame frame;
	private JButton sendMessage;
	private JTextField messageBox;
	private JTextArea chatBox;
	private String currentPlayerUsername;

	public ChatPanel() {
		createChatPanel();
		
		currentPlayerUsername = "Jamam";
	}

	private void createChatPanel() {
		
		frame = new JFrame();
		frame.setSize(500, 555);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		frame.getContentPane().setLayout(new MigLayout("", "[470px]", "[1px]"));
		this.setLayout(new MigLayout());
		
		chatBox = new JTextArea();
		chatBox.setEditable(false);
		chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
		chatBox.setLineWrap(true);
		
		this.add(chatBox, "grow, pushx, wrap");

		messageBox = new JTextField(40);
		messageBox.requestFocusInWindow();
		
		
		this.add(messageBox);

		sendMessage = new JButton("Send");
		sendMessage.addActionListener(e -> activateChatboxBehavior());

		
		this.add(sendMessage);

		frame.getContentPane().add(this, "cell 0 0,growx,aligny top");
		frame.setVisible(true);

	}

	private void activateChatboxBehavior() {
		if (messageBox.getText().length() < 1) {
			// do nothing
		} else {
			chatBox.append("<" + currentPlayerUsername + ">:  " + messageBox.getText() + "\n");
			messageBox.setText("");
		}
		messageBox.requestFocusInWindow();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
}
