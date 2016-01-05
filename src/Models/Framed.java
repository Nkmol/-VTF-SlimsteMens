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
		//currentTurn.startTimer();
		if(getCurrentTurn().getCurrentQuestion() != null) {
			if(getLastTurn() != null) {
				getCurrentTurn().setCurrentQuestion(getLastTurn().getCurrentQuestion());
			}
		}
		//updateView();
		if(!continueCurrentTurn)
			DataManager.getInstance().pushTurn(currentTurn);
		initPlayerAnswers();
	}
	
//	public void startTime() {
//		currentTurn.startTimer();
//	}

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
						currentTurn.stopTimer();
					}
				}
				else {
					updateView();
				}
			}
			else {
				currentTurn.stopTimer();
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
	}

	@Override
	public void onPass() {
		currentTurn.stopTimer();
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
//		int amount = DataManager.getInstance().getAmountAskedQuestionsForRound(this);
		
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
		
		System.out.println("iscompleted " + getCurrentTurn() + " " + this);
		if (roundCompleted)
			currentTurn.stopTimer();
		
		return roundCompleted;
	}

	@Override
	public void playerTimeIsOver() {
		// TODO Auto-generated method stub
		System.out.println("timeover");
		getGame().getController().endTurn();
		getGame().stopGame();
	}


}
