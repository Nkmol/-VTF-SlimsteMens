package Models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.TimerTask;

import Managers.DataManager;

public class Final extends Round {

	private final static int POINTS_QUESTION = 30,
							AMOUNT_OF_ANSWERS = 5;
	private int amountCorrectAnswers = 0,
				secondsEarned = 0;
	private Boolean responseIsRight;
	private ArrayList<Answer> AnswersHandled;
	
	TimerTask checkIfGameEnds;
	
	
	public Boolean getResponseIsRight() {
		return responseIsRight;
	}

	public void setResponseIsRight(Boolean responseIsRight) {
		this.responseIsRight = responseIsRight;
	}

	private ArrayList<PlayerAnswer> playerAnswers;
	
	Question currentQuestion;
	
	
	public Final(Game game) {
		super(game, RoundType.Final);
		if (game.getGameState() != GameState.Finished)
			initNewTurn();
	}
	
	public Final(ResultSet data, Game game) {
		super(data, game);
		if (game.getGameState() != GameState.Finished)
			initNewTurn();
	}
	
	public void initNewTurn() {
		//amountCorrectAnswers = 0;
		AnswersHandled = new ArrayList<>();
		currentTurn = initCurrentTurn(this);
		DataManager.getInstance().pushTurn(currentTurn);
		currentTurn.startTimer();
		
		checkIfGameEnds = new MyTimer().schedule(() -> isCompleted(), 1000);
		
		updateView();
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
	
	private boolean answerIsValid(String answerString) {
		return !Question.isPlayerAnswerCorrect(answerString, AnswersHandled);
	}

	@Override
	public void onSubmit(String answer) {
		// TODO Auto-generated method stub
		System.out.println("your answers is " + answer);
		
		if (playerAnswers == null)
			playerAnswers = new ArrayList<>();
		
		
		int answerId = playerAnswers.size() + 1;
		PlayerAnswer playerAnswer = new PlayerAnswer(currentTurn, answerId, answer, currentTurn.getMoment()); 
		//DataManager.getInstance().pushPlayerAnswer(playerAnswer);
		playerAnswers.add(playerAnswer);
		
		Question currentQuestion = (currentTurn.getSkippedQuestion() != null) ? currentTurn.getSkippedQuestion() : currentTurn.getCurrentQuestion();
		
		System.out.println("Question id: " + currentQuestion.getId());
		
		if (currentQuestion != null) {
			Answer answerCorrect = currentQuestion.isAnswerCorrect(answer);
			if (answerCorrect != null && answerIsValid(answer)) {
				AnswersHandled.add(answerCorrect);
				//amountCorrectAnswers++; 
				
				secondsEarned+=POINTS_QUESTION;
				currentTurn.setSecondsFinalLost(secondsEarned);
				
				
				setResponseIsRight(true);
			}else
				setResponseIsRight(false);
		}	
		
		updateView();
		
		if (AnswersHandled.size() == AMOUNT_OF_ANSWERS) {
			currentTurn.setTurnState(TurnState.Correct);
			DataManager.getInstance().updateTurn(currentTurn);
			AnswersHandled = new ArrayList<>();
			if (isCompleted())
				game.getController().endTurn();
			pushAnswers(playerAnswers);
			if (currentTurn.getSkippedQuestion() == null)
				getGame().getController().endTurn();
			else 
				initNewTurn();
		}
	
	}
	
	
	public ArrayList<PlayerAnswer> getSubmittedAnswers() {
		return playerAnswers;
	}

	@Override
	public void onPass() {
		// TODO Auto-generated method stub
		currentTurn.setTurnState(TurnState.Pass);
		DataManager.getInstance().updateTurn(currentTurn);
		if (isCompleted()) 
			game.getController().endTurn();
		if (currentTurn.getSkippedQuestion() == null)
			getGame().getController().endTurn();
		else 
			initNewTurn();
		
	}
	

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		if (currentTurn.getTotalActualTime() == 0){
			getGame().getController().loadNextRound(getRoundType());
//			getGame().getController().endTurn();
//			getGame().stopGame();
		}
		
		return true;
	}

}
