package Models;

import Controllers.ReplayController;

public class ReplayFinal extends Final {
	
	private int turnIndex;
	private ReplayController parent;
	public ReplayFinal(Game game, ReplayController parent) {
		super(game);
		this.parent = parent;
		turnIndex = 0;
		currentTurn = turns.get(turnIndex);
	}
	
	@Override
	public void onSubmit(String answer) {
		NextTurn();
	}
	
	private void NextTurn() {
		if (turnIndex < turns.size()-1)
			currentTurn = turns.get(++turnIndex);
		else
			parent.RoundEnd();
		updateView();
	}
}