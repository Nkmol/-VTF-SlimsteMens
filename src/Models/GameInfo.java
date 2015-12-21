package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import Managers.DataManager;

public class GameInfo {
	
	private int gameId;
	private Player player1;
	private Player player2;
	private GameState gameState;
	
	public GameInfo(ResultSet data) {
		try {
			gameId = data.getInt("spel_id");
			player1 = DataManager.getInstance().getPlayer(data.getString("speler1"));
			player2 = DataManager.getInstance().getPlayer(data.getString("speler2"));
			gameState = GameState.fromString(data.getString("toestand_type"));
		} catch (SQLException e) {
			System.err.println("Error initializing game info");
			System.err.println(e.getMessage());
		}
	}

	public int getGameId() {
		return gameId;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public GameState getGameState() {
		return gameState;
	}

}
