package Controllers;

import Models.Framed;
import Models.Game;
import Models.Round;
import View.FramedView;

public class FramedController implements RoundController{

	private Framed model;
	private FramedView view;
	
	public FramedController(Game parent) {
		
		model = new Framed(parent);
		view = new FramedView(model);
		model.addObserver(view);
	}
	
	public FramedController(Game parent, Round round) {
		
		model = (Framed) round;
		view = new FramedView(model);
		model.addObserver(view);
	}

	@Override
	public FramedView getView() {
		// TODO Auto-generated method stub
		return view;
	}

	@Override
	public Framed getModel() {
		// TODO Auto-generated method stub
		return model;
	}
}
