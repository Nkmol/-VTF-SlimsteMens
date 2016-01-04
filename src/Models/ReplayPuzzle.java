package Models;

import Controllers.ReplayController;
import Managers.DataManager;

public class ReplayPuzzle extends Puzzle {

	int turnIndex;
	private ReplayController parent;
	public ReplayPuzzle(Game game, ReplayController parent) {
		super(game);
		this.parent = parent;
		turnIndex = 0;
		turns = DataManager.getInstance().getTurns(this);
		currentTurn = turns.get(turnIndex);
		continueCurrentTurn = true;
		updateView();
	}
	
	@Override
	public void onSubmit(String answer) {
		NextTurn();
		updateView();
	}

	@Override
	public void onPass() {
		NextTurn();
		updateView();
	}
	
	private void NextTurn() {
		if (turnIndex < turns.size()-1)
			currentTurn = turns.get(++turnIndex);
		else
			parent.RoundEnd();
		updateView();
	}
}