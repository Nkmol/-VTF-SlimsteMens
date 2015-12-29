package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import Managers.DataManager;

public class TurnInfo {
	private TurnState turnState;
	private Player player;
	
	public TurnInfo(ResultSet data) {
		try {
			player = DataManager.getInstance().getPlayer(data.getString("speler"));
			turnState = TurnState.fromString(data.getString("beurtstatus"));
		} catch (SQLException e) {
			System.err.println("Error initializing TurnInfo");
			System.err.println(e.getMessage());
		}
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
