package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import Managers.DataManager;
import Utilities.StringUtility;

public class Game extends Observable {
	
	private static final int MinimumAnswerPercentage = 80;
	
	private int id;
	private PlayerGame player1;
	private PlayerGame player2;
	private GameState gameState;
	private ArrayList<Round> rounds;
	private Round currentRound;
	private ArrayList<ChatMessage> chatMessages;
	
	public Game(int gameId, Player player1, Player player2, GameState gameState) {
		this.id = gameId;
		this.player1 = new PlayerGame(player1, this);
		this.player2 = new PlayerGame(player2, this);
		
		this.gameState = gameState;
		rounds = DataManager.getInstance().getRounds(this);
		chatMessages = DataManager.getInstance().getChatMessages(id);
		
		this.player1.startTimer();
	}
	
	public Game(ResultSet data) {
		try {
			id = data.getInt("spel_id");
			player1 = new PlayerGame(DataManager.getInstance().getPlayer(data.getString("speler1")), this);
			player2 = new PlayerGame(DataManager.getInstance().getPlayer(data.getString("speler2")), this);
			gameState = GameState.fromString(data.getString("toestand_type"));
			rounds = DataManager.getInstance().getRounds(this);
			chatMessages = DataManager.getInstance().getChatMessages(id);
		} catch (SQLException e) {
			System.err.println("Error initializing game");
			System.err.println(e.getMessage());
		}
		
		this.player1.startTimer();
	}
	
	public void updateView() {
		setChanged();
		notifyObservers(this);
	}
	
	
	public Player toggleCurrentPlayer() {
		if(player1.isActive) {
			player2.isActive = true;
			player1.isActive = false;
			player1.stopTimer();
			player2.startTimer();
			
			return player2.getPlayer();
		}
		else {
			player2.isActive = false;
			player1.isActive = true;
			player2.stopTimer();
			player1.startTimer();
			
			return player1.getPlayer();
		}
	}
	
	public static boolean isPlayerAnswerCorrect(PlayerAnswer player, Answer answer) {
		return IsPlayerAnswerCorrect(player.getAnswer(), answer);
	}
	
	public static boolean IsPlayerAnswerCorrect(String playerAnswer, Answer answer) {
		if (StringUtility.CalculateMatchPercentage(playerAnswer, answer.getAnswer()) >=  MinimumAnswerPercentage)
			return true;
		for (String alternative : answer.getAlternatives())
			if (StringUtility.CalculateMatchPercentage(playerAnswer, alternative) >=  MinimumAnswerPercentage)
				return true;
		return false;
	}
	
	public static boolean IsPlayerAnswerCorrect(String playerAnswer, Collection<Answer> answers) {
		for (Answer answer : answers) {
			if (IsPlayerAnswerCorrect(playerAnswer, answer))
				return true;
		}
		return false;
	}
	
	public int getId() {
		return id;
	}
	
	public PlayerGame getPlayerGame1() {
		return player1;
	}
	
	public PlayerGame getPlayerGame2() {
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
	
	public Round getCurrentRound() {
		return currentRound;
	}
	
	public void setCurrentRound(Round round) {		
		//TODO: add to Rounds<>
		currentRound = round;	
	}
	
	public void nextRound() {
		currentRound = Round.createRound(RoundType.nextRoundType(currentRound.getRoundType()), this);
	}

	public void addRound(Round model) {
		rounds.add(model);
		setCurrentRound(model);
		
		setChanged();
		notifyObservers(this);
	}
}
