package View;

import javax.swing.JPanel;
import javax.swing.JTextField;

import Controllers.PuzzleController;
import Models.Puzzle;

@SuppressWarnings("serial")
public class PuzzlePanel extends JPanel {

	private PuzzleController controller;
	
	private JTextField txtQuestion;
	
	public PuzzlePanel(PuzzleController controller) {
		this.controller = controller;
	}
	
	// Observer method for type safety
	public void Observe(Puzzle puzzle) {
		puzzle.addObserver((obs, o) -> Update((Puzzle)obs, o));
	}
	
	private void Update(Puzzle puzzle, Object o) {
		puzzle.getQuestions();
	}
}
