package Models;

import java.sql.ResultSet;
import java.util.Observer;

import javax.swing.JPanel;

import Controllers.ReplayListController;
import Controllers.RoundController;
import Controllers.ThreeSixNineController;
import Managers.DataManager;

public class Replay extends Game {
	private RoundController currentRoundController;
	private int AnswerNumber;
	
	public Replay(int gameId, Player player1, Player player2, GameState gameState) {
		super(gameId, player1, player2, gameState);
		rounds = DataManager.getInstance().getRounds(this);
		AnswerNumber = 0;
		Round currentRound = new ReplayThreeSixNine(this);
		currentRoundController = new ThreeSixNineController(this, currentRound);
	}
	
	public JPanel GetView() {
		return currentRoundController.getView();
	}
	
	public String getCurrentAnswer() {
		return currentRoundController.getModel()
				.currentTurn
				.getCurrentQuestion()
				.getAnswers()
				.get(AnswerNumber)
				.getAnswer();
	}
	
	public void AddObserver(Observer o) {
		currentRound.addObserver(o);
	}
}