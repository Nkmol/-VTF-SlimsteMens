package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Managers.DataManager;
import Utilities.StringUtility;

public class Question {
	
	private static final int MinimumAnswerPercentage = 80;
	
	private int id;
	protected Turn turn;
	protected String text;
	private ArrayList<Answer> answers;

	//TODO ugly ...
	public Question(Question question) {
		text = question.getText();
		answers = question.getAnswers();
		id = question.getId();
		turn = question.getTurn();
	}
	
	public Question(int id, Turn turn, String text) {
		this.id = id;
		this.turn = turn;
		this.text = text;
	}
	
	public Question(ResultSet data, Turn turn) {
		try {
			id = data.getInt("vraag_id");
			this.turn = turn;
			text = data.getString("vraagtekst");
			answers = DataManager.getInstance().getAnswers(id);
		} catch (SQLException e) {
			System.err.println("Error initializing quesiton");
			System.err.println(e.getMessage());
		}
	}
	
/*	public Question(ResultSet data) {
		try {
			id = data.getInt("vraag_id");
			//TODO: try to get the round 
			text = data.getString("vraagtekst");
			answers = DataManager.getInstance().getAnswers(id);
		} catch (SQLException e) {
			System.err.println("Error initializing quesiton");
			System.err.println(e.getMessage());
		}
	}*/
	
	public boolean isPlayerAnswerCorrect(PlayerAnswer playerAnswer) {
		
		for(Answer answer : answers){
			
			if (StringUtility.CalculateMatchPercentage(playerAnswer.getAnswer(), answer.getAnswer()) >=  MinimumAnswerPercentage)
				return true;
			
			for (String alternative : answer.getAlternatives())
				if (StringUtility.CalculateMatchPercentage(playerAnswer.getAnswer(), alternative) >=  MinimumAnswerPercentage)
					return true;
		}
		
		return false;
	}
	
	public boolean isPlayerAnswerCorrect(String answerString) {
		for(Answer answer : answers){
			
			if (StringUtility.CalculateMatchPercentage(answerString, answer.getAnswer()) >=  MinimumAnswerPercentage)
				return true;
			
			for (String alternative : answer.getAlternatives())
				if (StringUtility.CalculateMatchPercentage(answerString, alternative) >=  MinimumAnswerPercentage)
					return true;
		}
		
		return false;
	}
	
	public static boolean isPlayerAnswerCorrect(String answerString, ArrayList<Answer> answers) {
		for(Answer answer : answers){
			
			if (StringUtility.CalculateMatchPercentage(answerString, answer.getAnswer()) >=  MinimumAnswerPercentage)
				return true;
			
			for (String alternative : answer.getAlternatives())
				if (StringUtility.CalculateMatchPercentage(answerString, alternative) >=  MinimumAnswerPercentage)
					return true;
		}
		
		return false;
	}
	
	public Answer isAnswerCorrect(String answerString) {
		for(Answer answer : answers){
			if (StringUtility.CalculateMatchPercentage(answerString, answer.getAnswer()) >=  MinimumAnswerPercentage)
				return answer;

			for (String alternative : answer.getAlternatives())
				if (StringUtility.CalculateMatchPercentage(answerString, alternative) >=  MinimumAnswerPercentage)
					return answer;
		}
		return null;
	}
	
	public int getId() {
		return id;
	}
	
	public Turn getTurn() {
		return turn;
	}
	
	public void setTurn(Turn turn) {
		this.turn = turn;
	}
	
	public String getText() {
		return text;
	}
	
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
}
