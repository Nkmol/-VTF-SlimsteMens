package View;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controllers.PuzzleController;
import Models.Puzzle;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class PuzzlePanel extends JPanel {

	private PuzzleController controller;
	
	private JTextField txtQuestion;
	
	private JLabel[] lblAnswerPositions;
	private JLabel[] lblAnswers;
	
	public PuzzlePanel(PuzzleController controller) {
		this.controller = controller;
		setLayout(new GridLayout(5, 5));
		txtQuestion = new JTextField();
		int AnswerAmount = 3;
		lblAnswerPositions = new JLabel[AnswerAmount];
		lblAnswers = new JLabel[AnswerAmount];
		for (int I = 0; I < AnswerAmount; I++) {
			lblAnswerPositions[I] = new JLabel('A' + I + ":");
			lblAnswers[I] = new JLabel();
		}
		
	}
	
	// Observer method for type safety
	public void Observe(Puzzle puzzle) {
		puzzle.addObserver((obs, o) -> Update((Puzzle)obs));
		Update(puzzle);
	}
	
	private void Update(Puzzle puzzle) {
		txtQuestion.setText(puzzle.getTurn().getQuestion().getText());
		
	}
}
