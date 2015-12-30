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
		initNewTurn();
		updateView();
	}
	
	public ThreeSixNine(ResultSet data, Game game) {
		super(data, game);
		initNewTurn();
		updateView();
	}
	
	public void initNewTurn() {
		if(lastTurn != null)
			getCurrentTurn().setSharedSkippedQuestion(initSharedSkippedQuestion());
		getCurrentTurn().setSharedQuestion(initSharedQuestion());
		if(!continueCurrentTurn) {
			DataManager.getInstance().pushTurn(currentTurn);
			DataManager.getInstance().pushSharedQuestion(getCurrentTurn());
		}
		
		currentTurn.startTurn();
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
		SharedQuestion sharedSkippedQuestion = DataManager.getInstance().getLastSkippedSharedQuestion(lastTurn);
		if(!sharedSkippedQuestion.getTurn().getPlayer().getName().equals(currentTurn.getPlayer().getName())) //If the skipped question is not from you
			return DataManager.getInstance().getLastSkippedSharedQuestion(lastTurn);
		else
			return null;
				
	}

	@Override
	public void onSubmit(String answer) {
		System.out.println("your answers is " + answer);
		Question currentQuestion = (currentTurn.getSkippedQuestion() != null) ? currentTurn.getSkippedQuestion() : currentTurn.getCurrentQuestion();
		
		if (currentQuestion.isPlayerAnswerCorrect(answer)) 
			Turn.pushTurn(currentTurn, TurnState.Correct, answer);
		else 
			Turn.pushTurn(currentTurn, TurnState.Wrong, answer);

		returnScreenCheck();
	}

	@Override
	public void onPass() {
		Turn.pushTurn(getCurrentTurn(), TurnState.Pass, null);
		returnScreenCheck();
	}
	
	public void returnScreenCheck() {
		if(currentTurn.getSkippedQuestion() == null) 
			getGame().getController().endTurn();
		else {
			currentTurn = initCurrentTurn(this);
			initNewTurn();
			updateView();
		}
	}
}
