package Models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import Managers.DataManager;

public class Puzzle extends Round {


	private final static int AMOUNT_QUESTIONS = 3;
	
	private int amountCorrectAnswers = 0;
	private int secondsEarned = 0;
	private ArrayList<PlayerAnswer> playerAnswers;
	
	public Puzzle(Game game) {
		super(game, RoundType.Puzzle);	
		init();
	}
	
	public Puzzle(ResultSet data, Game game) {
		super(data,game);
		init();
	}
	
	public void init() {
		//questions = DataManager.getInstance().getQuestions(this);
		updateView();
		DataManager.getInstance().pushTurn(currentTurn);
	}
	
	public void initNewTurn() {
		amountCorrectAnswers = 0;
		currentTurn = initCurrentTurn(this);
		DataManager.getInstance().pushTurn(currentTurn);
		updateView();
	}

	@Override
	public void onSubmit(String answer) {
		// TODO Auto-generated method stub
		System.out.println("your answer is " + answer);
		if (currentTurn.getCurrentQuestion().isPlayerAnswerCorrect(answer)) 
			Turn.pushTurn(currentTurn, TurnState.Correct, answer);
		else 
			Turn.pushTurn(currentTurn, TurnState.Wrong, answer);

		updateView();

	}

	@Override
	public void onPass() {
		//TODO: further implementation
		currentTurn.setTurnState(TurnState.Pass);
		DataManager.getInstance().updateTurn(currentTurn);
		if (currentTurn.getSkippedQuestion() == null)
			getGame().getController().endTurn();
		else 
			initNewTurn();
	}
	
	public ArrayList<PlayerAnswer> getSubmittedAnswers() {
		return playerAnswers;
	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

}
