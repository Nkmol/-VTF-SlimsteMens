package Models;

import Controllers.FinalController;
import Controllers.FramedController;
import Controllers.OpenDoorController;
import Controllers.PuzzleController;
import Controllers.ReplayController;
import Controllers.RoundController;
import Controllers.ThreeSixNineController;
import Managers.DataManager;

public class Replay extends Game {
	private int AnswerNumber;
	private ReplayController parent;
	private int currentRoundIndex;
	public Replay(int gameId, Player player1, Player player2, GameState gameState, ReplayController parent) {
		super(gameId, player1, player2, gameState);
		currentRoundIndex = 0;
		this.parent = parent;
		AnswerNumber = 0;
	}
	
	public RoundController getPreviousRound() {
		switch (currentRound.roundType) {
			case OpenDoor:
				currentRound = new ReplayThreeSixNine(this, parent, true);
				return new ThreeSixNineController(this, currentRound);
			case Puzzle:
				currentRound = new ReplayOpenDoor(this, parent, true);
				return new OpenDoorController(this, currentRound);
			case Framed:
				currentRound = new ReplayPuzzle(this, parent, true);
				return new PuzzleController(this, currentRound);
			case Final:
				currentRound = new ReplayFramed(this, parent, true);
				return new FramedController(this, currentRound);
			default:
				return null;
		}
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
				currentRound = new ReplayFramed(this, parent);
				return new FramedController(this, currentRound);
			case Framed:
				currentRound = new ReplayFinal(this, parent);
				return new FinalController(this, currentRound);
			default:
				return null;
		}
	}
	
	public String getCurrentAnswer() {
		if (currentRound.getRoundType() == RoundType.ThreeSixNine) {
		}
		StringBuilder builder = new StringBuilder();
		for (PlayerAnswer answer : currentRound.getCurrentTurn().getPlayerAnswers()) {
			builder.append(answer.getAnswer());
			builder.append(" ");
		}
		builder.trimToSize();
		return builder.toString();
	}
	
	private int getSecondsEarnedForPlayer(Player player) {
		int seconds = 0;
		for (Turn turn: currentRound.getTurns()) {
			if (turn.getPlayer().getName().equals(player.getName()))
				seconds += turn.getSecondsEarned();
			if (turn.getTurnId() == currentRound.getCurrentTurn().getTurnId())
				break;
		}
		return seconds;	
	}
	
	public int GetSecondsEarnedPlayer1() {
		return getSecondsEarnedForPlayer(getPlayer1());
	}
	
	public int GetSecondsEarnedPlayer2() {
		return getSecondsEarnedForPlayer(getPlayer2());
	}
}