package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import Managers.DataManager;

public class ThreeSixNine extends Round {

	private final static int AMOUNT_QUESTIONS = 9,
							 BONUS_ITERATION = 3,
							 POINTS_QUESTION = 20;
	
	public ThreeSixNine(Game game) {
		super(game, RoundType.ThreeSixNine);
		init();
	}
	
	public ThreeSixNine(ResultSet data, Game game) {
		super(data, game);
		init();
	}
	
	public void init() {
		System.out.println(currentTurn.getSkippedQuestion().getText());
		updateView();
	}

	@Override
	public void onSubmit(String answer) {
		System.out.println("your answers is " + answer);
		if (currentTurn.getCurrentQuestion().isPlayerAnswerCorrect(answer)) 
			Turn.pushTurn(currentTurn, TurnState.Correct, answer);
		else 
			Turn.pushTurn(currentTurn, TurnState.Wrong, answer);
	}

	@Override
	public void onPass() {
		// TODO Auto-generated method stub
		
	}
}
