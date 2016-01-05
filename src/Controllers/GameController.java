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
		
/*		ChatController chatcontroller = new ChatController(model);
		view.setChatPanel(chatcontroller.returnView());*/
		
		model.addObserver(view);
		model.setController(this);
		
		//ActionListeners
		Utilities.ComponentUtility.addActionListener(view, "btnSubmit", (e) -> Submit_Click());
		Utilities.ComponentUtility.addActionListener(view, "btnPass", (e) -> Pass_Click());
		
		loadLastRound();
	}
	
	private void Submit_Click() {
		model.getCurrentRound().onSubmit(view.txtInput.getText());
	}
	
	private void Pass_Click() {
		model.getCurrentRound().onPass();
	}
	
	public void endTurn() {
		model.getCurrentRound().getCurrentTurn().stopTimer();
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

		switch(round.getRoundType()) {
		case ThreeSixNine:
			return new ThreeSixNineController(model, round);
		case OpenDoor:
			return new OpenDoorController(model, round);
		case Final:
			return new FinalController(model, round);
		case Framed:
			return new FramedController(model, round);
		case Puzzle:
			return new PuzzleController(model, round);
		default:
			return null;
		}
	}
	
	public void loadNextRound(RoundType currentRoundType) {
		if (currentRoundType != RoundType.Final) {
			RoundType nextRoundType = RoundType.nextRoundType(currentRoundType);
			model.setRound(Round.createRound(nextRoundType, model));
			model.getCurrentRound().getCurrentTurn().stopTimer();
			loadLastRound();
		} else {
			// Stop the game 
			endTurn();
			model.stopGame();
		}
	}

	public void loadLastRound() {
		
		Round round = DataManager.getInstance().getLastRoundForGame(model);
		// TODO Remove this as it's for testing purposes
		//round = new Final(model);

		if (round != null) {
//			round.getCurrentTurn().stopTimer();
			if (round.isCompleted()) {
				loadNextRound(round.getRoundType());
			}
		}

		if(round == null) {	
			round = new ThreeSixNine(model); // Add 369 manually to the database in table ronde
		}

		RoundController roundController = getRoundController(round, model);
	
		model.setRound(roundController.getModel());
		view.setRound(roundController.getView());
		
		model.updateView();
		roundController.getModel().updateView();
	}
}
