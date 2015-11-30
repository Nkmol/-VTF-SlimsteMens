package Models;

public class ChatMessage {

	private Game game;
	private int timestamp;
	private int millisec;
	private Player sender;
	private String message;
	
	public ChatMessage(Game game, int timestamp, int millisec, Player sender, String message) {
		this.game = game;
		this.timestamp = timestamp;
		this.millisec = millisec;
		this.sender = sender;
		this.message = message;
	}
	
	public void send() {
		
	}
	
}
