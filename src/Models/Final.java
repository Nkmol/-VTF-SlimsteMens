package Models;

import java.sql.ResultSet;

public class Final extends Round {

	private final static int POINTS_QUESTION = 20;
	Question currentQuestion;
	
	
	public Final(Game game) {
		super(game, RoundType.Final);
		init();
	}
	
	public Final(ResultSet data, Game game) {
		super(data, game);
		init();
	}
	
	public void init() {
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
