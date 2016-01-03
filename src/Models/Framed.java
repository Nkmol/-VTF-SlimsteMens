package Models;

import java.sql.ResultSet;
import java.util.ArrayList;

import Managers.DataManager;

public class Framed extends Round {

	private final static int AMOUNT_QUESTIONS = 1,
							 BONUS_ITERATION = 3,
							 POINTS_QUESTION = 20,
							 AMOUNT_ANSWERS = 10;
	private int amountCorrectAnswers = 0;
	private int secondsEarned = 0;
	private ArrayList<PlayerAnswer> playerAnswers;
	
	private Boolean stay;
	
	public Framed(Game game) {
		super(game, RoundType.Framed);	
		init();
	}
	
	public Framed(ResultSet data, Game game) {
		super(data,game);
		init();
	}
	
	public void init() {
		//questions = DataManager.getInstance().getQuestions(this);
			//handleTurns();
			//checkStay();
		if(getCurrentTurn().getCurrentQuestion() != null) {
			if(getLastTurn() != null) {
				getCurrentTurn().setCurrentQuestion(getLastTurn().getCurrentQuestion());
			}
		}
		updateView();
		DataManager.getInstance().pushTurn(currentTurn);
	}
	
	public void initNewTurn() {
		amountCorrectAnswers = 0;
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
	
	public Boolean checkQuestionDone() {
		int amount = DataManager.getInstance().getAmountCorrectAnswersForQuestion(currentTurn.getGameId(), currentTurn.getCurrentQuestion());
		if(amount == AMOUNT_ANSWERS) {
			return true;
		}
		return false;
	}
	
	public void checkRoundDone() {
		
	}

	public void checkUsedAnswer() {
		
	}

	@Override
	public void onSubmit(String answer) {
		// TODO Auto-generated method stub
		System.out.println("your answer is " + answer);
		if (currentTurn.getCurrentQuestion().isPlayerAnswerCorrect(answer)) 
			Turn.pushTurn(currentTurn, TurnState.Correct, answer);
		else 
			Turn.pushTurn(currentTurn, TurnState.Wrong, answer);
		

		if (playerAnswers == null)
			playerAnswers = new ArrayList<>();
		
		int answerId = playerAnswers.size() + 1;
		PlayerAnswer playerAnswer = new PlayerAnswer(currentTurn, answerId, answer, 10); //TODO: change the moment
	
		playerAnswers.add(playerAnswer);
			
		Question currentQuestion = currentTurn.getCurrentQuestion();
		
		System.out.println("Question id: " + currentQuestion.getId());
		
		//TODO maybe feedback of je het goed had of niet?
		if (currentQuestion != null) {
			if (currentQuestion.isPlayerAnswerCorrect(answer)) {
				amountCorrectAnswers++; 
				//secondsEarned+=POINTS_QUESTION;
				currentTurn.setTurnState(TurnState.Correct);
				currentTurn.addSecondsEarnd(POINTS_QUESTION);
				DataManager.getInstance().updateTurn(currentTurn);
				DataManager.getInstance().pushPlayerAnswer(playerAnswer);
				if (isCompleted()) {
					getGame().getController().loadLastRound();
				}
			}
			else {
				currentTurn.setTurnState(TurnState.Wrong);
				//currentTurn.addSecondsEarnd(secondsEarned);
				DataManager.getInstance().updateTurn(currentTurn);
				amountCorrectAnswers = 0;
				if (isCompleted()) {
					getGame().getController().loadLastRound();
				}
				else {
					getGame().getController().endTurn();
				}
				
			}
		}		
			
		updateView();

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
		//TODO: AFMAKEN
		currentTurn.setTurnState(TurnState.Pass);
		DataManager.getInstance().updateTurn(currentTurn);
		if (currentTurn.getSkippedQuestion() == null)
			getGame().getController().endTurn();
		else 
			initNewTurn();
		
		if (isCompleted()) {
			getGame().getController().loadLastRound();
		}
		else {
			getGame().getController().endTurn();
		}
	}
	
	public ArrayList<PlayerAnswer> getSubmittedAnswers() {
		return playerAnswers;
	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		Turn lastTurn = DataManager.getInstance().getLastTurnForGame(this);
		Turn secondLastTurn = DataManager.getInstance().getXLastTurnForGame(getGame().getId(), this, 1); // TODO lelijk
		int amount = DataManager.getInstance().getAmountAskedQuestionsForRound(this);
		
			if(secondLastTurn != null && lastTurn.getTurnState() == TurnState.Wrong && secondLastTurn.getTurnState() == TurnState.Wrong &&
					secondLastTurn.getCurrentQuestion().getText().equals(lastTurn.getCurrentQuestion().getText())) {
				return true;
			}
			else if(secondLastTurn != null && lastTurn.getTurnState() == TurnState.Correct && secondLastTurn.getTurnState() == TurnState.Wrong &&
					secondLastTurn.getCurrentQuestion().getText().equals(lastTurn.getCurrentQuestion().getText())) {
				return true;
			}
			else if(secondLastTurn != null && lastTurn.getTurnState() == TurnState.Correct && secondLastTurn.getTurnState() == TurnState.Pass &&
					secondLastTurn.getCurrentQuestion().getText().equals(lastTurn.getCurrentQuestion().getText())) {
				return true;
			}
			else if(secondLastTurn != null && lastTurn.getTurnState() == TurnState.Pass && secondLastTurn.getTurnState() == TurnState.Pass &&
					secondLastTurn.getCurrentQuestion().getText().equals(lastTurn.getCurrentQuestion().getText())) {
				return true;
			}	
			else if (checkQuestionDone()) {
				return true;
			}
		
		
		return false;
	}


}
