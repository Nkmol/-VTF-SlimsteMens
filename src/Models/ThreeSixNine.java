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
		if(lastTurn != null)
			getCurrentTurn().setSharedSkippedQuestion(initSharedSkippedQuestion());
		getCurrentTurn().setSharedQuestion(initSharedQuestion());
		if(!continueCurrentTurn) {
			DataManager.getInstance().pushTurn(currentTurn);
			DataManager.getInstance().pushSharedQuestion(getCurrentTurn());
		}
		updateView();
	}
	
	public SharedQuestion initSharedQuestion() {
		if(getCurrentTurn().getSharedSkippedQuestion() == null) {
			SharedQuestion newSharedQuestion =  new SharedQuestion(getCurrentTurn().getCurrentQuestion(), 1);
			return newSharedQuestion;
		}
		else {
			SharedQuestion sharedSkippedQuestion = getCurrentTurn().getSharedSkippedQuestion();
			System.out.println(sharedSkippedQuestion.getId());
			//sharedSkippedQuestion.setRound(this);
			return sharedSkippedQuestion;
		}
	}
	
	public SharedQuestion initSharedSkippedQuestion() {
		return DataManager.getInstance().getLastSkippedSharedQuestion(lastTurn);
	}

	@Override
	public void onSubmit(String answer) {
		System.out.println("your answers is " + answer);
		
		if(currentTurn.getSkippedQuestion() != null) {
			if (currentTurn.getSkippedQuestion().isPlayerAnswerCorrect(answer)) 
				Turn.pushTurn(currentTurn, TurnState.Correct, answer);
			else 
				Turn.pushTurn(currentTurn, TurnState.Wrong, answer);
			
			currentTurn.deleteSkippedQuestion();
		} 
		else {
			initSharedQuestion();
			
			if (currentTurn.getCurrentQuestion().isPlayerAnswerCorrect(answer)) 
				Turn.pushTurn(currentTurn, TurnState.Correct, answer);
			else 
				Turn.pushTurn(currentTurn, TurnState.Wrong, answer);
			
			getGame().getController().endTurn();
		}
	}

	@Override
	public void onPass() {
		Turn.pushTurn(getCurrentTurn(), TurnState.Pass, null);
		getGame().getController().endTurn();
	}
}
