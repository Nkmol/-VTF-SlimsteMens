package Models;

import Controllers.FinalController;
import Controllers.OpenDoorController;
import Controllers.PuzzleController;
import Controllers.ReplayController;
import Controllers.RoundController;
import Managers.DataManager;

public class Replay extends Game {
	private int AnswerNumber;
	ReplayController parent;
	public Replay(int gameId, Player player1, Player player2, GameState gameState, ReplayController parent) {
		super(gameId, player1, player2, gameState);
		rounds = DataManager.getInstance().getRounds(this);
		this.parent = parent;
		AnswerNumber = 0;
	}
	
	public RoundController getNextRound() {
		switch (currentRound.roundType) {
			case ThreeSixNine:
				currentRound = new ReplayOpenDoor(this, parent);
				return new OpenDoorController(this, currentRound);
			case OpenDoor:
				currentRound = new ReplayPuzzle(this, parent);
				return new PuzzleController(this, currentRound);
			case Puzzle:
				currentRound = new ReplayPuzzle(this, parent);
				return new PuzzleController(this, currentRound);
			case Framed:
				currentRound = new ReplayFinal(this, parent);
				return new FinalController(this, currentRound);
			default:
				return null;
		}
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