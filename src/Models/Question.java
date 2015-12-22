package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Managers.DataManager;
import Utilities.StringUtility;

public class Question {
	
	private static final int MinimumAnswerPercentage = 80;
	
	private int id;
	private Round round;
	private String text;
	private ArrayList<Answer> answers;
	
	public Question(int id, Round round, String text) {
		this.id = id;
		this.round = round;
		this.text = text;
	}
	
	public Question(ResultSet data, Round round) {
		try {
			id = data.getInt("vraag_id");
			this.round = round;
			text = data.getString("vraagtekst");
			answers = DataManager.getInstance().getAnswers(id);
		} catch (SQLException e) {
			System.err.println("Error initializing quesiton");
			System.err.println(e.getMessage());
		}
	}
	
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
	
	public int getId() {
		return id;
	}
	
	public Round getRound() {
		return round;
	}
	
	public String getText() {
		return text;
	}
	
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	
}
