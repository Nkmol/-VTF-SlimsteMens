package Models;

import java.sql.ResultSet;
import java.util.ArrayList;

import Managers.DataManager;

public class Final extends Round {

	private final static int POINTS_QUESTION = 20;
	protected ArrayList<Question> questions;
	Question currentQuestion;
	
	
	public Final(Game game) {
		super(game, RoundType.Final);
		questions =  DataManager.getInstance().getQuestions(this);
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
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}

	@Override
	public void onSubmit(String answer) {
		// TODO Auto-generated method stub
		System.out.println("your answers is " + answer);
		if (currentTurn.getCurrentQuestion().isPlayerAnswerCorrect(answer)) 
			Turn.pushTurn(currentTurn, TurnState.Correct, answer);
		else 
			Turn.pushTurn(currentTurn, TurnState.Wrong, answer);
		
		updateView();
	}

	@Override
	public void onPass() {
		// TODO Auto-generated method stub
		
	}

}
