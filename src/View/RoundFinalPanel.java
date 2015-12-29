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

import Models.Final;
import Models.ThreeSixNine;
import Models.Turn;
import net.miginfocom.swing.MigLayout;

public class RoundFinalPanel extends JPanel implements Observer {

	private JLabel question;
	private JLabel timeremaining;
	private JButton insertAnswer;
	private JTextField messageBox;
	private Final model;
	
	
	public RoundFinalPanel(Final model)
	{
		this.model = model;
		constructFinalRound();
	}

	private void constructFinalRound() {
		// TODO Auto-generated method stub

		this.setLayout(new MigLayout());
		
		timeremaining = new JLabel();
		timeremaining.setText("Time left: " + model.getCurrentTurn().getTime() + " sec");
		this.add(timeremaining, "wrap");
		
		question = new JLabel();
		//question.setText(model );//model.getQuestions().get(0).getText());
		this.add(question, "wrap");
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
		
		System.out.println("Hij komt in de update");
		
		Final model = (Final)arg1;
		Turn currentTurn = model.getCurrentTurn();
		System.out.println("question: " + model.getCurrentTurn().getCurrentQuestion());

		if (model.getCurrentTurn().getCurrentQuestion() != null) 
			question.setText(currentTurn.getCurrentQuestion().getText());
	}
	
}
