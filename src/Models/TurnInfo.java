package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import Managers.DataManager;

public class TurnInfo {
	private int gameId;
	private RoundType roundType;
	private int turnId;
	private int questionId;
	private TurnState turnState;
	private Player player;
	
	public TurnInfo(ResultSet data) {
		try {
			gameId = data.getInt("spel_id");
			roundType = RoundType.fromString(data.getString("rondenaam"));
			turnId = data.getInt("beurt_id");
			questionId = data.getInt("vraag_id");
			player = DataManager.getInstance().getPlayer(data.getString("speler"));
			turnState = TurnState.fromString(data.getString("beurtstatus"));
		} catch (SQLException e) {
			System.err.println("Error initializing TurnInfo");
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
	
	public int getQuestionId() {
		return questionId;
	}
	
	public TurnState getTurnState() {
		return turnState;
	}

	public void setTurnState(TurnState turnState) {
		this.turnState = turnState;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
