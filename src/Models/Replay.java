package Models;

import Managers.DataManager;

public class Replay extends Game {
	private int AnswerNumber;
	
	public Replay(int gameId, Player player1, Player player2, GameState gameState) {
		super(gameId, player1, player2, gameState);
		rounds = DataManager.getInstance().getRounds(this);
		AnswerNumber = 0;
	}
	
	public String getCurrentAnswer() {
		return getCurrentRound()
				.currentTurn
				.getCurrentQuestion()
				.getAnswers()
				.get(AnswerNumber)
				.getAnswer();
	}
}