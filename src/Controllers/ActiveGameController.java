package Controllers;

import java.util.ArrayList;

import Managers.DataManager;
import Models.ActiveGames;
import Models.Round;
import Models.RoundType;
import View.ActiveGameListView;

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
	
		ArrayList<Round> rounds = DataManager.getInstance().getRounds(gameController.getModel());
		
		for(int i = 0; i < rounds.size(); i++) {
			//set default round
			gameController.addRound(rounds.get(i).getRoundType());
		}
		
		parent.SetViewCategoryPanel(gameController.getView());
	}
	
	
	public void handleButtonClick(int gameId) {
		startGame(gameId);
	}
}
