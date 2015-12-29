package Controllers;

import javax.swing.JPanel;

import Models.Final;
import Models.Game;
import Models.OpenDoor;
import Models.Round;
import View.OpenDoorView;
import View.RoundFinalPanel;

public class FinalController implements RoundController {

	private Final model;
	private RoundFinalPanel view;
	
	public FinalController(Game parent) {
		model = new Final(parent);
		view = new RoundFinalPanel(model);
		model.addObserver(view);
		model.updateView();
	}
	
	public FinalController(Game parent, Round round) {
		model = (Final) round;
		view = new RoundFinalPanel(model);
		model.addObserver(view);
		model.updateView();
	}

	@Override
	public JPanel getView() {
		// TODO Auto-generated method stub
		return view;
	}

	@Override
	public Round getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	
}
