package View;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Models.Answer;
import Models.Final;
import Models.OpenDoor;
import Models.PlayerAnswer;
import Models.Question;
import Models.ThreeSixNine;
import Models.Turn;
import Utilities.StringUtility;
import net.miginfocom.swing.MigLayout;

public class RoundFinalPanel extends JPanel implements Observer {

/*	private JLabel question;
	private JLabel timeremaining;
	private JButton insertAnswer;
	private JTextField messageBox;
	private Final model;*/
	
	private JTextArea questionTextArea;
	private JPanel answersPanel;
	private ArrayList<AnswerView> answerViews;
	
	public RoundFinalPanel(Final model)
	{
		//this.model = model;
		constructFinalRound();
	}

	private void constructFinalRound() {
		// TODO Auto-generated method stub

		setLayout(new BorderLayout());
		
		questionTextArea = new JTextArea();
		textAreaProperties(questionTextArea);
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add(questionTextArea, BorderLayout.NORTH);
		add(container, BorderLayout.NORTH);
		
		answersPanel = new JPanel();
		answersPanel.setLayout(new GridBagLayout());
		add(answersPanel, BorderLayout.CENTER);
	}
	
	private JTextArea textAreaProperties(JTextArea textArea) {
	    textArea.setEditable(false);  
	    textArea.setCursor(null);  
	    textArea.setOpaque(false);  
	    textArea.setFocusable(false);
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    return textArea;
	}
	
	private void placeAnswers(ArrayList<Answer> answers) {
		
		if (answerViews == null)
			answerViews = new ArrayList<>();
		
		if (answerViews.size() > 0) {
			for (AnswerView asnwerView : answerViews) 
				answersPanel.remove(asnwerView);
		}
		
		answerViews = new ArrayList<>();
		int index = 0;
		GridBagConstraints c = new GridBagConstraints();
		
		for (Answer answer : answers) {
			AnswerView answerView = new AnswerView(answer);
			answerView.hideAnswer();
			answerViews.add(answerView);
			c.gridx = 0;
			c.gridy = 1 * index;
			c.insets = new Insets(2, 2, 2, 2);
			c.anchor = GridBagConstraints.WEST;
			c.fill = GridBagConstraints.HORIZONTAL;
			answersPanel.add(answerView,c);
			index++;
		}
		
		repaint();
	}
	
	private void checkSubmittedAnswers(ArrayList<PlayerAnswer> submittedAnswers, Final model) {
		for (PlayerAnswer submittedAnswer : submittedAnswers) {
			for (AnswerView answerView : answerViews) {
				if (playerAnswerCorrect(submittedAnswer, answerView.getAnswer()))
					answerView.unhideAnswer();

			}
		}
	}
	
	private boolean playerAnswerCorrect(PlayerAnswer playerAnswer, Answer answer) {
		if (StringUtility.CalculateMatchPercentage(playerAnswer.getAnswer(), answer.getAnswer()) >=  80)
			return true;

		for (String alternative : answer.getAlternatives())
			if (StringUtility.CalculateMatchPercentage(playerAnswer.getAnswer(), alternative) >=  80)
				return true;

		return false;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
		//System.out.println("Hij komt in de update");
		
		Final model = (Final)arg1;
		Turn currentTurn = model.getCurrentTurn();
		System.out.println("question: " + model.getCurrentTurn().getCurrentQuestion());
		
		Question currentQuestion = model.getCurrentTurn().getCurrentQuestion();
		
		if (model.getCurrentTurn().getCurrentQuestion() != null) 
			questionTextArea.setText(currentTurn.getCurrentQuestion().getText());
		
		if (currentQuestion != null) {
			questionTextArea.setText(currentQuestion.getText());
			placeAnswers(currentQuestion.getAnswers());
		}
		
		ArrayList<PlayerAnswer> submittedAnswers = model.getSubmittedAnswers();
		
		if (submittedAnswers != null && submittedAnswers.size() > 0) {
			checkSubmittedAnswers(submittedAnswers, model);
		}
	}
	
	
	
}
