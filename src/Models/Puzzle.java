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
	private ArrayList<PlayerAnswer> playerAnswers;
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
		playerAnswers = new ArrayList<PlayerAnswer>();
		questions = new ArrayList<Question>();
		questions = loadQuestions();
		updateView();
		
		if(!continueCurrentTurn)
			DataManager.getInstance().pushTurn(currentTurn);
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
		for(Question question : questions) 
			if (question.isPlayerAnswerCorrectPuzzle(answer)) {
				System.out.println("correct");
				break;
			}
			else 
				System.out.println("wrong");

		updateView();
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
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}
}
