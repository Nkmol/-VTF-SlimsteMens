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
		view = new ChallengeListView();
		model = new ActiveChallenges();
		model.addObserver(view);
	}
	
	public ChallengeListView getView() {
		return view;
	}
	
	
}
