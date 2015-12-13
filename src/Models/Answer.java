package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Managers.DataManager;

public class Answer {

	private int questionId;
	private String answer;
	private ArrayList<String> alternatives;
	
	public Answer(int questionId, String answer, ArrayList<String> alternatives) {
		this.questionId = questionId;
		this.answer = answer;
		this.alternatives = alternatives;
	}
	
	public Answer(ResultSet data) {
		try {
			questionId = data.getInt("vraag_id");
			answer = data.getString("antwoord");
			alternatives = DataManager.getInstance().getAlternatives(questionId, answer);
		} catch (SQLException e) {
			System.err.println("Error initializing answer");
			System.err.println(e.getMessage());
		}
	}
	
	public int getQuestionId() {
		return questionId;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public ArrayList<String> getAlternatives() {
		return alternatives;
	}
}
