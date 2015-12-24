package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import Managers.DataManager;

public class ThreeSixNine extends Round {

	private final static int AMOUNT_QUESTIONS = 9,
							 BONUS_ITERATION = 3;
	
	public ThreeSixNine(Game game) {
		super(game, RoundType.ThreeSixNine);
		questions = DataManager.getInstance().getQuestions(this);
		currentTurn.setCurrentQuestion(questions);
	}
	
	public ThreeSixNine(ResultSet data, Game game) {
		super(data, game);
	}
	
	public Boolean checkTurn(){
		// TODO later naar kijken
		return false;
	}
	
	public void setNewCurrentQuestion() {
		currentTurn.setCurrentQuestion(questions);
		setChanged();
		notifyObservers();
	}
	
//	public void setNewQuestion(){
//		// Get the view to update question with a new question
//		int current = this.getQuestions().indexOf(currentQuestion);
//		
//		if(this.getQuestions().get(current + 1) != null){
//
//			setCurrentQuestion(++current);
//
//			setChanged();
//			notifyObservers();
//		}
//	}
	
	public Question getSkippedQuestion() {
		return skippedQuestion;
	}
	
	public void setSkippedQuestion(Question quesiton) {
		skippedQuestion = quesiton;
	}

	@Override
	public void onSubmit(String answer) {
		System.out.println("your answers is " + answer);
		int index = (currentTurn.getSharedQuestions() != null) ? currentTurn.getSharedQuestions().size() + 1 : 1;
		currentTurn.addSharedQuestion(new SharedQuestion(currentTurn, index, answer));
		if (currentTurn.getCurrentQuestion().isPlayerAnswerCorrect(answer)) 
			currentTurn.setTurnState(TurnState.Correct);
		else 
			currentTurn.setTurnState(TurnState.Wrong);
		DataManager.getInstance().updateTurn(currentTurn);
		try {
			DataManager.getInstance().pushSharedQuestion(currentTurn);
		} catch (SQLException e) {
			System.err.println("Error pushing shared question");
			System.err.println(e.getMessage());
		}
	}
}
