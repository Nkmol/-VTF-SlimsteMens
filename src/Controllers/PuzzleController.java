package Controllers;

import javax.swing.JPanel;

import Models.Game;
import Models.OpenDoor;
import Models.Puzzle;
import Models.Round;
import View.OpenDoorView;
import View.PuzzlePanel;

public class PuzzleController{
	
	private PuzzlePanel view;
	
	private Puzzle model;
	
	public PuzzleController(Game parent, Round round) {
		model = (Puzzle)round;
		view = new PuzzlePanel(this);
		view.Observe(model);
	}
	
	public JPanel GetView() {
		return view;
	}
}
