package Models;

public class ReplayFramed extends Framed {
	
	int turnIndex;
	public ReplayFramed(Game game) {
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