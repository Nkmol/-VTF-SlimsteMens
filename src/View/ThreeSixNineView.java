package View;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controllers.ThreeSixNineController;
import Models.Question;
import Models.ThreeSixNine;
import net.miginfocom.swing.MigLayout;

public class ThreeSixNineView extends JPanel implements Observer {

	private final static String BTN_ANSWER = "Answer",
								BTN_PASS = "Pass";
								
	
	// Player answers 9 question in this round
	// Player can pass 
	
	
	private JFrame frame;
	private JLabel question,
				   timeRemainingPlayer1,
				   timeRemainingPlayer2,
				   rightAnswer,
				   isQuestionCorrect;
	private JButton btnSendAnswer,
					btnPass;
	private JTextField txtAnswer;
	private ThreeSixNine currentRound;
	private ArrayList<Question> questions;
	private Question currentQuestion;
	
	
	
	public ThreeSixNineView(ThreeSixNine currentRound)
	{
		/*this.currentRound = currentRound;
		this.questions = currentRound.getQuestions();*/
		add(new JLabel("test"));
		//createRoundOnePanel();
	}

	
	private void createRoundOnePanel()
	{
		this.setLayout(new MigLayout());
	
		timeRemainingPlayer1 = new JLabel();
		timeRemainingPlayer1.setText("Time left: " + 33 + " sec");
		this.add(timeRemainingPlayer1, "wrap");
		
		//currentQuestion = currentRound.getCurrentQuestion();
		question = new JLabel("test question");
		/*question.setText(currentQuestion.getText());*/
		this.add(question, "wrap");
		
		txtAnswer = new JTextField(40);
		txtAnswer.requestFocusInWindow();
		this.add(txtAnswer);

		btnSendAnswer = new JButton(ThreeSixNineView.BTN_ANSWER);
		this.add(btnSendAnswer);
		
		btnPass = new JButton(ThreeSixNineView.BTN_PASS);
		this.add(btnPass);
	}

	private void setQuestion(Question question2){
		
		if(this.question != null){
			this.question.setText(question2.getText());
		}	
	}
	
	private void setTimeRemaining(String seconds){
		if(this.timeRemainingPlayer1 != null)
		{
			timeRemainingPlayer1.setText(seconds);
		}
	}
	
	private void setRightAnswer(String answer)
	{
		if(this.timeRemainingPlayer1 != null)
		{
			rightAnswer.setText(answer);
		}
	}
	
	public void setPlayerNames(String player1, String player2)
	{
		
	}

	public String getAnswer(){
		
		if(txtAnswer.getText().length() > 0)
		return txtAnswer.getText();
		
		return "Error: No input";
	}
	
	public Question getCurrentQuestion()
	{
		return currentQuestion;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		ThreeSixNine threesixnine = (ThreeSixNine)arg;
		
		// vraag instellen
		//setQuestion(threesixnine.getCurrentQuestion());
		
		// de punten aanpasssen
		
		
	}
}
