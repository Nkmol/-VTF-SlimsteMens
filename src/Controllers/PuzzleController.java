package Controllers;

import Models.Game;
import Models.Puzzle;
import View.PuzzlePanel;

public class PuzzleController {
	
	private PuzzlePanel view;
	
	private Puzzle model;
	
	public PuzzleController(Game game) {
		view = new PuzzlePanel(this);
		model = new Puzzle(game);
		view.Observe(model);
	}
	
	public PuzzlePanel GetView() {
		return view;
	}
}
