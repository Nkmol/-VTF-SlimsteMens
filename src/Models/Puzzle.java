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
	private ArrayList<Question> questions;
	
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
		else if(lastTurn != null && lastTurn.getTurnState() == TurnState.Pass && !Game.isCurrentUser(lastTurn.getPlayer().getName())) // start new turn with a previous turn of puzzle played -> load previous questions
			sharedQuestions = lastTurn.getSharedQuestions();
		else { // start new turn without a previous turn -> load new questions
			questions = new ArrayList<Question>();
			questions = loadQuestions();
			for(int i = 0; i < questions.size(); i++) 
				sharedQuestions.add(new SharedQuestion(questions.get(i), i+1));
		}
		
		//TODO puzzle round is played ONCE each player. When one player passes the other can continue with his turn

		return sharedQuestions;
	}
	
	private ArrayList<Question> loadQuestions() {
		ArrayList<Question> questions = new ArrayList<Question>();
		
		for(int i = 0; i < AMOUNT_QUESTIONS; i++) {
			Question q = DataManager.getInstance().getRandomQuestionForRoundType(currentTurn);
			if(!containsQuestion(q))
				questions.add(q);
			else
				i--; //Step back -> generate new question
			System.out.println(questions.get(i).getId());
		}
		
		return questions;
	}

	private boolean containsQuestion(Question q) {
		for(Question question : questions)
			if(question.getId() == q.getId())
				return true;
		return false;
	}
	
	@Override
	public void onSubmit(String answer) {
		// TODO Auto-generated method stub
		System.out.println("your answer is " + answer);
		
		Question questionCorrect = null;
		for(Question question : questions) 
			if (question.isPlayerAnswerCorrectPuzzle(answer)) {
				questionCorrect = question;
				break;
			}
		
		if(questionCorrect != null)
			pushTurn(questionCorrect, TurnState.Correct, answer);
		//TODO ELSE - wrong answer (?)
				
		if(currentTurn.getAmountAnswers() == 3)
			endTurn();
		updateView();
	}

	public void pushTurn(Question q, TurnState turnState, String answer) { //TODO merge with Turn.pushTurn ??
		currentTurn.setTurnState(turnState);
		
		DataManager.getInstance().updateTurn(currentTurn);
		currentTurn.addPlayerAnswer(answer);
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
	
	public ArrayList<Question> getQuestions() {
		return questions;
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
