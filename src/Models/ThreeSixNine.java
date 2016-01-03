package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import Managers.DataManager;

public class ThreeSixNine extends Round {

	private final static int AMOUNT_QUESTIONS = 9,
							 BONUS_ITERATION = 3,
							 POINTS_BONUS  = 60,
							 POINTS_QUESTION = 20;
	private int amountCorrectQuestions = 0;
	
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
		currentTurn.startTimer();
		amountCorrectQuestions = DataManager.getInstance().getCorrectAmountUniqueSharedQuestionsForRound(this, DataManager.getInstance().getCurrentUser());
		System.out.println(amountCorrectQuestions);
		if(amountCorrectQuestions > 0 && amountCorrectQuestions % BONUS_ITERATION == 0)
			currentTurn.setTurnState(TurnState.Bonus);
		
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
			SharedQuestion newSharedQuestion = null;
			if(lastTurn == null)
				newSharedQuestion =  new SharedQuestion(getCurrentTurn().getCurrentQuestion(), 1);
			else
				newSharedQuestion =  new SharedQuestion(getCurrentTurn().getCurrentQuestion(), lastTurn.getSharedQuestion().getIndexNumber() + 1);
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
		if(sharedSkippedQuestion != null && 
				!sharedSkippedQuestion.getTurn().getPlayer().getName().equals(currentTurn.getPlayer().getName())) //If the skipped question is not from you
			return DataManager.getInstance().getLastSkippedSharedQuestion(lastTurn);
		else
			return null;
				
	}

	@Override
	public void onSubmit(String answer) {
		System.out.println("your answers is " + answer);
		Question currentQuestion = (currentTurn.getSkippedQuestion() != null) ? currentTurn.getSkippedQuestion() : currentTurn.getCurrentQuestion();
		TurnState turnState = null;
		
		if (currentQuestion.isPlayerAnswerCorrect(answer)) {
			if(currentTurn.getTurnState() == TurnState.Bonus)
				currentTurn.addSecondsEarnd(POINTS_BONUS);
			else
				currentTurn.addSecondsEarnd(POINTS_QUESTION);
			turnState = TurnState.Correct;
		}
		else 
			turnState = TurnState.Wrong;

		Turn.pushTurn(currentTurn, turnState, answer);
		returnScreenCheck(turnState);
		isCompleted();
	}

	@Override
	public void onPass() {
		Turn.pushTurn(getCurrentTurn(), TurnState.Pass, null);
		returnScreenCheck(TurnState.Pass);
		isCompleted();
	}
	
	public void returnScreenCheck(TurnState turnState) {
		if(currentTurn.getSkippedQuestion() == null && turnState != TurnState.Correct) 
			getGame().getController().endTurn();
		else {
			currentTurn = initCurrentTurn(this);
			initNewTurn();
			updateView();
		}
	}

	@Override
	public boolean isCompleted() {
		boolean isCompleted = false;
		
		int amountUniqueSharedQuestions = DataManager.getInstance().getAmountUniqueSharedQuestionsForRound(this, DataManager.getInstance().getCurrentUser());
		System.out.println("amountUnique: " + amountUniqueSharedQuestions);
		if(amountUniqueSharedQuestions >= AMOUNT_QUESTIONS)
			isCompleted = true;
		else
			isCompleted = false;
		
		if(isCompleted)
			game.getController().loadNextRound(roundType);
		
		return isCompleted;
	}
}
