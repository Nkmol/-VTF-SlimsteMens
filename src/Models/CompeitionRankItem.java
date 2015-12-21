package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompetitionRankItem {

	private String player;
	private int amountPlayedGames;
	private int amountGamesWon;
	private int amountGamesLost;
	private int averageSecondsLeft;
	
	public CompetitionRankItem(ResultSet data) {
		try {
			player = data.getString("speler");
			amountPlayedGames = data.getInt("aantal_gespeelde_spellen");
			amountGamesWon = data.getInt("aantal_gewonnen_spellen");
			amountGamesLost = data.getInt("aantal_verloren_spellen");
			averageSecondsLeft = data.getInt("gemiddeld_aantal_seconden_over");
		} catch (SQLException e) {
			System.err.println("Error initializing competition rank item");
			System.err.println(e.getMessage());
		}
	}

	public String getPlayer() {
		return player;
	}

	public int getAmountPlayedGames() {
		return amountPlayedGames;
	}

	public int getAmountGamesWon() {
		return amountGamesWon;
	}

	public int getAmountGamesLost() {
		return amountGamesLost;
	}

	public int getAverageSecondsLeft() {
		return averageSecondsLeft;
	}
	
	
	
}
