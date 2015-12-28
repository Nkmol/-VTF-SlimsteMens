package Models;

import java.sql.ResultSet;

public class Framed extends Round {

	private final static int AMOUNT_QUESTIONS = 9,
							 BONUS_ITERATION = 3,
							 POINTS_QUESTION = 20;
	
	public Framed(Game game) {
		super(game, RoundType.Framed);	
	}
	
	public Framed(ResultSet data, Game game) {
		super(data,game);
	}
	
	public void init() {
		//questions = DataManager.getInstance().getQuestions(this);
		currentTurn.setCurrentQuestion();
		
		updateView();
	}

	@Override
	public void onSubmit(String answer) {
		// TODO Auto-generated method stub
		System.out.println("your answers is " + answer);
		if (currentTurn.getCurrentQuestion().isPlayerAnswerCorrect(answer)) 
			Turn.pushTurn(currentTurn, TurnState.Correct, answer);
		else 
			Turn.pushTurn(currentTurn, TurnState.Wrong, answer);
	}

}
