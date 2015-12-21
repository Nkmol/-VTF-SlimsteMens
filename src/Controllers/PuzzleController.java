package Controllers;

import Models.Game;
import Models.Puzzle;
import View.PuzzlePanel;

public class PuzzleController {
	
	private PuzzlePanel View;
	
	private Puzzle model;
	
	public PuzzleController(Game game) {
		View = new PuzzlePanel();
		model = new Puzzle(game);
	}
	

}
