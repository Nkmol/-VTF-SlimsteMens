package Controllers;

import Models.Game;
import Models.ThreeSixNine;
import Utilities.ComponentUtility;
import View.ThreeSixNineView;

public class ThreeSixNineController {

	private ThreeSixNine model;
	private ThreeSixNineView view;
	
	public ThreeSixNineController(Game parent) {
		
		model = new ThreeSixNine(parent);
		view = new ThreeSixNineView(model);
		model.addObserver(view);
		model.updateView();
	}
	
	public ThreeSixNineView getView(){
		return view;
	}
	
	public ThreeSixNine getModel(){
		return model;
	}
	
}
