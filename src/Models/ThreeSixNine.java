package Models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Observable;

public class ThreeSixNine extends Round {

	private Question currentQuestion;
	
	public ThreeSixNine(Game game) {
		super(game);
		
		currentQuestion = this.getQuestions().get(0);
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



}
