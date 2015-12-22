package Controllers;

import javax.swing.JPanel;

import Managers.DataManager;
import Models.Game;
import Models.PlayerAnswer;
import Models.Round;
import Models.RoundType;
import View.GamePanel;

public class GameController {
	
	private Game model;
	private GamePanel view;
	
	public GameController(int gameId) {
		model = DataManager.getInstance().getGame(gameId);
		view = new GamePanel();
		model.addObserver(view);
		model.updateView();
		
		//ActionListeners
		//Utilities.ComponentUtility.addActionListener(view, "btnSubmit", (e) -> Submit_Click());
		//Utilities.ComponentUtility.addActionListener(view, "btnPass", (e) -> Pass_Click());
	}
	
	public void Submit_Click() {
		Round currentRound = model.getCurrentRound();
		int answerId = currentRound.generateAnswerId();
		
		currentRound.getCurrentTurn().addPlayerAnswer(new PlayerAnswer(answerId, view.txtInput.getText(), currentRound.getCurrentTurn().getTime()));
	}
	
	public void Pass_Click() {
		//TODO Next player
	}
	
	public void setRoundView(JPanel round) {
		view.setRound(round);
	}
	
	public GamePanel getView() {
		return view;
	}
	
	public Game getModel() {
		return model;
	}

	public void addRound(RoundType roundType) {
		//TODO Dynamic
		ThreeSixNineController threesixnineController = new ThreeSixNineController(model);
		
		view.setRound(threesixnineController.getView());
	}
}
