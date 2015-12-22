package Controllers;

import javax.swing.JPanel;

import Managers.DataManager;
import Models.Game;
import Models.PlayerAnswer;
import View.GamePanel;

public class GameController {
	
	private Game model;
	private GamePanel view;
	
	public GameController(int gameId) {
		model = DataManager.getInstance().getGame(gameId);
		view = new GamePanel();
		model.addObserver(view);
		model.updateView();
		
		Utilities.ComponentUtility.addActionListener(view, "btnSubmit", (e) -> Submit_Click());
	}
	
	public void Submit_Click() {
		int answerId = model.getCurrentRound().generateAnswerId();
		model.getCurrentRound().getTurn().addPlayerAnswer(new PlayerAnswer(answerId, view.txtInput.getText(), 0));
		//TODO: keep track of the moment it is posted
	}
	
	public void setRoundView(JPanel round) {
		view.setRound(round);
	}
	
	public GamePanel getView() {
		return view;
	}
	
}
