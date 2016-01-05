package Controllers;

import java.util.ArrayList;

import Managers.DataManager;
import Models.ActiveGames;
import Models.ActivePlayers;
import Models.Player;
import Models.Round;
import Models.RoundType;
import Models.ThreeSixNine;
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
		GameController gameController = new GameController(gameId, parent);
		parent.setGame(gameController.getView());
		if (gameController.getModel().getCurrentRound() instanceof ThreeSixNine) 
			((ThreeSixNine)gameController.getModel().getCurrentRound()).getCurrentTurn().startQuestionTimer(25);
		else 
			gameController.getModel().getCurrentRound().getCurrentTurn().startTimer();
		
	}

	public void handleButtonClick(int gameId) {
		startGame(gameId);
	}
	
	public void handleChatButtonClick(int gameId) {
		ChatController chatController = new ChatController(DataManager.getInstance().getGameInfoForGame(gameId));
		
		parent.SetViewCategoryPanel(chatController.returnView());
	}
}
