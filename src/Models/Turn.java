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
	private int secondsFinalLost;
	private ArrayList<SharedQuestion> sharedQuestions;
	private ArrayList<PlayerAnswer> playerAnswers;

	public Turn(RoundType roundType, Player player) {
//		this.round = round; 
		this.roundType = roundType;
//		this.player = player;
	}
	
	public Turn(RoundType rondeType, Player player, int gameId) {
		this.roundType = rondeType;
		this.player = player; //TODO Should become PlayerGame?
		this.gameId = gameId;
		
		playerAnswers = new ArrayList<PlayerAnswer>();
		sharedQuestions = new ArrayList<SharedQuestion>();
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
			secondsFinalLost = data.getInt("sec_finale_af");
			sharedQuestions = DataManager.getInstance().getSharedQuestions(this);
			//TODO: turn id
			playerAnswers = DataManager.getInstance().getPlayerAnswers(gameId, roundType, turnId);
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
	
	public void setTurnId(int turnId) {
		this.turnId = turnId;
	}
	
	public Question getQuestion() {	
		
		if (questionId > 0) {
			//TODO: get question for id
//			return DataManager.getInstance().getques
		}
		return null;
	}
	
	public int getQuestionId() {
		return questionId;
	}
	
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public TurnState getTurnState() {
		return turnState;
	}
	
	public void setTurnState(TurnState state) {
		turnState = state;
	}
	
	public int getSecondsEarned() {
		return secondsEarnd;
	}
	
	public void setSecondsEarned(int secondsEarned) {
		this.secondsEarnd = secondsEarned;
	}
	
	public int getSecondsFinalLost() {
		return secondsFinalLost;
	}
	
	public void setSecondsFinalLost(int secondsLost) {
		secondsFinalLost = secondsLost;
	}
	
	public ArrayList<SharedQuestion> getSharedQuestions() {
		return sharedQuestions;
	}
	
	public void setSharedQuestions(ArrayList<SharedQuestion> sharedQuestions) {
		this.sharedQuestions = sharedQuestions;
	}
	
	public ArrayList<PlayerAnswer> getPlayerAnswers() {
		return playerAnswers;
	}
	
	public void setPlayerAnswers(ArrayList<PlayerAnswer> playerAnswers) {
		this.playerAnswers = playerAnswers;
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
	
	public void addPlayerAnswer(PlayerAnswer answer) {
		playerAnswers.add(answer);
	}
	
	public int getAmountAnswers() {
		System.out.println(playerAnswers);
		//return playerAnswers == null ? 0 : playerAnswers.size();
		return 1;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
}
