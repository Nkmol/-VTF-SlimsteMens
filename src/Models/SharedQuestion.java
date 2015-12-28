package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import Managers.DataManager;

public class SharedQuestion extends Question {
	
	private int turnId;
	public SharedQuestion(int id, Round round, String text) {
		
		super(id, round, text);
	}

	public SharedQuestion(Question question) {
		super(question);
	}
	
	public SharedQuestion(ResultSet data, Round round) throws SQLException {
		super(DataManager.getInstance().getQuestionForId(data.getInt("vraag_id")));
		try {
			this.round = round;
			indexNumber = data.getInt("volgnummer");
		} catch (SQLException e) {
			System.err.println("Error initializing shared question");
			System.err.println(e.getMessage());
		}
	}
	
	public String getQuestionText() {
		return text;
	}

	public int getTurnId() {
		return turnId;
	}
	
	public SharedQuestion setTurnId(int id) {
		turnId = id;
		
		return this; //chaining
	}
}
