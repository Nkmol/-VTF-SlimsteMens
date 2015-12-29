package Controllers;

import javax.swing.JPanel;

import Models.Game;
import Models.OpenDoor;
import Models.Round;
import View.OpenDoorView;

public class OpenDoorController implements RoundController {

	private OpenDoor model;
	private OpenDoorView  view;
	
	public OpenDoorController(Game parent) {
		model = new OpenDoor(parent);
		view = new OpenDoorView(model);
		model.addObserver(view);
	}
	
	public OpenDoorController(Game parent, Round round) {
		model = (OpenDoor) round;
		view = new OpenDoorView(model);
		model.addObserver(view);
	}

	@Override
	public JPanel getView() {
		return view;
	}

	@Override
	public Round getModel() {
		return model;
	}
}
