package Controllers;

import Managers.DataManager;
import Models.Game;
import Models.Replay;
import Models.ReplayThreeSixNine;
import Models.Round;
import View.ReplayPanel;

public class ReplayController {
	MainController parent;
	RoundController currentRoundController;
	ReplayPanel view;
	Replay model; 
	public ReplayController(int id, MainController parent) {
		this.parent = parent;
		view = new ReplayPanel(this);
		Game game = DataManager.getInstance().getGame(id);
		model = new Replay(game.getId(), game.getPlayer1(), game.getPlayer2(), game.getGameState());
		Utilities.ComponentUtility.addActionListener(view, "btnSubmit", (e) -> model.getCurrentRound().onSubmit(model.getCurrentAnswer()));
		Utilities.ComponentUtility.addActionListener(view, "btnPass", (e) -> model.getCurrentRound().onPass());
		Round currentRound = new ReplayThreeSixNine(model);
		currentRoundController = new ThreeSixNineController(model, currentRound);
		model.setCurrentRound(currentRound);
	}
	
	public void ShowView() {
		parent.SetViewCategoryPanel(view);
	}
	
	public ReplayPanel GetView() {
		return view;
	}
}
