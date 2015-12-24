package Controllers;

import java.util.ArrayList;

import Managers.DataManager;
import Models.ActiveGames;
import Models.ActivePlayers;
import Models.Player;
import Models.Round;
import Models.RoundType;
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
	
	public void startGame(int gameId) {
		GameController gameController = new GameController(gameId);

		
		//set default round
		gameController.addRound(RoundType.ThreeSixNine);
		
		
		// TODO TEST PURPOSES FOR FINAL ROUND
		//gameController.addRound(RoundType.Final);
		
		
		parent.SetViewCategoryPanel(gameController.getView());
	}
	
	public void handleButtonClick(int gameId) {
		startGame(gameId);
	}
}
