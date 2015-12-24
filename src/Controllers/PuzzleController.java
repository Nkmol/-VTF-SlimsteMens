package Controllers;

import javax.swing.JPanel;

import Models.Game;
import Models.Puzzle;

import View.PuzzlePanel;

public class PuzzleController{
	
	private PuzzlePanel view;
	
	private Puzzle model;
	
	public PuzzleController(Game game, GameController parent) {
		view = new PuzzlePanel(this);
		model = new Puzzle(game);
		view.Observe(model);
	}
	
	public JPanel GetView() {
		return view;
	}
}
