package Models;

import java.util.TimerTask;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Managers.DataManager;

public class Turn {

	private int gameId;
	private RoundType roundType;
	private int turnId;
	private int questionId;
	private String playerName;
	private TurnState turnState;
	private Player player;
	private TimerTask timer;
	private int time;
	private int secondsEarnd;
	private int secondsFinal;
	private ArrayList<SharedQuestion> sharedQuestions;
	private ArrayList<PlayerAnswer> playerAnswers;

	public Turn(RoundType roundType, Player player) {
//		this.round = round; 
		this.roundType = roundType;
//		this.player = player;
	}
	
	public Turn(ResultSet data) {
		try {
			gameId = data.getInt("spel_id");
			roundType = RoundType.fromString(data.getString("rondenaam"));
			turnId = data.getInt("beurt_id");
			questionId = data.getInt("vraag_id");
			playerName = data.getString("speler");
			turnState = TurnState.fromString(data.getString("beurtstatus"));
			secondsEarnd = data.getInt("sec_verdiend");
			secondsFinal = data.getInt("sec_finale_af");
			sharedQuestions = DataManager.getInstance().getSharedQuestions(this);
			playerAnswers = DataManager.getInstance().getPlayerAnswers(this);
		} catch (SQLException e) {
			System.err.println("Error initializing Turn");
			System.err.println(e.getMessage());
		}
	}
	
	public int getGameId() {
		return gameId;
	}
	
	public RoundType getRoundType() {
		return roundType;
	}
	
	public int getTurnId() {
		return turnId;
	}
	
	public Question getQuestion() {	
		
		if (questionId > 0) {
			//TODO: get question for id
//			return DataManager.getInstance().getques
		}
		return null;
	}
	
	public Player getPlater() {
		if (playerName != null) {
			return DataManager.getInstance().getPlayer(playerName);
		}
		return null;
	}
	
	public TurnState getTurnState() {
		return turnState;
	}
	
	public int getSecondsEarned() {
		return secondsEarnd;
	}
	
	public int getSecondsFinal() {
		return secondsFinal;
	}
	
	public ArrayList<SharedQuestion> getSharedQuestions() {
		return sharedQuestions;
	}
	
	public ArrayList<PlayerAnswer> getPlayerAnswers() {
		return playerAnswers;
	}
	
	public void startTimer() {
		timer = new MyTimer().schedule(() -> time--, 1000);
	}
	
	public void stopTimer() {
		timer.cancel();
	}
	
	public void submitTurn() {
		//TODO: submit to the database
	}
	
	public int getTime() {
		return time;
	}
	
}
