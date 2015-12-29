package Models;

import java.sql.ResultSet;
import java.util.ArrayList;

import Managers.DataManager;

public class OpenDoor extends Round {

	private ArrayList<PlayerAnswer> playerAnswers;
	private int amountCorrectAnswers = 0;
	private static final int amountOfAnswers = 4;
	
	public OpenDoor(Game game) {
		super(game, RoundType.OpenDoor);
		init();
	}
	
	public OpenDoor(ResultSet data, Game game) {
		super(data, game);
		init();
	}
	
	public void init() {
		currentTurn.setCurrentQuestion();
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
		
		System.out.println("Question id: " + currentTurn.getCurrentQuestion().getId());
		
		if (currentTurn.getCurrentQuestion().isPlayerAnswerCorrect(answer)) 
			amountCorrectAnswers++;
		
		updateView();
		
		if (amountCorrectAnswers == amountOfAnswers) {
			currentTurn.setTurnState(TurnState.Correct);
			DataManager.getInstance().updateTurn(currentTurn);
			getGame().getController().endTurn();
		}
	
	}
	
	public ArrayList<PlayerAnswer> getSubmittedAnswers() {
		return playerAnswers;
	}

}
