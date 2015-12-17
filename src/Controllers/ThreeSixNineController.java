package Controllers;

import Models.ThreeSixNine;
import Utilities.ComponentUtility;
import View.ThreeSixNineView;

public class ThreeSixNineController {

	private ThreeSixNine model;
	private ThreeSixNineView view;
	
	public ThreeSixNineController(ThreeSixNine currentRound, ThreeSixNineView currentRoundView){
		
		model = currentRound;
		view = currentRoundView;
		model.addObserver(view);
		
		ComponentUtility.addActionListener(view, "btnSendAnswer", (e) -> btnSendAnswer_Press());
		ComponentUtility.addActionListener(view, "btnPass", (e) -> btnPass_Press());
		
	}
	
	private void btnSendAnswer_Press(){
		
	}
	
	private void btnPass_Press(){
		
	}
	
	public ThreeSixNineView getView(){
		return view;
	}
	
}
