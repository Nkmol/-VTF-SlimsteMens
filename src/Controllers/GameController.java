package Controllers;

import javax.swing.JPanel;

import Managers.DataManager;
import Models.Game;
import View.GamePanel;

public class GameController {
	
	private Game model;
	private GamePanel view;
	
	public GameController(int gameId) {
		model = DataManager.getInstance().getGame(gameId);
		view = new GamePanel();
		model.addObserver(view);
	}
	
	public void setRoundView(JPanel round) {
		view.setRound(round);
	}
	
	
}
