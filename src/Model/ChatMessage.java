package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import Managers.DataManager;

public class ChatMessage {

	//private Game game;
	private int gameId;
	private Timestamp timestamp;
	private int millisec;
	//private Player sender;
	private String senderName;
	private String message;
	
	public ChatMessage(int gameId, Timestamp timestamp, int millisec, String senderName, String message) {
		this.gameId = gameId;
		this.timestamp = timestamp;
		this.millisec = millisec;
		this.senderName = senderName;
		this.message = message;
	}
	
	public ChatMessage(ResultSet data) {
		try {
			this.gameId = data.getInt("spel_id");
			this.timestamp = data.getTimestamp("tijdstip");
			this.millisec = data.getInt("millisec");
			this.senderName = data.getString("account_naam_zender");
			this.message = data.getString("bericht");
		}
		catch (SQLException e) {
			System.err.println("Error initializing chat message: " + e.getMessage());
		}
	}

	public void send() {
		try {
		DataManager.getInstance().registerChatMessage(gameId, timestamp, millisec, senderName, message);
		}
		catch (Exception e) {
			System.err.println("Error sending chat message: " + e.getMessage());
		}
	}
	
}
