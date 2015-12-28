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
	private MainController parent;
	
	public GameController(int gameId, MainController parent) {
		model = DataManager.getInstance().getGame(gameId);
		view = new GamePanel();
		this.parent = parent;
		
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
		model.getCurrentRound().endTurn(TurnState.Pass, 0);
		//model.getPlayerGame1().stopTimer();
		parent.ShowMainPanel();
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
	
	public static RoundController getRoundController(RoundType roundType, Game model) {
		switch(roundType) {
		case ThreeSixNine:
			return new ThreeSixNineController(model);
		case OpenDoor:
			//return new OpenDoorController(model);
			//TODO add other controllers
		default:
			return null;
		}
	}

	public void addRound(RoundType roundType) {
		RoundController roundController = getRoundController(roundType, model);
		
		model.addRound(roundController.getModel());
		view.setRound(roundController.getView());
	}

	public void loadLastRound() {
		RoundController roundController = getRoundController(DataManager.getInstance().getLastRoundForGame(model).getRoundType(), model);
		
		model.setRound(roundController.getModel());
		view.setRound(roundController.getView());
	}
}
