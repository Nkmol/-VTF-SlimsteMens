package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Models.Answer;
import Models.Framed;
import Models.PlayerAnswer;
import Models.Puzzle;
import Models.Question;
import Models.SharedQuestion;
import Utilities.StringUtility;

public class PuzzleView extends JPanel implements Observer {

	private final Dimension d = new Dimension(4, 3);
	private JPanel container;
	private JTextArea questionTextArea;
	private FramedAnswerView[][] framedAnswerViews;
	private int index;
	private int currentQuestionId = 0;
	PuzzleAnswerView[] puzzleAnswerViews;
	
	public PuzzleView(Puzzle currentRound) {
		createRoundThreePanel();
		
		index = 0;
	}
	
	private void createRoundThreePanel() {
		setLayout(new BorderLayout());
		
		container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		add(container, BorderLayout.CENTER);
		
		JPanel questionView = new JPanel();
		questionView.setLayout(new GridBagLayout());
		questionView.setBackground(new Color(193,212,255));
		questionView.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		
		JPanel answersView = new JPanel();
		answersView.setLayout(new GridBagLayout());
		
		container.add(questionView, BorderLayout.NORTH);
		container.add(answersView, BorderLayout.CENTER);
		
		ArrayList<PuzzleQuestionView> questionPanels = new ArrayList<PuzzleQuestionView>();
		
		for(int x = 0; x < d.width; x++) {
			for(int y = 0; y < d.height; y++) {
				
				PuzzleQuestionView questionPanel = new PuzzleQuestionView("Vraag");
				
				GridBagConstraints c = new GridBagConstraints();
				c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 2;
				c.gridx = x;
				c.gridy = y;
				
				questionView.add(questionPanel, c);
				questionPanels.add(questionPanel);
			}
		}
		
		PuzzleQuestionView[][] answerQuestions = new PuzzleQuestionView[d.height][d.width];
		
		int temp = questionPanels.size();
		
		for(int i = 0; i < temp; i++) {
			Random r = new Random();
			int index = r.nextInt(questionPanels.size());
			if (i < 4) {
				answerQuestions[0][i] = questionPanels.get(index);
				questionPanels.remove(index);
			}
			else if (i < 8) {
				answerQuestions[1][i - 4] = questionPanels.get(index);
				questionPanels.remove(index);
			}
			else if (i < 12) {
				answerQuestions[2][i - 8] = questionPanels.get(index);
				questionPanels.remove(index);
			}
		}
		
		puzzleAnswerViews = new PuzzleAnswerView[Puzzle.AMOUNT_QUESTIONS];
		
		for(int y = 0; y < d.height; y++) {
			
			PuzzleAnswerView puzzleAnswerView = new PuzzleAnswerView(""+Puzzle.CORRECT_POINTS, "-");
			puzzleAnswerView.setQuestionViews(answerQuestions[y]);
			puzzleAnswerViews[y] = puzzleAnswerView;
			
			GridBagConstraints c = new GridBagConstraints();
			c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 2;
			c.gridx = 1;
			c.gridy = y;
			
			answersView.add(puzzleAnswerView, c);
		}
	}
	
	private void addAnswers(ArrayList<Answer> answers, Question question, Color color) {
		puzzleAnswerViews[index].setAnswer(question);
		puzzleAnswerViews[index].fillQuestionViews(answers, color);
		index++;
	}
	
	private void revealAnswer(Question question) {
		for(int i = 0; i < 3; i++) {
			if (puzzleAnswerViews[i].getAnswer().getText().equals(question.getText())) {
				puzzleAnswerViews[i].revealAnswer();
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Puzzle puzzle = (Puzzle)arg;
		
		ArrayList<SharedQuestion> sharedQuestions = puzzle.getCurrentTurn().getSharedQuestions();
		
		for(SharedQuestion sharedQuestion : sharedQuestions)
			addAnswers(sharedQuestion.getAnswers(), sharedQuestion, Color.red);
		index = 0;
	}
}
