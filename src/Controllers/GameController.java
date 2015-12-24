package Controllers;

import javax.swing.JPanel;

import Managers.DataManager;
import Models.Game;
import Models.OpenDoor;
import Models.PlayerAnswer;
import Models.Round;
import Models.RoundType;
import Models.Turn;
import Models.TurnState;
import View.GamePanel;

public class GameController {
	
	private Game model;
	private GamePanel view;
	
	public GameController(int gameId) {
		model = DataManager.getInstance().getGame(gameId);
		view = new GamePanel();
		
		ChatController chatcontroller = new ChatController(model);
		view.setChatPanel(chatcontroller.returnView());
		
		model.addObserver(view);
		model.updateView();
		
		//ActionListeners
		Utilities.ComponentUtility.addActionListener(view, "btnSubmit", (e) -> Submit_Click());
		Utilities.ComponentUtility.addActionListener(view, "btnPass", (e) -> Pass_Click());
	}
	
	public void Submit_Click() {
		Round currentRound = model.getCurrentRound();
		int answerId = currentRound.generateAnswerId();
		
		Turn currentTurn = currentRound.getCurrentTurn();
		currentRound.getCurrentTurn().addPlayerAnswer(new PlayerAnswer(currentTurn,answerId, view.txtInput.getText(), currentTurn.getTime()));
		
		currentRound.onSubmit(view.txtInput.getText());
	}
	
	public void Pass_Click() {
		model.getCurrentRound().nextTurn(TurnState.Pass);
		model.getPlayerGame1().stopTimer();
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
		RoundController roundController = null;
		
		switch(roundType) {
			case ThreeSixNine:
				roundController = new ThreeSixNineController(model);
			case OpenDoor:
				roundController = new OpenDoorController(model);
				//TODO add other controllers
		}
		
		model.addRound(roundController.getModel());
		view.setRound(roundController.getView());
	}
}
