package Controllers;

import Models.ActivePlayers;
import Models.Player;
import View.PlayerListView;

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
	
	public void handleButtonClick(Player player2) {
		model.sendChallenge(player2);
	}
}
