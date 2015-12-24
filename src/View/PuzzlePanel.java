package View;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Controllers.PuzzleController;
import Models.Answer;
import Models.Puzzle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class PuzzlePanel extends JPanel {
	private JLabel[] lblQuestionPositions;
	private JLabel[] lblQuestion;
	
	private JPanel AnswersPanel;
	private JPanel CenterPanel;
	
	public PuzzlePanel(PuzzleController controller) {
		super(new BorderLayout());
		
		final int QuestionAmount = Puzzle.getQuestionAmount();
		
		CenterPanel = new JPanel(new GridLayout(Puzzle.getQuestionAmount(), Puzzle.getAnswerPerQuestionAmount()));
		CenterPanel.setBackground(Color.GREEN);
		add(CenterPanel, BorderLayout.CENTER);
		
		AnswersPanel = new JPanel(new GridLayout(QuestionAmount, 2));
		lblQuestionPositions = new JLabel[QuestionAmount];
		lblQuestion = new JLabel[QuestionAmount];
		for (int I = 0; I < QuestionAmount; I++) {
			lblQuestionPositions[I] = new JLabel('A' + I + ":");
			AnswersPanel.add(lblQuestionPositions[I]);
			lblQuestion[I] = new JLabel();
			AnswersPanel.add(lblQuestion[I]);
		}
		add(AnswersPanel, BorderLayout.PAGE_END);
	}
	
	// Observer method for type safety
	public void Observe(Puzzle puzzle) {
		puzzle.addObserver((obs, o) -> Update((Puzzle)obs));
		Update(puzzle);
	}
	
	private void Update(Puzzle puzzle) {	
		CenterPanel.removeAll();
		for (Answer answer : puzzle.GetPossibleAnswers()) 
			CenterPanel.add(new JLabel(answer.getAnswer()));
		for (int I = 0; I < lblQuestion.length; I++)
			lblQuestion[I].setText((puzzle.getPuzzleQuestions()[I] != null) ? puzzle.getPuzzleQuestions()[I].getText() : "...");
		RefreshView();
	}
	
	public void RefreshView() {
		revalidate();
		repaint();
	}
}
