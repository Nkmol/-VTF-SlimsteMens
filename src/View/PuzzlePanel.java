package View;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controllers.PuzzleController;
import Models.Puzzle;

import java.awt.BorderLayout;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class PuzzlePanel extends JPanel {
	
	private JTextField txtQuestion;
	
	private JLabel[] lblAnswerPositions;
	private JLabel[] lblAnswers;
	
	private JPanel AnswersPanel;
	
	public PuzzlePanel(PuzzleController controller) {
		super(new BorderLayout());
		
		final int AnswerAmount = Puzzle.getAnswerAmount();
		
		add(txtQuestion, BorderLayout.CENTER);
		
		AnswersPanel = new JPanel(new GridLayout(AnswerAmount, 2));
		lblAnswerPositions = new JLabel[AnswerAmount];
		lblAnswers = new JLabel[AnswerAmount];
		for (int I = 0; I < AnswerAmount; I++) {
			lblAnswerPositions[I] = new JLabel('A' + I + ":");
			AnswersPanel.add(lblAnswerPositions[I]);
			lblAnswers[I] = new JLabel();
			AnswersPanel.add(lblAnswers[I]);
		}
		add(AnswersPanel, BorderLayout.PAGE_END);
	}
	
	// Observer method for type safety
	public void Observe(Puzzle puzzle) {
		puzzle.addObserver((obs, o) -> Update((Puzzle)obs));
		Update(puzzle);
	}
	
	private void Update(Puzzle puzzle) {
		// TODO: Does puzzle only have one question?	
		txtQuestion.setText(puzzle.GetQuestion().getText());
		
		for (int I = 0; I < lblAnswers.length || I < puzzle.getCurrentTurn().getPlayerAnswers().size(); I++)
			lblAnswers[I].setText(puzzle.getCurrentTurn().getPlayerAnswers().get(I).getAnswer());

		RefreshView();
	}
	
	public void RefreshView() {
		revalidate();
		repaint();
	}
}
