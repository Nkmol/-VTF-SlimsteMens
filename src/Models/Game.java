package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

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
	private Timer syncTimer;
	
	public Game(int gameId, Player player1, Player player2, GameState gameState) {
		this.id = gameId;
		this.player1 = new PlayerGame(player1, this);
		this.player2 = new PlayerGame(player2, this);
		
		this.gameState = gameState;
		rounds = DataManager.getInstance().getRounds(this);
		chatMessages = DataManager.getInstance().getChatMessages(id);
		
		start();
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
		
		start();
	}
	
	public void start() {
		this.player1.startTimer();
		startSyncTimer();
	}
	
	private void startSyncTimer() {
		syncTimer = new Timer();
		syncTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				checkTurn();
			}
			
		}, 0L, 1000L);
	}
	
	private void checkTurn() {
		Turn lastTurn = DataManager.getInstance().getLastTurnForGame(id);
		//if(lastTurn.getPlayer().getName() != )
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
