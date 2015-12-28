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
		model.updateView();
	}
	
	public OpenDoorController(Game parent, Round round) {
		model = (OpenDoor) round;
		view = new OpenDoorView(model);
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
