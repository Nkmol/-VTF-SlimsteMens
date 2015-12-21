package Models;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ThreeSixNine extends Round {

	private Question currentQuestion;
	
	public ThreeSixNine(Game game) {
		super(game);
	}
	
	public ThreeSixNine(ResultSet data, Game game) {
		super(data, game);
	}
	
	public Boolean checkTurn(){
		// TODO
		return false;
	}
	
	public Boolean isAnswerCorrect(int questionID, String Answer, ArrayList<Answer> answers){
		
		for(Answer answer : answers){
			if(answer.getQuestionId() == questionID){
				if(answer.getAnswer().equals(Answer)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void setNewQuestion(){
		// Get the view to update question with a new question
		
		setChanged();
		notifyObservers();
	}
	
	public Question getCurrentQuestion(){
		return currentQuestion;
	}

}
