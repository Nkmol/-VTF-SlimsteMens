package Controllers;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.text.View;

import Models.ActiveChallenges;
import Models.ActivePlayers;
import Utilities.ComponentUtility;
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
		//model.acceptChallenge(gameId); //TODO: ...
		startGame(gameId);
	}
	
	public void handleRejectButtonClick(int gameId) {
		model.rejectChallenge(gameId);
	}
	
	public void setRejectButtonClick(ChallengeView view, ActionListener l) {
		ComponentUtility.addActionListener(view, "declineButton", l);
	}
	
}
