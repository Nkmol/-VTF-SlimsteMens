package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import Managers.DataManager;

public class SharedQuestion extends Question {
	private int indexNumber;
	
	public SharedQuestion(int id, Turn turn, String text) {
		
		super(id, turn, text);
	}

	public SharedQuestion(Question question, int indexNumber) {
		super(question);
		this.indexNumber = indexNumber;
	}
	
	public SharedQuestion(ResultSet data, Turn turn) throws SQLException {
		super(DataManager.getInstance().getQuestionForId(data.getInt("vraag_id"), turn.getRound()));
		try {
			this.turn = turn;
			indexNumber = data.getInt("volgnummer");
		} catch (SQLException e) {
			System.err.println("Error initializing shared question");
			System.err.println(e.getMessage());
		}
	}
	
	public String getQuestionText() {
		return text;
	}

	public int getIndexNumber() {
		if(indexNumber <= 0)
			System.err.println("indexNumber for sharedQuestion is 0.");
		return indexNumber;
	}
	
	public void setIndexNumber(int value) {
		indexNumber = value;
	}
}
