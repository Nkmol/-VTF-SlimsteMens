package Models;

public class ReplayOpenDoor extends OpenDoor {

	int turnIndex;
	public ReplayOpenDoor(Game game) {
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
