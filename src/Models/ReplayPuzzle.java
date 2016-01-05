package Models;

import java.util.ArrayList;

import Controllers.ReplayController;
import Managers.DataManager;

public class ReplayPuzzle extends Puzzle {

	int turnIndex;
	private ReplayController parent;
	public ReplayPuzzle(Game game, ReplayController parent) {
		this(game, parent, false);
	}
	
	public ReplayPuzzle(Game game, ReplayController parent, boolean LastTurn) {
		super(game);
		this.parent = parent;
		turns = DataManager.getInstance().getTurns(this);
		turnIndex = (LastTurn) ? turns.size()-1 : 0;
		currentTurn = turns.get(turnIndex);
		answersHandled = new ArrayList<>();
		updateView();
	}

	@Override
	public void onSubmit(String answer) {
		PreviousTurn();
		updateView();
	}
	
	@Override
	public void onPass() {
		NextTurn();
		updateView();
	}
	
	private void PreviousTurn() {
		if (turnIndex > 0) {
			currentTurn = turns.get(--turnIndex);
			// for 'fake' bonus rounds
			if (currentTurn.getCurrentQuestion() == null)
				PreviousTurn();
		} else {
			parent.PrevRound();
		}
	}
	
	private void NextTurn() {
		if (turnIndex < turns.size()-1) {
			currentTurn = turns.get(++turnIndex);
			// for 'fake' bonus rounds
			if (currentTurn.getCurrentQuestion() == null)
				NextTurn();
		} else {
			parent.NextRound();
		}
	}
}