package Controllers;

import Models.ActiveGames;
import Models.ActivePlayers;
import Models.Player;
import View.ActiveGameListView;
import View.PlayerListView;

public class ActiveGameController {
	ActiveGameListView view;
	ActiveGames model;
	MainController parent;
	
	public ActiveGameController(MainController parent) {	
		this.parent = parent;
		view = new ActiveGameListView(this);
		model = new ActiveGames();
		model.addObserver(view);
	}
	
	public ActiveGameListView getView() {
		return view;
	}
}
