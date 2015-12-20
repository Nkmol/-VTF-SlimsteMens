package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SharedQuestion {
	
	private int gameId;
	private RoundType roundType;
	private int turnId;
	private int indexNumber;
	private int questionId;
	private String answer;

	public SharedQuestion(ResultSet data) {
		try {
			gameId = data.getInt("spel_id");
			roundType = RoundType.fromString(data.getString("rondenaam"));
			turnId = data.getInt("beurt_id");
			indexNumber = data.getInt("volgnummer");
			questionId = data.getInt("vraag_id");
			answer = data.getString("antwoord");
		} catch (SQLException e) {
			System.err.println("Error initializing shared question");
			System.err.println(e.getMessage());
		}
	}

	public int getGameId() {
		return gameId;
	}

	public RoundType getRoundType() {
		return roundType;
	}

	public int getTurnId() {
		return turnId;
	}

	public int getIndexNumber() {
		return indexNumber;
	}

	public int getQuestionId() {
		return questionId;
	}

	public String getAnswer() {
		return answer;
	}
	
	
	
}
