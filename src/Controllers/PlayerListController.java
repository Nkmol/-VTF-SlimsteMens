package Controllers;

import javax.swing.JFrame;

import Models.ActivePlayers;
import View.ChallengeView;
import View.PlayerListView;
import View.PlayerView;

public class PlayerListController {
	PlayerListView view;
	ActivePlayers model;
	MainController parent;
	
	public PlayerListController(MainController parent) {	
		this.parent = parent;
		view = new PlayerListView(this);
		model = new ActivePlayers();
		model.addObserver(view);
	}
	
	public PlayerListView getView() {
		return view;
	}
	
	public void handleButtonClick(String playername) {
		model.sendChallenge(playername);
	}
}
