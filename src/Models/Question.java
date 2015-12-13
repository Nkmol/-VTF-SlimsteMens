package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Managers.DataManager;

public class Question {
	
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
