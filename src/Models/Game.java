package Models;

import java.util.ArrayList;

public class Game {
	
	private int id;
	private Player player1;
	private Player player2;
	private GameState gameState;
	private Round currentRound;
	private ArrayList<ChatMessage> chatMessages;
	
	public Game(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
	}
	
}
