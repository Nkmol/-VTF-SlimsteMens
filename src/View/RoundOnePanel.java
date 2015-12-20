package View;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class RoundOnePanel extends JPanel implements Observer {

	// Player answers 9 question in this round
	
	private JFrame frame;
	private JLabel question;
	private JLabel timeremaining;
	private JButton insertAnswer;
	private JTextField messageBox;
	
	public RoundOnePanel()
	{
		createRoundOnePanel();
	}

	
	private void createRoundOnePanel()
	{
		frame = new JFrame();
		frame.setSize(500, 555);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[470px]", "[1px]"));
		this.setLayout(new MigLayout());
		
		timeremaining = new JLabel();
		timeremaining.setText("Time left: " + 33 + " sec");
		this.add(timeremaining, "wrap");
		
		question = new JLabel();
		question.setText("How many feathers has a bird");
		this.add(question, "wrap");
		
		messageBox = new JTextField(40);
		messageBox.requestFocusInWindow();
		
		
		this.add(messageBox);

		insertAnswer = new JButton("Answer");
		//insertAnswer.addActionListener(e -> activateReponseBehavior());

		
		this.add(insertAnswer);

		frame.getContentPane().add(this, "cell 0 0,growx,aligny top");
		frame.setVisible(true);
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
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
