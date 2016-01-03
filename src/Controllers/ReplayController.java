package Controllers;

import Managers.DataManager;
import Models.Game;
import Models.Replay;
import Models.ReplayThreeSixNine;
import Models.Round;
import Models.RoundType;
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
		model = new Replay(game.getId(), game.getPlayer1(), game.getPlayer2(), game.getGameState(), this);
		Round currentRound = new ReplayThreeSixNine(model, this);
		currentRoundController = new ThreeSixNineController(model, currentRound);
		model.setCurrentRound(currentRound);
		view.setRound(currentRoundController.getView());
		view.btnSubmit.addActionListener((e) -> model.getCurrentRound().onSubmit(model.getCurrentAnswer()));
		view.btnSubmit.addActionListener((e) -> model.getCurrentRound().onPass());
	}
	
	public void RoundEnd() {
		currentRoundController = model.getNextRound();
		if (currentRoundController == null) {
			EndReplay();
		} else {
			view.setRound(currentRoundController.getView());
		}
	}
	
	public void EndReplay() {
		// TODO: Show endresult?
		parent.ShowMainPanel();
	}
	
	public void ShowView() {
		parent.SetViewCategoryPanel(view);
	}
	
	public ReplayPanel GetView() {
		return view;
	}
}
