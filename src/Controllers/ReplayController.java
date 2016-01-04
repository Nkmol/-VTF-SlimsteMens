package Controllers;

import Managers.DataManager;
import Models.Game;
import Models.Replay;
import Models.ReplayThreeSixNine;
import Models.Round;
import Models.RoundType;
import View.ReplayPanel;

public class ReplayController {
	private MainController parent;
	private RoundController currentRoundController;
	private ReplayPanel view;
	private Replay model;
	private Round currentRound;
	public ReplayController(int id, MainController parent) {
		this.parent = parent;
		view = new ReplayPanel(this);
		Game game = DataManager.getInstance().getGame(id);
		model = new Replay(game.getId(), game.getPlayer1(), game.getPlayer2(), game.getGameState(), this);
		Round currentRound = new ReplayThreeSixNine(model, this);
		currentRoundController = new ThreeSixNineController(model, currentRound);
		
		model.setCurrentRound(currentRound);
		view.setRound(currentRoundController.getView());
		view.btnSubmit.addActionListener((e) -> OnSubmit());
		view.btnPass.addActionListener((e) -> OnPass());	
		model.addObserver(view);
		model.updateView();
	}
	
	public void OnSubmit() {
		model.getCurrentRound().onSubmit(model.getCurrentAnswer());
	}
	
	public void OnPass() {
		model.getCurrentRound().onPass();
	}
	
	public void RoundEnd() {
		currentRoundController = model.getNextRound();
		if (currentRoundController == null) {
			EndReplay();
		} else {
			view.btnSubmit.addActionListener((e) -> model.getCurrentRound().onSubmit(model.getCurrentAnswer()));
			view.btnPass.addActionListener((e) -> model.getCurrentRound().onPass());
			view.setRound(currentRoundController.getView());
			view.revalidate();
			view.repaint();
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
