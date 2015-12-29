package Models;

import java.sql.ResultSet;

import Managers.DataManager;

public class OpenDoor extends Round {

	Question currentQuestion;
	
	public OpenDoor(Game game) {
		super(game, RoundType.OpenDoor);
		
		roundType = RoundType.OpenDoor;
		setCurrrentTurn(new Turn(DataManager.getInstance().getCurrentUser(), this));
	}
	
	public OpenDoor(ResultSet data, Game game) {
		super(data, game);
	}
	
	//TODO: function to check turn
	
	public void setNewQuestion() {
		// Get the view to update question with a new question
		// TODO load all questions
		/*int current = this.getQuestions().indexOf(currentQuestion);
		
		if(this.getQuestions().get(current + 1) != null){

			setCurrentQuestion(++current);

			setChanged();
			notifyObservers();
		}*/
	}
	
	public Question getCurrentQuestion(){
		return currentQuestion;
	}
	
	public void setCurrentQuestion(int index){
		/*this.currentQuestion = this.getQuestions().get(index);*/
	}


	@Override
	public void onSubmit(String answer) {
		// TODO Auto-generated method stub
		
	}

}
