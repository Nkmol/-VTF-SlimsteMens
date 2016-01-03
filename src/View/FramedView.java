package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Managers.DataManager;
import Models.Answer;
import Models.Framed;
import Models.OpenDoor;
import Models.PlayerAnswer;
import Models.Question;
import Models.RoundType;
import Models.Turn;
import Utilities.StringUtility;

public class FramedView extends JPanel implements Observer{

	private JPanel container;
	private JTextArea questionTextArea;
	private FramedAnswerView[][] framedAnswerViews;
	private int index;
	private int currentQuestionId = 0;
	private Boolean loaded;
	
	public FramedView(Framed currentRound) {
		createRoundFourPanel();
		loaded = false;
		index = 0;
	}
	
	private void createRoundFourPanel() {
		setLayout(new BorderLayout());
		
		container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		add(container, BorderLayout.CENTER);
		
		JPanel questionView = new JPanel();
		questionView.setLayout(new BorderLayout());
		questionView.setBackground(new Color(193,212,255));
		questionView.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JPanel answersView = new JPanel();
		answersView.setLayout(new GridBagLayout());
		
		container.add(questionView, BorderLayout.NORTH);
		container.add(answersView, BorderLayout.CENTER);
		
		questionTextArea = new JTextArea();
		textAreaProperties(questionTextArea);
		questionTextArea.setMargin(new Insets(5,5,5,5));
		
		questionView.add(questionTextArea, BorderLayout.CENTER);
		
		framedAnswerViews = new FramedAnswerView[2][5];
		
		for(int x = 0; x < 2; x++) {
			for(int y = 0; y < 5; y++) {
				
				FramedAnswerView framedAnswerView = new FramedAnswerView("00", "-");
				framedAnswerViews[x][y] = framedAnswerView;
				
				GridBagConstraints c = new GridBagConstraints();
				c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 2;
				c.gridx = x;
				c.gridy = y;
				
				answersView.add(framedAnswerView, c);
			}
		}
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
	
	private void addAnswer(Answer answer, String points) {
		int x;
		int y;
		
		if(index < 5) {
			x = 0;
			y = index;
		}
		else {
			x = 1;
			y = index - 5;
		}
		
		framedAnswerViews[x][y].setAnswer(answer);
		framedAnswerViews[x][y].setPoints(points);
		
		index++;
		
	}
	
	private void addAnswerArray(ArrayList<Answer> answers) {
		for(int i = 0; i < answers.size(); i++) {
			addAnswer(answers.get(i), "20");
		}
	}
	
	private void checkAnswer(ArrayList<PlayerAnswer> submittedAnswers) {
		for (PlayerAnswer submittedAnswer : submittedAnswers) {
			for (FramedAnswerView[] framedAnswerViewCollection : framedAnswerViews) {
				for (FramedAnswerView framedAnswerView : framedAnswerViewCollection) {
					if (isPlayerAnswerCorrect(submittedAnswer, framedAnswerView.getAnswer())) {
						framedAnswerView.revealAnswer();
					}
				}
			}
		}
	}
	
	private boolean isPlayerAnswerCorrect(PlayerAnswer playerAnswer, Answer answer) {
		if (StringUtility.CalculateMatchPercentage(playerAnswer.getAnswer(), answer.getAnswer()) >=  80)
			return true;

		for (String alternative : answer.getAlternatives())
			if (StringUtility.CalculateMatchPercentage(playerAnswer.getAnswer(), alternative) >=  80)
				return true;

		return false;
	}
	
	private ArrayList<PlayerAnswer> calculatePlayerAnswersForTurn(Framed framed) {
		ArrayList<PlayerAnswer> playerAnswers = new ArrayList<PlayerAnswer>();
		
		ArrayList<Turn> allTurnsForRound = DataManager.getInstance().getTurnsForQuestion(framed, framed.getCurrentTurn().getCurrentQuestion());
		
		for(int i = 0; i < allTurnsForRound.size(); i++) {
			Turn turn = allTurnsForRound.get(i);
			if(turn.getCurrentQuestion().getText().equals(framed.getCurrentTurn().getCurrentQuestion().getText())) {
				playerAnswers.addAll(turn.getPlayerAnswers());
			}
		}
		
		return playerAnswers;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		Framed framed = (Framed)arg;
		Turn currentTurn = framed.getCurrentTurn();
		Question currentQuestion = null;
		
		if (currentTurn.getCurrentQuestion() != null) {
			currentQuestion = currentTurn.getCurrentQuestion();
			System.out.println("question: " + currentQuestion.getId());
		}
		
		if (currentQuestion != null) 
			questionTextArea.setText(currentQuestion.getText());
			
		
		if (currentQuestion != null) {
			if(currentQuestionId != currentQuestion.getId()) {
				questionTextArea.setText(currentQuestion.getText());
				ArrayList<Answer> answers = currentQuestion.getAnswers();
				addAnswerArray(answers);
				currentQuestionId = currentQuestion.getId();
				index = 0;
			}
		}
		
		if(currentQuestion != null) {
			ArrayList<PlayerAnswer> playerAnswers = DataManager.getInstance().getPlayerAnswers(framed.getGame().getId(), RoundType.Framed, framed.getCurrentTurn().getTurnId());
			checkAnswer(playerAnswers);
		}
		
		if(!loaded) {
			ArrayList<PlayerAnswer> submittedAnswers = calculatePlayerAnswersForTurn(framed);
			
			if (submittedAnswers != null && submittedAnswers.size() > 0) {
				checkAnswer(submittedAnswers);
			}
			
			loaded = true;
		}
		
	}
}
