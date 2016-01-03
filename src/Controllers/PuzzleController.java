package Controllers;

import javax.swing.JPanel;

import Models.Framed;
import Models.Game;
import Models.Puzzle;
import Models.Round;
import View.FramedView;
import View.PuzzlePanel;
import View.PuzzleView;

public class PuzzleController implements RoundController{

	private Puzzle model;
	private PuzzleView view;
	
	public PuzzleController(Game parent) {
		
		model = new Puzzle(parent);
		view = new PuzzleView(model);
		model.addObserver(view);
	}
	
	public PuzzleController(Game parent, Round round) {
		
		model = (Puzzle) round;
		view = new PuzzleView(model);
		model.addObserver(view);
	}

	@Override
	public PuzzleView getView() {
		// TODO Auto-generated method stub
		return view;
	}

	@Override
	public Puzzle getModel() {
		// TODO Auto-generated method stub
		return model;
	}
}
