 package Models;

import java.util.TimerTask;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

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
	private Integer secondsFinalLost;
	private ArrayList<SharedQuestion> sharedQuestions;
	private ArrayList<PlayerAnswer> playerAnswers;
	private Question currentQuestion;
	private Question skippedQuestion; //TODO: fuck dit
	private Round parent;
	
	public Turn(RoundType rondeType, Player player, Round parentRound) {
		this.roundType = rondeType;
		this.player = player;
		this.parent = parentRound;
		this.gameId = parent.getGame().getId();
	}
	
	public Turn(ResultSet data) { //TODO dont use this constructor anymore -> Conficts with ActiveGame as it has no rounds/games to init
		readResultSet(data);
	}
	
	public Turn(ResultSet data, Round parent) { //TODO parent should already be made, so better to already use it right away?
		this.parent = parent;
		readResultSet(data);
	}
	
	private void readResultSet(ResultSet data) {
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
	
	public void startTurn() {
		playerAnswers = new ArrayList<PlayerAnswer>();
		sharedQuestions = new ArrayList<SharedQuestion>();
		
		startTimer();
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
	
	public Question getCurrentQuestion(){
		return currentQuestion;
	}
	
	//TODO move this to database
	public void setCurrentQuestion(ArrayList<Question> questions) {
		Random random = new Random();
		int randomNumber = random.nextInt(questions.size());
		currentQuestion = questions.get(randomNumber-1);
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
	
	public void setSharedQuestions(ArrayList<SharedQuestion> sharedQuestions) {
		this.sharedQuestions = sharedQuestions;
	}
	
	public void addSharedQuestion(SharedQuestion sharedQuestion) {
		if (sharedQuestions == null)
			sharedQuestions = new ArrayList<>();
		
		sharedQuestions.add(sharedQuestion);
	}
	
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
		playerAnswers.add(answer);
	}
	
	public int getAmountAnswers() {
		return playerAnswers == null ? 0 : playerAnswers.size();
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public static void pushTurn(Turn turn, TurnState turnState, String answer) {
		//SharedQuestion
		if(turn.roundType == RoundType.ThreeSixNine || turn.roundType == RoundType.Puzzle) {
			int index = turn.getSharedQuestions() != null ? turn.getSharedQuestions().size() + 1 : 1;
			
			turn.setQuestionId(turn.getCurrentQuestion().getId());
			turn.addSharedQuestion(new SharedQuestion(turn, index, answer));
			turn.setTurnState(turnState);
			
			DataManager.getInstance().updateTurn(turn);
			try {
				DataManager.getInstance().pushSharedQuestion(turn);
			} catch (SQLException e) {
				System.err.println("Error pushing shared question");
				System.err.println(e.getMessage());
			}
		}
	}
	
}
