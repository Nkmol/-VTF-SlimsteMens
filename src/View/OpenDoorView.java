package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import Models.Answer;
import Models.OpenDoor;
import Models.PlayerAnswer;
import Models.Question;
import Models.Turn;
import Utilities.StringUtility;

public class OpenDoorView extends JPanel implements Observer{

	private JTextArea questionTextArea;
	private JPanel answersPanel;
	private ArrayList<AnswerView> answerViews;
	
	public OpenDoorView(OpenDoor currentRound) {
		createOpenDoorPanel();
	}
	
	private void createOpenDoorPanel() {
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
				answersPanel.remove(asnwerView); // TODO asnwerview
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
	
	private void checkSubmittedAnswers(ArrayList<PlayerAnswer> submittedAnswers, OpenDoor openDoor) {
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
	public void update(Observable o, Object arg) {
		OpenDoor openDoor = (OpenDoor)arg;
		Turn currentTurn = openDoor.getCurrentTurn();
		System.out.println("question: " + openDoor.getCurrentTurn().getCurrentQuestion());
		
		Question currentQuestion = null;
		
		if (currentTurn.getSkippedQuestion() != null) 
			currentQuestion = currentTurn.getSkippedQuestion();
		else if (currentTurn.getCurrentQuestion() != null) 
			currentQuestion = currentTurn.getCurrentQuestion();
		
		if (currentQuestion != null) {
			questionTextArea.setText(currentQuestion.getText());
			placeAnswers(currentQuestion.getAnswers());
		}
		
		ArrayList<PlayerAnswer> submittedAnswers = openDoor.getSubmittedAnswers();
		
		if (submittedAnswers != null && submittedAnswers.size() > 0) {
			checkSubmittedAnswers(submittedAnswers, openDoor);
		}
		
	}

}
