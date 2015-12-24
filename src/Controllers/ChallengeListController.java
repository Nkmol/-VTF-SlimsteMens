package Controllers;

import javax.swing.JFrame;

import Models.ActiveChallenges;
import Models.ActivePlayers;
import Models.Game;
import Models.Round;
import Models.RoundType;
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
		GameController gameController = new GameController(gameId, parent);
	
		//set default round
		gameController.addRound(RoundType.ThreeSixNine);
		
		parent.SetViewCategoryPanel(gameController.getView());
	}
	
	public void handleAcceptButtonClick(int gameId) {
		model.acceptChallenge(gameId);

		startGame(gameId);
		System.out.println(gameId);
	}
	
	public void handleRejectButtonClick(int gameId) {
		model.rejectChallenge(gameId);
	}
	
	
}
