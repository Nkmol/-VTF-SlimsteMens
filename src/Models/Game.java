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
	
	private static final int MinimumAnswerPercentage = 80;
	
	private int id;
	private PlayerGame playerGame1; // <-- THIS IS YOU
	private PlayerGame playerGame2; 
	private GameState gameState;
	private ArrayList<Round> rounds;
	private Round currentRound;
	private ArrayList<ChatMessage> chatMessages;
	private Timer syncTimer;
	
	public Game(int gameId, Player player1, Player player2, GameState gameState) {
		this.id = gameId;
		this.playerGame1 = new PlayerGame(player1, this);
		this.playerGame2 = new PlayerGame(player2, this);
		
		this.gameState = gameState;
		rounds = DataManager.getInstance().getRounds(this);
		chatMessages = DataManager.getInstance().getChatMessages(id);
		
		start(); 
	}
	
	public Game(ResultSet data) {
		try {
			id = data.getInt("spel_id");
			playerGame1 = new PlayerGame(DataManager.getInstance().getPlayer(data.getString("speler1")), this);
			playerGame2 = new PlayerGame(DataManager.getInstance().getPlayer(data.getString("speler2")), this);
			gameState = GameState.fromString(data.getString("toestand_type"));
			rounds = DataManager.getInstance().getRounds(this);
			currentRound = DataManager.getInstance().getLastRoundForGame(this);
			chatMessages = DataManager.getInstance().getChatMessages(id);
		} catch (SQLException e) {
			System.err.println("Error initializing game");
			System.err.println(e.getMessage());
		}
		
		start();
	}
	
	public void start() {
		playerGame1.startTimer();
		startSyncTimer();
	}
	
	private void startSyncTimer() {
		System.out.println("test");
		syncTimer = new Timer();
		syncTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				checkTurn();
			}
			
		}, 0L, 1000L);
	}
	
	private void checkTurn() {
		Player player = isCurrentPlayerTurn(id) ? playerGame1.getPlayer() : playerGame2.getPlayer();

		if(isCurrentUser(player.getName()))
			playerGame1.startTimer();
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
	
	public PlayerGame getPlayerGame1() {
		return playerGame1;
	}
	
	public PlayerGame getPlayerGame2() {
		return playerGame2;
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
		
		DataManager.getInstance().pushRound(getCurrentRound());
		
		setChanged();
		notifyObservers(this);
	}
}
