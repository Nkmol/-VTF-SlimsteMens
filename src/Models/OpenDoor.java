package Models;

import java.sql.ResultSet;
import java.util.ArrayList;

import Managers.DataManager;

public class OpenDoor extends Round {

	private ArrayList<PlayerAnswer> playerAnswers;
	private int amountCorrectAnswers = 0;
	private static final int AMOUNT_OF_ANSWERS = 4;
	private int secondsEarned = 0;
	private static final int SECONDS_PER_CORRECT_ANSWER = 40;
	
	public OpenDoor(Game game) {
		super(game, RoundType.OpenDoor);
		init();
	}
	
	public OpenDoor(ResultSet data, Game game) {
		super(data, game);
		init();
	}
	
	public void init() {
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
		
		if (playerAnswers == null)
			playerAnswers = new ArrayList<>();
		
		int answerId = playerAnswers.size() + 1;
		PlayerAnswer playerAnswer = new PlayerAnswer(currentTurn, answerId, answer, 10); //TODO: change the moment
		DataManager.getInstance().pushPlayerAnswer(playerAnswer);
		playerAnswers.add(playerAnswer);
		
		Question currentQuestion = (currentTurn.getSkippedQuestion() != null) ? currentTurn.getSkippedQuestion() : currentTurn.getCurrentQuestion();
		
		System.out.println("Question id: " + currentQuestion.getId());
		
		if (currentQuestion != null) {
			if (currentQuestion.isPlayerAnswerCorrect(answer)) {
				amountCorrectAnswers++; 
				secondsEarned+=SECONDS_PER_CORRECT_ANSWER;
			}
		}		
			
		updateView();
		
		if (amountCorrectAnswers == AMOUNT_OF_ANSWERS) {
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

}
