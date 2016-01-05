package Models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import Managers.DataManager;

public class Puzzle extends Round {


	public final static int AMOUNT_QUESTIONS = 3,
							CORRECT_POINTS = 30;
	
	private int amountCorrectAnswers = 0;
	private int secondsEarned = 0;
	protected ArrayList<Answer> answersHandled;
	
	public Puzzle(Game game) {
		super(game, RoundType.Puzzle);	
		if (game.getGameState() != GameState.Finished)
			initNewTurn();
	}
	
	public Puzzle(ResultSet data, Game game) {
		super(data,game);
		if (game.getGameState() != GameState.Finished)
			initNewTurn();
	}
	
	public void initNewTurn() {
		currentTurn.startTimer();
		answersHandled = new ArrayList<Answer>();
		currentTurn.setSharedQuestions(generateSharedQuestions());
		showAnswersForSkippedQuestion();
		updateView();
		
		if(!continueCurrentTurn) {
			DataManager.getInstance().pushTurn(currentTurn);
			DataManager.getInstance().pushSharedQuestions(currentTurn);
		}
	}

	public ArrayList<Answer> getHandledAnswers() {
		return answersHandled;
	}
	
	private ArrayList<SharedQuestion> generateSharedQuestions() {
		ArrayList<SharedQuestion> sharedQuestions = new ArrayList<SharedQuestion>();
		if(continueCurrentTurn) // Continue turn -> load current sharedQuestions
			sharedQuestions = currentTurn.getSharedQuestions();
		else if(lastTurn != null && lastTurn.getTurnState() == TurnState.Pass && !Game.isCurrentUser(lastTurn.getPlayer().getName())) { // start new turn with a previous turn of puzzle played -> load previous questions
			sharedQuestions = lastTurn.getSharedQuestions();
			currentTurn.setPlayerAnswers(lastTurn.getPlayerAnswers());
		}
		else { // start new turn without a previous turn -> load new questions
			sharedQuestions = loadNewQuestions();
		}

		return sharedQuestions;
	}
	
	public void showAnswersForSkippedQuestion() {
		if(lastTurn != null) {
			ArrayList<PlayerAnswer> playerAnswers = lastTurn.getPlayerAnswers();
			
			for (PlayerAnswer playerAnswer : playerAnswers) {
				for(SharedQuestion sharedQuestion : currentTurn.getSharedQuestions()) {
					Answer answerCorrect = sharedQuestion.isAnswerCorrect(playerAnswer.getAnswer());
					if (answerCorrect != null && answerIsValid(playerAnswer.getAnswer())) { // The answer is correct
						answersHandled.add(answerCorrect);
						break;
					}
				}
			}	
		}
	}
	
	private boolean answerIsValid(String answerString) {
		return !Question.isPlayerAnswerCorrect(answerString, answersHandled);
	}
	
	private ArrayList<SharedQuestion> loadNewQuestions() {
		ArrayList<SharedQuestion> sharedQuestions = new ArrayList<SharedQuestion>();
		
		for(int i = 0; i < AMOUNT_QUESTIONS; i++) {
			Question q = DataManager.getInstance().getRandomQuestionForRoundType(currentTurn);
			if(!containsQuestion(q, sharedQuestions))
				sharedQuestions.add(new SharedQuestion(q, i+1));
			else
				i--; //Step back -> generate new question
			
			System.out.println(sharedQuestions.get(i).getId());
		}
		
		return sharedQuestions;
	}

	private boolean containsQuestion(Question q, ArrayList<SharedQuestion> SharedQuestions) {
		for(SharedQuestion sharedQuestion : SharedQuestions)
			if(sharedQuestion.getId() == q.getId())
				return true;
		return false;
	}
	
	@Override
	public void onSubmit(String answer) {
		// TODO Auto-generated method stub
		System.out.println("your answer is " + answer);
		
		SharedQuestion questionCorrect = null;
		for(SharedQuestion sharedQuestion : currentTurn.getSharedQuestions()) 
			if (sharedQuestion.isPlayerAnswerCorrectPuzzle(answer) && !sharedQuestion.hasAnswer(answer)) {
				questionCorrect = sharedQuestion;
				break;
			}
		
		if(questionCorrect != null) {
			currentTurn.addSecondsEarnd(CORRECT_POINTS);
			currentTurn.addPlayerAnswer(answer);
		}
		//TODO ELSE - wrong answer (?)
				
		if(currentTurn.getAmountAnswers() == 3) {
			pushPlayerAnswers();
			currentTurn.setTurnState(TurnState.Correct);
			DataManager.getInstance().updateTurn(currentTurn); // TODO only when turn is ending
			
			if(!isCompleted()) 
				endTurn();
			else
				game.getController().loadNextRound(roundType);
		}
		updateView();
	}
	
	public void endTurn() {
		if(lastTurn != null && lastTurn.getTurnState() == TurnState.Pass && !Game.isCurrentUser(lastTurn.getPlayer().getName())) {
			currentTurn = initCurrentTurn(this);
			initNewTurn();
		}
		else
			getGame().getController().endTurn();
	}
	
	@Override
	public void onPass() {
		currentTurn.setTurnState(TurnState.Pass);
		DataManager.getInstance().updateTurn(currentTurn);
		pushPlayerAnswers();
		if(!isCompleted()) 
			endTurn();
		else
			game.getController().loadNextRound(roundType);

	}
	
	private void pushPlayerAnswers() {
		if(currentTurn.getAmountAnswers() > 0)
			for(PlayerAnswer pa : currentTurn.getPlayerAnswers())
				DataManager.getInstance().pushPlayerAnswer(pa);
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
		//		return Game.isCurrentUser(lastTurn.getPlayer().getName()) || !Game.isCurrentUser(lastTurn.getPlayer().getName()) && lastTurn.getTurnState() != TurnState.Pass;
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

	@Override
	public void playerTimeIsOver() {
		// TODO Auto-generated method stub
		getGame().getController().endTurn();
		getGame().stopGame();
	}

}
