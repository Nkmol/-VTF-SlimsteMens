package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import Managers.DataManager;
import Utilities.StringUtility;

public class Game extends Observable {
	
	private static final int MinimumAnswerPercentage = 80;
	
	private int id;
	private Player player1;
	private Player player2;
	private GameState gameState;
	private ArrayList<Round> rounds;
	private ArrayList<ChatMessage> chatMessages;
	
	private int time1, time2;
	
	public int getTime1() {
		return time1;
	}
	
	public int getTime2() {
		return time1;
	}
	
	public Game(int gameId, Player player1, Player player2, GameState gameState) {
		this.id = gameId;
		this.player1 = player1;
		this.player2 = player2;
		this.gameState = gameState;
		rounds = DataManager.getInstance().getRounds(this);
		chatMessages = DataManager.getInstance().getChatMessages(id);
	}
	
	public Game(ResultSet data) {
		try {
			id = data.getInt("spel_id");
			player1 = DataManager.getInstance().getPlayer(data.getString("speler1"));
			player2 = DataManager.getInstance().getPlayer(data.getString("speler2"));
			gameState = GameState.fromString(data.getString("toestand_type"));
			rounds = DataManager.getInstance().getRounds(this);
			chatMessages = DataManager.getInstance().getChatMessages(id);
		} catch (SQLException e) {
			System.err.println("Error initializing game");
			System.err.println(e.getMessage());
		}
	}
	
	public void updateView() {
		setChanged();
		notifyObservers(this);
	}
	
	public static boolean isPlayerAnswerCorrect(PlayerAnswer player, Answer answer) {
		
		if (StringUtility.CalculateMatchPercentage(player.getAnswer(), answer.getAnswer()) >=  MinimumAnswerPercentage)
			return true;
		
		for (String alternative : answer.getAlternatives())
			if (StringUtility.CalculateMatchPercentage(player.getAnswer(), alternative) >=  MinimumAnswerPercentage)
				return true;
		
		return false;
	}
	
	public int getId() {
		return id;
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
	
	public ArrayList<Round> getRounds() {
		return rounds;
	}
	
	public ArrayList<ChatMessage> getChatMessages() {
		return chatMessages;
	}
}
