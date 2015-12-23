package Models;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ThreeSixNine extends Round {

	private final static int AMOUNT_QUESTIONS = 9,
							 BONUS_ITERATION = 3;
	private ArrayList<Question> questions;
	private Question currentQuestion;
	
	public ThreeSixNine(Game game) {
		super(game);
		
		roundType = RoundType.ThreeSixNine;
		//TODO Push to database
		setCurrrentTurn(new Turn(roundType, game.getPlayerGame2().getPlayer(), game.getId()));
	}
	
	public ThreeSixNine(ResultSet data, Game game) {
		super(data, game);
	}
	
	public Boolean checkTurn(){
		// TODO later naar kijken
		return false;
	}
	
	public void setNewQuestion(){
		// Get the view to update question with a new question
		int current = this.getQuestions().indexOf(currentQuestion);
		
		if(this.getQuestions().get(current + 1) != null){

			setCurrentQuestion(++current);

			setChanged();
			notifyObservers();
		}
	}
	
	public Question getCurrentQuestion(){
		return currentQuestion;
	}
	
	public void setCurrentQuestion(int index){
		this.currentQuestion = this.getQuestions().get(index);
	}

	@Override
	public void onSubmit() {
		System.out.println("test");
	}
}
