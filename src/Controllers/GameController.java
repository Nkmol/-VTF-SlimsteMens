package Controllers;

import javax.swing.JPanel;

import Managers.DataManager;
import Models.Final;
import Models.Game;
import Models.OpenDoor;
import Models.PlayerAnswer;
import Models.Round;
import Models.RoundType;
import Models.ThreeSixNine;
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
		model.setController(this);
		
		//ActionListeners
		Utilities.ComponentUtility.addActionListener(view, "btnSubmit", (e) -> Submit_Click());
		Utilities.ComponentUtility.addActionListener(view, "btnPass", (e) -> Pass_Click());
		
		loadLastRound();
	}
	
	public void Submit_Click() {
		model.getCurrentRound().onSubmit(view.txtInput.getText());
	}
	
	public void Pass_Click() {
		Turn.pushTurn(model.getCurrentRound().getCurrentTurn(), TurnState.Pass, null);
		//model.getPlayerGame1().stopTimer();
		//parent.ShowMainPanel();
	}
	
	public void endTurn() {
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
	
	public static RoundController getRoundController(Round round, Game model) {
		
		// TODO switch om terug naar round.getRoundType()

		//switch(round.getRoundType()) {
		switch(RoundType.Final) {
		case ThreeSixNine:
			return new ThreeSixNineController(model, round);
		case OpenDoor:
			return new OpenDoorController(model, round);
		case Final:
			return new FinalController(model, round);
		default:
			return null;
		}
	}

	public void loadLastRound() {
		
		Round round = DataManager.getInstance().getLastRoundForGame(model);
		
		if(round == null) {	
			//round = new ThreeSixNine(model);
			round = new Final(model);
		}

		RoundController roundController = getRoundController(round, model);
	
		model.setRound(roundController.getModel());
		view.setRound(roundController.getView());
		
		roundController.getModel().updateView();
	}
}
