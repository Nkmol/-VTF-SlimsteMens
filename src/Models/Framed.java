package Models;

import java.sql.ResultSet;
import java.util.ArrayList;

import Managers.DataManager;

public class Framed extends Round {

	private final static int AMOUNT_QUESTIONS = 1,
							 BONUS_ITERATION = 3,
							 POINTS_QUESTION = 20,
							 AMOUNT_ANSWERS = 10;
	private int secondsEarned = 0;
	private ArrayList<PlayerAnswer> playerAnswers;
	protected ArrayList<Answer> answersHandled;
	
	private Boolean stay;
	
	public Framed(Game game) {
		super(game, RoundType.Framed);
        if (game.getGameState() != GameState.Finished)
        	init();
	}
	
	public Framed(ResultSet data, Game game) {
		super(data,game);
        if (game.getGameState() != GameState.Finished)
        	init();
	}
	
	public void init() {
		//questions = DataManager.getInstance().getQuestions(this);
			//handleTurns();
			//checkStay();
		getCurrentTurn().startTimer();
		if(getCurrentTurn().getCurrentQuestion() != null) {
			if(getLastTurn() != null) {
				getCurrentTurn().setCurrentQuestion(getLastTurn().getCurrentQuestion());
			}
		}
		updateView();
		DataManager.getInstance().pushTurn(currentTurn);
		initPlayerAnswers();
	}
	
	public void initNewTurn() {
		currentTurn = initCurrentTurn(this);
		DataManager.getInstance().pushTurn(currentTurn);
		updateView();
	}
	
	public void checkStay() {
		Turn secondLastTurn = DataManager.getInstance().getXLastTurnForGame(this.getGame().getId(), this, 1);
		
		/*
		if(getLastTurn().getTurnState() == TurnState.Busy) {
			if(secondLastTurn != null && !currentTurn.getCurrentQuestion().getText().equals(secondLastTurn.getCurrentQuestion().getText())) {
				stay = false;
			}
			else {
				if(secondLastTurn != null && !secondLastTurn.getPlayer().getName().equals(currentTurn.getPlayer().getName())) {
					if(secondLastTurn.getTurnState() == TurnState.Correct){
						stay = false;
					}
					else if(secondLastTurn.getTurnState() == TurnState.Pass){
						stay = true;
					}
					else if(secondLastTurn.getTurnState() == TurnState.Wrong){
						stay = true;
					}
					else if(secondLastTurn.getTurnState() == TurnState.Timeout){
						stay = true;
					}
				}
				else {
					stay = false;
				}
			}
		}
		else {
			if(getLastTurn() != null && !currentTurn.getCurrentQuestion().getText().equals(getLastTurn().getCurrentQuestion().getText())) {
				stay = false;
			}
			else {
				if(getLastTurn() != null && !getLastTurn().getPlayer().getName().equals(currentTurn.getPlayer().getName())) {
					if(getLastTurn().getTurnState() == TurnState.Correct){
						stay = false;
					}
					else if(getLastTurn().getTurnState() == TurnState.Pass){
						stay = true;
					}
					else if(getLastTurn().getTurnState() == TurnState.Wrong){
						stay = true;
					}
					else if(getLastTurn().getTurnState() == TurnState.Timeout){
						stay = true;
					}
				}
				else {
					stay = false;
				}
			}
		}
		
		*/
	}
	
