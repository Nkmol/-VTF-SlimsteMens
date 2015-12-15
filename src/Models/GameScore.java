package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameScore {
	
	private int gameId;
	private String player1Name;
	private String player2Name;
	private GameState gameState;
	private int player1Seconds;
	private int player2Seconds;

	public GameScore(ResultSet data) {
		try {
			gameId = data.getInt("spel_id");
			player1Name = data.getString("speler1");
			player2Name = data.getString("speler2");
			gameState = GameState.fromString(data.getString("toestand_type"));
			player1Seconds = data.getInt("seconden1");
			player2Seconds = data.getInt("seconden2");
		} catch (SQLException e) {
			System.err.println("Error initializing GameScore");
			System.err.println(e.getMessage());
		}
	}

	public int getGameId() {
		return gameId;
	}

	public String getPlayer1Name() {
		return player1Name;
	}

	public String getPlayer2Name() {
		return player2Name;
	}

	public GameState getGameState() {
		return gameState;
	}

	public int getPlayer1Seconds() {
		return player1Seconds;
	}

	public int getPlayer2Seconds() {
		return player2Seconds;
	}
	
	public boolean isDraw() {
		return (player1Seconds == player2Seconds);
	}
	
	public String getWinner() {
		if (player1Seconds != player2Seconds) 
			return (player1Seconds > player2Seconds) ? player1Name : player2Name;
		return null;
	}
	
}
