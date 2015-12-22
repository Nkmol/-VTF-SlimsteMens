package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Observable;

import Managers.DataManager;

public class ChatMessage extends Observable {

		private int gameId;
		private Timestamp timestamp;
		private int millisec;
		private String senderName;
		private String message;
		
		public ChatMessage(int gameId, Timestamp timestamp, int millisec, String senderName, String message) {
			this.gameId = gameId;
			this.timestamp = currentTimeStamp();
			this.millisec = currentTimeMillis();
			this.senderName = senderName;
			this.message = message;
		}
		
		public int currentTimeMillis() {
		    return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
		}
		
	    public Timestamp currentTimeStamp()
	    {
		  Date date = new Date();
		  return new Timestamp(date.getTime());
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
			DataManager.getInstance().pushChatMessage(gameId, timestamp, millisec, senderName, message);
			
			setChanged();
			notifyObservers();
			}
			catch (Exception e) {
				System.err.println("Error sending chat message: " + e.getMessage());
			}
		}
	
}