	public void handleTurns() {
		/*
		Turn secondLastTurn = DataManager.getInstance().getXLastTurnForGame(this.getGame().getId(), this, 1);
		
		Question currentQuestion = null;
		
		if(getLastTurn() != null && getLastTurn().getTurnState() == TurnState.Busy) {
			// last turn busy
			currentQuestion = getLastTurn().getCurrentQuestion();
		}
		else if(getLastTurn() != null && secondLastTurn != null && !getLastTurn().getCurrentQuestion().getText().equals(secondLastTurn.getCurrentQuestion().getText())) {
			// same question
			currentQuestion = getLastTurn().getCurrentQuestion();
		}
		else if(getLastTurn() != null && getLastTurn().getPlayer().getName().equals(currentTurn.getPlayer().getName())) {
			if(getLastTurn().getTurnState() == TurnState.Correct) {
				// same question
				currentQuestion = getLastTurn().getCurrentQuestion();
			}
			else if(secondLastTurn.getTurnState() == TurnState.Pass){
				// new question
				currentQuestion = getCurrentTurn().getCurrentQuestion();
			}
			else if(secondLastTurn.getTurnState() == TurnState.Wrong){
				// new question
				currentQuestion = getCurrentTurn().getCurrentQuestion();
			}
			else if(secondLastTurn.getTurnState() == TurnState.Timeout){
				// new question
				currentQuestion = getCurrentTurn().getCurrentQuestion();
			}
		}
		else if(secondLastTurn != null && secondLastTurn.getPlayer().getName().equals(currentTurn.getPlayer().getName())) {
			if(secondLastTurn.getTurnState() == TurnState.Correct) {
				// same question
				currentQuestion = getLastTurn().getCurrentQuestion();
			}
			else if(secondLastTurn.getTurnState() == TurnState.Pass){
				// new question
				currentQuestion = getCurrentTurn().getCurrentQuestion();
			}
			else if(secondLastTurn.getTurnState() == TurnState.Wrong){
				// new question
				currentQuestion = getCurrentTurn().getCurrentQuestion();
			}
			else if(secondLastTurn.getTurnState() == TurnState.Timeout){
				// new question
				currentQuestion = getCurrentTurn().getCurrentQuestion();
			}
		}
		else {
			currentQuestion = getCurrentTurn().getCurrentQuestion();
		}
		
		
		if(currentQuestion != null) {
			getCurrentTurn().setCurrentQuestion(currentQuestion);
		}
		*/
	}
	
	public void checkRoundDone() {
		
	}
	
	public void initPlayerAnswers() {
		if(playerAnswers == null) {
			playerAnswers = new ArrayList<PlayerAnswer>();
		}
		
		if(answersHandled == null) {
			answersHandled = new ArrayList<Answer>();
		}
	}

	private boolean answerIsValid(String answerString) {
		return !Question.isPlayerAnswerCorrect(answerString, answersHandled);
	}
	
	public void pushAnswers(ArrayList<PlayerAnswer> playerAnswers) {
		if (playerAnswers != null && playerAnswers.size() > 0) {
			//Push player's answers
			for (PlayerAnswer playerAnswer : playerAnswers) {
				DataManager.getInstance().pushPlayerAnswer(playerAnswer);
			}
			
			this.playerAnswers = null;
		}
	}


	@Override
	public void onSubmit(String answer) {
		// TODO Auto-generated method stub
		System.out.println("your answer is " + answer);
		
		/*
		if (currentTurn.getCurrentQuestion().isPlayerAnswerCorrect(answer))  //TODO IDK zeker of nodig
			Turn.pushTurn(currentTurn, TurnState.Correct, answer);
		else 
			Turn.pushTurn(currentTurn, TurnState.Wrong, answer);
			*/
		
		int answerId = playerAnswers.size() + 1;
		PlayerAnswer playerAnswer = new PlayerAnswer(currentTurn, answerId, answer, currentTurn.getMoment());
		
		playerAnswers.add(playerAnswer);
		
		Question currentQuestion = currentTurn.getCurrentQuestion();
		
		System.out.println("Question id: " + currentQuestion.getId());
		
		//TODO maybe feedback of je het goed had of niet?
		if (currentQuestion != null) {
			Answer answerCorrect = getCurrentTurn().getCurrentQuestion().isAnswerCorrect(answer);
			if (answerCorrect != null && answerIsValid(answer)) {
				answersHandled.add(answerCorrect);
				currentTurn.addSecondsEarnd(POINTS_QUESTION);
				
				if(answersHandled.size() == AMOUNT_ANSWERS) {
					pushAnswers(playerAnswers);
					currentTurn.setTurnState(TurnState.Correct);
					DataManager.getInstance().updateTurn(currentTurn);
					if (isCompleted()) {
						getGame().getController().endTurn();
						getGame().getController().loadNextRound(this.roundType);
					}
					else {
						getGame().getController().endTurn();
					}
				}
				else {
					updateView();
				}
			}
			else {
				currentTurn.setTurnState(TurnState.Wrong);
				//currentTurn.addSecondsEarnd(secondsEarned);
				DataManager.getInstance().updateTurn(currentTurn);
				pushAnswers(playerAnswers);
				if (isCompleted()) {
					getGame().getController().loadNextRound(this.roundType);
				}
				else {
					getGame().getController().endTurn();
				}
				
			}
		}		
			


		/*
		if (amountCorrectAnswers == AMOUNT_ANSWERS) {
			currentTurn.setTurnState(TurnState.Correct);
			currentTurn.addSecondsEarnd(POINTS_QUESTION);
			DataManager.getInstance().updateTurn(currentTurn);
			amountCorrectAnswers = 0;
			if (currentTurn.getSkippedQuestion() == null)
				getGame().getController().endTurn();
			else 
				initNewTurn();
		}
		*/
		
		/*
		if (isCompleted()) {
			getGame().getController().loadLastRound();
		}
		else {
			getGame().getController().endTurn();
		}
		
		*/
	
	}

