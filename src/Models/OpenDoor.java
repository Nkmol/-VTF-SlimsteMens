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
		initNewTurn();
	}
	
	public OpenDoor(ResultSet data, Game game) {
		super(data, game);
		initNewTurn();
	}
	
	public void initNewTurn() {
		amountCorrectAnswers = 0;
		currentTurn = initCurrentTurn(this);
		DataManager.getInstance().pushTurn(currentTurn);
		currentTurn.startTimer();
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

	@Override
	public void onSubmit(String answer) {
		
		if (playerAnswers == null)
			playerAnswers = new ArrayList<>();
		
		int answerId = playerAnswers.size() + 1;
		PlayerAnswer playerAnswer = new PlayerAnswer(currentTurn, answerId, answer, currentTurn.getMoment());
//		DataManager.getInstance().pushPlayerAnswer(playerAnswer);
		playerAnswers.add(playerAnswer);
		
		Question currentQuestion = (currentTurn.getSkippedQuestion() != null) ? currentTurn.getSkippedQuestion() : currentTurn.getCurrentQuestion();
		
		System.out.println("Question id: " + currentQuestion.getId());
		
		if (currentQuestion != null) {
			if (currentQuestion.isPlayerAnswerCorrect(answer)) {
				amountCorrectAnswers++; 
				secondsEarned+=SECONDS_PER_CORRECT_ANSWER; // TODO: maybe remove this
				currentTurn.addSecondsEarnd(SECONDS_PER_CORRECT_ANSWER);
			}
		}		
			
		updateView();
		
		if (amountCorrectAnswers == AMOUNT_OF_ANSWERS) {
			currentTurn.setTurnState(TurnState.Correct);
			currentTurn.setSecondsEarned(secondsEarned);
			DataManager.getInstance().updateTurn(currentTurn);
			amountCorrectAnswers = 0;
			if (isCompleted())
				game.getController().loadNextRound(roundType);
			pushAnswers(playerAnswers);
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
		pushAnswers(playerAnswers);
		if (isCompleted()) 
			game.getController().loadNextRound(roundType);
		if (currentTurn.getSkippedQuestion() == null)
			getGame().getController().endTurn();
		else 
			initNewTurn();
	}
	
	public ArrayList<PlayerAnswer> getSubmittedAnswers() {
		return playerAnswers;
	}
	
	public boolean thereAreBusyTurns(ArrayList<TurnInfo> turnInfos) {
		ArrayList<TurnInfo> busyTurnInfos = new ArrayList<>();
		for (TurnInfo turnInfo : turnInfos) {
			if (turnInfo.getTurnState() == TurnState.Busy)
				busyTurnInfos.add(turnInfo);
		}
		return busyTurnInfos.size() > 0;
	}

	@Override
	public boolean isCompleted() {
		
		boolean isCompleted = false;
		
		ArrayList<TurnInfo> turnInfos = DataManager.getInstance().getTurnInfosForRound(this);		
		
		if (turnInfos != null && turnInfos.size() >= 2) {
			if (turnInfos.size() >= 4 && !thereAreBusyTurns(turnInfos))
				isCompleted = true;
			if (turnInfos.size() == 3) {
				TurnState firstTurnState = turnInfos.get(0).getTurnState();
				TurnState thirdTurnState = turnInfos.get(2).getTurnState();
				isCompleted =  ((firstTurnState == TurnState.Pass && thirdTurnState == TurnState.Correct) || 
						(firstTurnState == TurnState.Correct && thirdTurnState == TurnState.Pass));
			}
			if (turnInfos.size() == 2) {
				if (turnInfos.get(0).getTurnState() == TurnState.Correct && turnInfos.get(1).getTurnState() == TurnState.Correct) 
					isCompleted =  true;
			}
		}
		
		if (isCompleted)
			game.getController().loadNextRound(roundType);
		
		return isCompleted;
	}
	

}
