package Controllers;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import Models.Game;
import Models.Puzzle;
import Utilities.ComponentUtility;
import View.PuzzlePanel;

public class PuzzleController {
	
	private PuzzlePanel view;
	
	private Puzzle model;
	
	public PuzzleController(Game game, GameController parent) {
		view = new PuzzlePanel(this);
		model = new Puzzle(game);
		view.Observe(model);
		ComponentUtility.addActionListener(parent.getView(), "btnSubmit", this::OnSubmit);
	}
	
	private void OnSubmit(ActionEvent e) {
		JButton source = (JButton)e.getSource();
		String submitted = source.getText();
		
		model.Submit(submitted);
	}
	
	public PuzzlePanel GetView() {
		return view;
	}
}