	@Override
	public void onPass() {
		currentTurn.setTurnState(TurnState.Pass);
		DataManager.getInstance().updateTurn(currentTurn);
		pushAnswers(playerAnswers);
		if (isCompleted()) {
			getGame().getController().loadNextRound(this.roundType);
		}
		else {
			getGame().getController().endTurn();
		}
	}
	
	public ArrayList<Answer> getSubmittedAnswers() {
		return answersHandled;
	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		Turn lastTurn = DataManager.getInstance().getLastTurnForGame(this);
		Turn secondLastTurn = DataManager.getInstance().getXLastTurnForGame(getGame().getId(), this, 1); // TODO lelijk
		int amount = DataManager.getInstance().getAmountAskedQuestionsForRound(this);
		
		boolean roundCompleted = false;
		
		if(secondLastTurn != null && lastTurn.getTurnState() == TurnState.Wrong && secondLastTurn.getTurnState() == TurnState.Wrong &&
				secondLastTurn.getCurrentQuestion().getText().equals(lastTurn.getCurrentQuestion().getText())) {
			roundCompleted = true;
		}
		else if(secondLastTurn != null && lastTurn.getTurnState() == TurnState.Correct && secondLastTurn.getTurnState() == TurnState.Wrong &&
				secondLastTurn.getCurrentQuestion().getText().equals(lastTurn.getCurrentQuestion().getText())) {
			roundCompleted = true;
		}
		else if(secondLastTurn != null && lastTurn.getTurnState() == TurnState.Correct && secondLastTurn.getTurnState() == TurnState.Correct &&
				secondLastTurn.getCurrentQuestion().getText().equals(lastTurn.getCurrentQuestion().getText())) {
			roundCompleted = true;
		}
		else if(secondLastTurn != null && lastTurn.getTurnState() == TurnState.Correct && secondLastTurn.getTurnState() == TurnState.Pass &&
				secondLastTurn.getCurrentQuestion().getText().equals(lastTurn.getCurrentQuestion().getText())) {
			roundCompleted = true;
		}
		else if(secondLastTurn != null && lastTurn.getTurnState() == TurnState.Pass && secondLastTurn.getTurnState() == TurnState.Correct &&
				secondLastTurn.getCurrentQuestion().getText().equals(lastTurn.getCurrentQuestion().getText())) {
			roundCompleted = true;
		}
		else if(secondLastTurn != null && lastTurn.getTurnState() == TurnState.Pass && secondLastTurn.getTurnState() == TurnState.Pass &&
				secondLastTurn.getCurrentQuestion().getText().equals(lastTurn.getCurrentQuestion().getText())) {
			roundCompleted = true;
		}	
		else if(secondLastTurn != null && lastTurn.getTurnState() == TurnState.Wrong && secondLastTurn.getTurnState() == TurnState.Pass &&
				secondLastTurn.getCurrentQuestion().getText().equals(lastTurn.getCurrentQuestion().getText())) {
			roundCompleted = true;
		}	
		else if(secondLastTurn != null && lastTurn.getTurnState() == TurnState.Pass && secondLastTurn.getTurnState() == TurnState.Wrong &&
				secondLastTurn.getCurrentQuestion().getText().equals(lastTurn.getCurrentQuestion().getText())) {
			roundCompleted = true;
		}	
		
		if (roundCompleted)
			currentTurn.stopTimer();
		
		return false;
	}

	@Override
	public void playerTimeIsOver() {
		// TODO Auto-generated method stub
		getGame().getController().endTurn();
		getGame().stopGame();
	}


}
