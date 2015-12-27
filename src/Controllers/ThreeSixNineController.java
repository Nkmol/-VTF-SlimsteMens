package Controllers;

import Models.Game;
import Models.Round;
import Models.ThreeSixNine;
import Utilities.ComponentUtility;
import View.ThreeSixNineView;

public class ThreeSixNineController implements RoundController {

	private ThreeSixNine model;
	private ThreeSixNineView view;
	
	public ThreeSixNineController(Game parent) {
		
		model = new ThreeSixNine(parent);
		view = new ThreeSixNineView(model);
		model.addObserver(view);
	}
	
	public ThreeSixNineController(Game parent, Round round) {
		
		model = (ThreeSixNine) round;
		view = new ThreeSixNineView(model);
		model.addObserver(view);
	}

	@Override
	public ThreeSixNineView getView() {
		// TODO Auto-generated method stub
		return view;
	}

	@Override
	public ThreeSixNine getModel() {
		// TODO Auto-generated method stub
		return model;
	}
}
