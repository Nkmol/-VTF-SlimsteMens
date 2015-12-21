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
		view = new ThreeSixNineView();
		model.addObserver(view);
		model.updateView();
		
		ComponentUtility.addActionListener(view, "btnSendAnswer", (e) -> btnSendAnswer_Press());
		ComponentUtility.addActionListener(view, "btnPass", (e) -> btnPass_Press());
		
	}
	
	private void btnSendAnswer_Press(){
		
		model.isAnswerCorrect(view.getCurrentQuestion().getId(), view.getAnswer(), view.getCurrentQuestion().getAnswers());
	}
	
	private void btnPass_Press(){
		
		// insert pass 
		
	}
	
	public ThreeSixNineView getView(){
		return view;
	}
	
	public ThreeSixNine getModel(){
		return model;
	}
	
}
