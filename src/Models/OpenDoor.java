package Models;

import java.sql.ResultSet;
import java.util.ArrayList;

import Managers.DataManager;

public class OpenDoor extends Round {

	private ArrayList<PlayerAnswer> playerAnswers;
	private ArrayList<Answer> answersHandled;
	private static final int AMOUNT_OF_ANSWERS = 4;
	private int secondsEarned = 0;
	private static final int SECONDS_PER_CORRECT_ANSWER = 40;
	
	public OpenDoor(Game game) {
		super(game, RoundType.OpenDoor);
		initNewTurn(false);
	}
	
	public OpenDoor(ResultSet data, Game game) {
		super(data, game);
		initNewTurn(false);
	}
	
	public void initNewTurn(boolean initCurrentTurn) {
		answersHandled = new ArrayList<>();
		
		if(initCurrentTurn)
			currentTurn = initCurrentTurn(this);
		if(!continueCurrentTurn)
			DataManager.getInstance().pushTurn(currentTurn);
		currentTurn.startTimer();
		if (currentTurn.getSkippedQuestion() != null) {
			showAnswersForSkippedQuestion(currentTurn.getSkippedQuestion());
		}
		updateView();
		
	}
	
	public void showAnswersForSkippedQuestion(Question skippedQuestion) {
		ArrayList<PlayerAnswer> playerAnswers = DataManager.getInstance().getPlayerAnswers(getGame().getId(), roundType, currentTurn.getTurnId()-1);
		
		for (PlayerAnswer playerAnswer : playerAnswers) {
			Answer answerCorrect = skippedQuestion.isAnswerCorrect(playerAnswer.getAnswer());
			if (answerCorrect != null && answerIsValid(playerAnswer.getAnswer())) { // The answer is correct
				answersHandled.add(answerCorrect);
			}
		}
		
	}
	
	public ArrayList<Answer> getHandledAnswers() {
		return answersHandled;
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
		return !Question.isPlayerAnswerCorrect(answerString, answersHandled);
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
			Answer answerCorrect = currentQuestion.isAnswerCorrect(answer);
			if (answerCorrect != null && answerIsValid(answer)) { // The answer is correct
				answersHandled.add(answerCorrect);
				secondsEarned+=SECONDS_PER_CORRECT_ANSWER; // TODO: maybe remove this
				currentTurn.addSecondsEarnd(SECONDS_PER_CORRECT_ANSWER);
			}
		}		
			
		updateView();
		
		if (answersHandled.size() == AMOUNT_OF_ANSWERS) {
			currentTurn.setTurnState(TurnState.Correct);
			currentTurn.setSecondsEarned(secondsEarned);
			DataManager.getInstance().updateTurn(currentTurn);
			answersHandled = new ArrayList<>();
//			amountCorrectAnswers = 0;
			if (isCompleted())
				game.getController().loadNextRound(roundType);
			else {
				pushAnswers(playerAnswers);
				if (currentTurn.getSkippedQuestion() == null)
					getGame().getController().endTurn();
				else 
					initNewTurn(true);
			}
			
		}
	
	}

	@Override
	public void onPass() {
		currentTurn.setTurnState(TurnState.Pass);
		DataManager.getInstance().updateTurn(currentTurn);
		pushAnswers(playerAnswers);
		if (isCompleted()) 
			game.getController().loadNextRound(roundType);
		else {
			if (currentTurn.getSkippedQuestion() == null)
				getGame().getController().endTurn();
			else 
				initNewTurn(true);
		}
		
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
/*		
		if (isCompleted)
			game.getController().loadNextRound(roundType);*/
		
		return isCompleted;
	}

}
