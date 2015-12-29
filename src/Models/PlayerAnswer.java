package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerAnswer {

	private int gameId;
	private RoundType roundType;
	private int turnId;
	private int answerId;
	private String answer;
	private int moment;
	
	public PlayerAnswer(Turn turn, int answerId, String answer, int moment) {
		this.gameId = turn.getGameId();
		this.roundType = turn.getRound().getRoundType();
		this.turnId = turn.getTurnId();
		this.answerId = answerId;
		this.answer = answer;
		this.moment = moment;
	}
	
	public PlayerAnswer(ResultSet data) {
		try {
			gameId = data.getInt("spel_id");
			roundType = RoundType.fromString(data.getString("rondenaam"));
			turnId = data.getInt("beurt_id");
			answerId = data.getInt("antwoord_id");
			answer = data.getString("antwoord");
			moment = data.getInt("moment");
		} catch (SQLException e) {
			System.err.println("Error initialzing PlayerAnswer");
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
	
	public int getAnswerId() {
		return answerId;
	}

	public String getAnswer() {
		return answer;
	}

	public int getMoment() {
		return moment;
	}
	
	public void submit(Turn turn) {
		//TODO: extract round name and turn id from turn
	}
	
}
