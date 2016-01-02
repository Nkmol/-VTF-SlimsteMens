package Models;

import java.sql.ResultSet;
import java.util.ArrayList;

import Managers.DataManager;

public class Final extends Round {

	private final static int POINTS_QUESTION = 20;
	private static final int amountOfAnswers = 5;
	private int amountCorrectAnswers = 0,
				secondsEarned = 0;
	private Boolean responseIsRight;
	
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
		init();
	}
	
	public Final(ResultSet data, Game game) {
		super(data, game);
		init();
	}
	
	public void init() {
		DataManager.getInstance().pushTurn(currentTurn);
		updateView();
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
		System.out.println("your answers is " + answer);
		
		if (playerAnswers == null)
			playerAnswers = new ArrayList<>();
		
		
		int answerId = playerAnswers.size() + 1;
		PlayerAnswer playerAnswer = new PlayerAnswer(currentTurn, answerId, answer, currentTurn.getMoment()); 
		DataManager.getInstance().pushPlayerAnswer(playerAnswer);
		playerAnswers.add(playerAnswer);
		
		Question currentQuestion = (currentTurn.getSkippedQuestion() != null) ? currentTurn.getSkippedQuestion() : currentTurn.getCurrentQuestion();
		
		System.out.println("Question id: " + currentQuestion.getId());
		
		if (currentTurn.getCurrentQuestion().isPlayerAnswerCorrect(answer)){ 
			amountCorrectAnswers++;
			setResponseIsRight(true);
		}else{
			setResponseIsRight(false);
		}
		
		if (currentQuestion != null) {
			if (currentQuestion.isPlayerAnswerCorrect(answer)) {
				amountCorrectAnswers++; 
				secondsEarned+=POINTS_QUESTION;
			}
		}	
		
		updateView();
		
		if (amountCorrectAnswers == amountOfAnswers) {
			currentTurn.setTurnState(TurnState.Correct);
			currentTurn.setSecondsEarned(secondsEarned);
			DataManager.getInstance().updateTurn(currentTurn);
			amountCorrectAnswers = 0;
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
		if (currentTurn.getSkippedQuestion() == null)
			getGame().getController().endTurn();
		else 
			initNewTurn();
		
	}

}
