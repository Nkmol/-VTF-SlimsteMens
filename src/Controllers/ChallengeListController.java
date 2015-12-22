package Controllers;

import javax.swing.JFrame;

import Models.ActiveChallenges;
import Models.ActivePlayers;
import View.ChallengeListView;
import View.ChallengeView;
import View.PlayerListView;

public class ChallengeListController {

	MainController parent;
	ChallengeListView view;
	ActiveChallenges model;
	
	
	public ChallengeListController(MainController parent) {
		this.parent = parent;
		view = new ChallengeListView(this);
		model = new ActiveChallenges();
		model.addObserver(view);
	}
	
	public ChallengeListView getView() {
		return view;
	}
	
	public void startGame(int gameId) {
		parent.SetViewCategoryPanel(new GameController(gameId).getView());
	}
	
	public void handleAcceptButtonClick(int gameId) {
		model.acceptChallenge(gameId);
		startGame(gameId);
	}
	
	public void handleRejectButtonClick(int gameId) {
		model.rejectChallenge(gameId);
	}
	
	
}
