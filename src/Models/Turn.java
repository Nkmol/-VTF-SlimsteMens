 package Models;

import java.util.TimerTask;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Managers.DataManager;

public class Turn {

	private int gameId;
	private int turnId;
	private String playerName;
	private TurnState turnState;
	private Player player;
	private TimerTask timer;
	private int time;
	private int secondsEarnd;
	private Integer secondsFinalLost;
	private ArrayList<SharedQuestion> sharedQuestions;
	private ArrayList<PlayerAnswer> playerAnswers;
	private Question currentQuestion;
	private SharedQuestion sharedQuestion;
	private Round parent;
	
	public Turn(Player player, Round parentRound) {
		this.player = player;
		this.parent = parentRound;
		this.gameId = parent.getGame().getId();
		this.sharedQuestion = DataManager.getInstance().getLastSharedQuestion(this);
		this.currentQuestion = DataManager.getInstance().getRandomQuestionForRoundType(this.getRound());
//		this.sharedQuestions = DataManager.getInstance().getSharedQuestions(parentRound, turnId);
	}
	
/*	public Turn(ResultSet data) { //TODO dont use this constructor anymore -> Conficts with ActiveGame as it has no rounds/games to init
		readResultSet(data);
		this.parent = DataManager.getInstance().g
	}*/

	public Turn(ResultSet data, Round parentRound) { //TODO parent should already be made, so better to already use it right away?
		this.parent = parentRound;
		readResultSet(data);
	}
	
	private void readResultSet(ResultSet data) {
		try {
			gameId = data.getInt("spel_id");
			turnId = data.getInt("beurt_id");
			//questionId = data.getInt("vraag_id");
			playerName = data.getString("speler");
			turnState = TurnState.fromString(data.getString("beurtstatus"));
			secondsEarnd = data.getInt("sec_verdiend");
			secondsFinalLost = data.getInt("sec_finale_af");
			currentQuestion = (SharedQuestion) DataManager.getInstance().getQuestionForId(data.getInt("vraag_id"), parent);
			sharedQuestions = DataManager.getInstance().getSharedQuestions(parent, turnId);
			setCurrentQuestion();
			//TODO: turn id
			playerAnswers = DataManager.getInstance().getPlayerAnswers(gameId, parent.getRoundType(), turnId);
		} catch (SQLException e) {
			System.err.println("Error initializing Turn");
			System.err.println(e.getMessage());
		}
	}
	
	public void startTurn() {
		playerAnswers = new ArrayList<PlayerAnswer>();
		sharedQuestions = new ArrayList<SharedQuestion>();
		
		startTimer();
	}
	
	public int getGameId() {
		return gameId;
	}
	
	public int getTurnId() {
		return turnId;
	}
	
	public void setTurnId(int turnId) {
		this.turnId = turnId;
	}
	
	public Question getCurrentQuestion(){
		return currentQuestion;
	}
	
	public SharedQuestion getSharedQuestion() {
		return sharedQuestion;
	}
	
	public void setCurrentQuestion(Question question) {
		currentQuestion = question;
	}
	
	public void setSharedQuestion(SharedQuestion sharedQuestion) {
		this.sharedQuestion = sharedQuestion;
	}
	
	public void setCurrentQuestion(ArrayList<SharedQuestion> sharedQuestions) {
		currentQuestion = sharedQuestions.get(sharedQuestions.size() - 1);
	}
	
	public void setCurrentQuestion() {
 		currentQuestion = DataManager.getInstance().getRandomQuestionForRoundType(parent);
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
	
	public void setSecondsEarned(Integer secondsEarned) {
		this.secondsEarnd = secondsEarned;
	}
	
	public Integer getSecondsFinalLost() {
		return secondsFinalLost;
	}
	
	public void setSecondsFinalLost(int secondsLost) {
		secondsFinalLost = secondsLost;
	}
	
	public ArrayList<SharedQuestion> getSharedQuestions() {
		return sharedQuestions;
	}
	
/*	public Question getSkippedQuestion() {
		return skippedQuestion;
	}
	
	public void setSkippedQuestion(Question question) {
		skippedQuestion = question;
	}*/
	
	/*public void setSharedQuestions(ArrayList<SharedQuestion> sharedQuestions) {
		this.sharedQuestions = sharedQuestions;
	}*/
	
	/*
	public void addSharedQuestion(SharedQuestion sharedQuestion) {
		if (sharedQuestions == null)
			sharedQuestions = new ArrayList<>();
		
		sharedQuestions.add(sharedQuestion);
	}*/
	
/*	public void setSharedQuestion() {
		if(sharedQuestions.size() > 0)
			currentQuestion = sharedQuestions.get(sharedQuestions.size() - 1).setTurnId(turnId);
	}
	
	public void setSharedQuestion(ArrayList<SharedQuestion> sharedQuestions) {
		currentQuestion = sharedQuestions.get(sharedQuestions.size() - 1).setTurnId(turnId);
	}*/
	
	/*public void setSharedQuestion(SharedQuestion sharedQuestion) {
		this.currentQuestion = sharedQuestion;
	}*/
	
	public ArrayList<PlayerAnswer> getPlayerAnswers() {
		return playerAnswers;
	}
	
	public void setPlayerAnswers(ArrayList<PlayerAnswer> playerAnswers) {
		this.playerAnswers = playerAnswers;
	}
	
	public void addTime(int value) {
		time += value;
	}
	
	public void substractTime(int value) {
		time -= value;
		
		parent.getGame().updateView();
	}
	
	public void startTimer() {
		timer = new MyTimer().schedule(() -> substractTime(1), 1000);
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
		if (playerAnswers == null)
			playerAnswers = new ArrayList<>();
		playerAnswers.add(answer);
	}
	
	public int getAmountAnswers() {
		return playerAnswers == null ? 0 : playerAnswers.size();
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public Round getRound() {
		return parent;
	}

	public static void pushTurn(Turn turn, TurnState turnState, String answer) {
		//SharedQuestion
		if(turn.getRound().roundType == RoundType.ThreeSixNine || turn.getRound().roundType == RoundType.Puzzle) {
			//turn.setQuestionId(turn.getCurrentQuestion().getId());
			turn.setTurnState(turnState);
			
			DataManager.getInstance().updateTurn(turn);
			if(answer != null && !answer.isEmpty())
				DataManager.getInstance().updateSharedQuestionAnswer(turn.getSharedQuestion(), answer);
		}
	}
}
