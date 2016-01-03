package Models;

public class ReplayPuzzle extends Puzzle {

	int turnIndex;
	public ReplayPuzzle(Game game) {
		super(game);
		turnIndex = 0;
		currentTurn = turns.get(turnIndex);
	}
	
	@Override
	public void onSubmit(String answer) {
		NextTurn();
	}
	
	private void NextTurn() {
		currentTurn = turns.get(++turnIndex);
		updateView();
	}
}