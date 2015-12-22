package Controllers;

import javax.swing.JPanel;

import Managers.DataManager;
import Models.Game;
import Models.PlayerAnswer;
import Models.Round;
import Models.Turn;
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
		Round currentRound = model.getCurrentRound();
		int answerId = currentRound.generateAnswerId();
		
		Turn currentTurn = currentRound.getCurrentTurn();
		currentRound.getCurrentTurn().addPlayerAnswer(new PlayerAnswer(currentTurn,answerId, view.txtInput.getText(), currentTurn.getTime()));
	}
	
	public void setRoundView(JPanel round) {
		view.setRound(round);
	}
	
	public GamePanel getView() {
		return view;
	}
}
