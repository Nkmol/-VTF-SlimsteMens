package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import Managers.DataManager;
import Utilities.StringUtility;

public class Game extends Observable {
	
	private static final int MinimumAnswerPercentage = 80,
							 BeginAmountTime = 100;
	
	
	private int id;
	private Player player1; // <-- THIS IS YOU
	private Player player2; 
	private GameState gameState;
	private ArrayList<Round> rounds;
	private Round currentRound;
	private ArrayList<ChatMessage> chatMessages;
	
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
			//rounds = DataManager.getInstance().getRounds(this); // TODO only needed on replay?
			currentRound = DataManager.getInstance().getLastRoundForGame(this);
			chatMessages = DataManager.getInstance().getChatMessages(id);
		} catch (SQLException e) {
			System.err.println("Error initializing game");
			System.err.println(e.getMessage());
		}
	}
	
	public static boolean isCurrentUser(String playerName) {
		return playerName.equals(DataManager.getInstance().getCurrentUser().getName());
	}
	
	public static boolean isCurrentPlayerTurn(int gameId) {
		Turn lastTurn = DataManager.getInstance().getLastTurnForGame(gameId);
		if(lastTurn != null) {
			if(!isCurrentUser(lastTurn.getPlayerName()) && lastTurn.getTurnState() != TurnState.Busy) 
				return true;
			else if(isCurrentUser(lastTurn.getPlayerName()) && lastTurn.getTurnState() == TurnState.Busy)
				return true;
			else 
				return false; 
		}
		
		return false;	
	}
	
	public void updateView() {
		setChanged();
		notifyObservers(this);
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
		if (rounds == null)
			rounds = new ArrayList<>();
		rounds.add(model);
		setCurrentRound(model);
		
		//DataManager.getInstance().pushRound(getCurrentRound());
		//DataManager.getInstance().pushTurn(getCurrentRound().getCurrentTurn());
		
		setChanged();
		notifyObservers(this);
	}
	
	public void setRound(Round model) {
		setCurrentRound(model);
	}
}
