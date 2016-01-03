package Models;

public class ReplayThreeSixNine extends ThreeSixNine {

	int turnIndex;
	public ReplayThreeSixNine(Game game) {
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