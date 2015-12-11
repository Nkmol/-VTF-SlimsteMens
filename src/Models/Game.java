package Models;

import java.util.ArrayList;

import Managers.DataManager;

public class Game {
	
	private int id;
	private Player player1;
	private Player player2;
	private GameState gameState;
	private ArrayList<Round> rounds;
	private ArrayList<ChatMessage> chatMessages;
	
	public Game(int gameId, Player player1, Player player2, GameState gameState) {
		this.id = gameId;
		this.player1 = player1;
		this.player2 = player2;
		this.gameState = gameState;
		rounds = DataManager.getInstance().getRounds(this);
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
	
}