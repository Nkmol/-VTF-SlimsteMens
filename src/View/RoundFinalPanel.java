package View;

import java.awt.Font;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class RoundFinalPanel extends JPanel implements Observer {

	
	private JFrame frame;
	private JLabel question;
	private JLabel timeremaining;
	private JButton insertAnswer;
	private JTextField messageBox;
	
	public RoundFinalPanel()
	{
		constructFinalRound();
	}

	private void constructFinalRound() {
		// TODO Auto-generated method stub
		
		frame = new JFrame();
		//frame.setBounds(100, 100, 500, 500);
		frame.setSize(500, 555);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		frame.getContentPane().setLayout(new MigLayout("", "[470px]", "[1px]"));
		this.setLayout(new MigLayout());
		
		timeremaining = new JLabel();
		timeremaining.setText("Time left: " + 33 + " sec");
		this.add(timeremaining, "wrap");
		
		question = new JLabel();
		question.setText("How many Legs has a human?");
		this.add(question, "wrap");
		
		messageBox = new JTextField(40);
		messageBox.requestFocusInWindow();
		
		
		this.add(messageBox);

		insertAnswer = new JButton("Answer");
		insertAnswer.addActionListener(e -> activateReponseBehavior());

		
		this.add(insertAnswer);

		frame.getContentPane().add(this, "cell 0 0,growx,aligny top");
		frame.setVisible(true);
		
		
	}
	
	private void activateReponseBehavior(){
		if (messageBox.getText().length() < 1) {
			// do nothing
		}
	}
	
	private void setQuesion(String question){
		
		if(this.question != null){
			this.question.setText(question);
		}	
	}
	
	private void setTimeRemaining(String seconds){
		if(this.timeremaining != null)
		{
			timeremaining.setText(seconds);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
}
