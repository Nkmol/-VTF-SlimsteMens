package Models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import Managers.DataManager;

public class Puzzle extends Round {


	public final static int AMOUNT_QUESTIONS = 3,
							CORRECT_POINTS = 60;
	
	private int amountCorrectAnswers = 0;
	private int secondsEarned = 0;
	
	public Puzzle(Game game) {
		super(game, RoundType.Puzzle);	
		initNewTurn();
	}
	
	public Puzzle(ResultSet data, Game game) {
		super(data,game);
		initNewTurn();
	}
	
	public void initNewTurn() {
		currentTurn.setSharedQuestions(generateSharedQuestions());
		updateView();
		
		if(!continueCurrentTurn) {
			DataManager.getInstance().pushTurn(currentTurn);
			DataManager.getInstance().pushSharedQuestions(currentTurn);
		}
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
			endTurn();
			currentTurn.setTurnState(TurnState.Correct);
			DataManager.getInstance().updateTurn(currentTurn); // TODO only when turn is ending
		}
		updateView();
	}
	
	public void endTurn() {
		pushPlayerAnswers();
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
		endTurn();
	}
	
	private void pushPlayerAnswers() {
		if(currentTurn.getAmountAnswers() > 0)
			for(PlayerAnswer pa : currentTurn.getPlayerAnswers())
				DataManager.getInstance().pushPlayerAnswer(pa);
	}
	
	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}
}
