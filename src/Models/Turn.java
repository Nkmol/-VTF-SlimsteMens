 package Models;

import java.util.TimerTask;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Managers.DataManager;

public class Turn {

	private int gameId;
	private int turnId;
	//private String playerName;
	private TurnState turnState;
	private Player player;
	private TimerTask timer;
	private int secondsEarnd,
				questionTime;
	private int moment;
	private Integer secondsFinalLost;
	private ArrayList<SharedQuestion> sharedQuestions;
	private ArrayList<PlayerAnswer> playerAnswers;
	private Question currentQuestion;
	private Question skippedQuestion;
	private SharedQuestion sharedQuestion;
	private SharedQuestion sharedSkippedQuestion;
	private int totalActualTime;
	private Round parent;
	
	public Turn(Player player, Round parentRound) {
		this.player = player;
		this.parent = parentRound;
		gameId = parent.getGame().getId();
//		this.sharedQuestion = DataManager.getInstance().getLastSharedQuestion(this); //TOOD: get last skipped question instead
		if(parent.roundType == RoundType.Puzzle) { //TODO: load in Puzzle round?
			sharedQuestions = DataManager.getInstance().getSharedQuestions(this); 
		}
//		currentQuestion = DataManager.getInstance().getRandomQuestionForRoundType(this);
		ArrayList<Question> questions = DataManager.getInstance().getTheLeastAskedQuestions(this.player, this, 1);
		if (questions != null && questions.size() > 0)
			currentQuestion = questions.get(0);
//		this.sharedQuestions = DataManager.getInstance().getSharedQuestions(parentRound, turnId); //Dont need this?
		//TODO: get last skipped quesiton
		//1- get last turn
		//2- fetch question from turn
		skippedQuestion = initSkippedQuestion();
		totalActualTime = getTotalActualTime();
	}

	public Turn(ResultSet data, Round parentRound, boolean setCurrentTurn) { //TODO setCurrentTurn quick fix for when turn not yet assigned to round, was used for Question init. Can be removed?
		if(setCurrentTurn)
			parentRound.setCurrrentTurn(this);
		parent = parentRound;
		readResultSet(data);
		totalActualTime = getTotalActualTime();
	}
	
	private void readResultSet(ResultSet data) {
		try {
			gameId = data.getInt("spel_id");
			turnId = data.getInt("beurt_id");
			//questionId = data.getInt("vraag_id");
			//playerName = data.getString("speler");
			player = DataManager.getInstance().getPlayer(data.getString("speler"));
			turnState = TurnState.fromString(data.getString("beurtstatus"));
			secondsEarnd = data.getInt("sec_verdiend");
			secondsFinalLost = data.getInt("sec_finale_af");
			
			//in 369 question is from sharedQuestion, will be overridden if null
			currentQuestion = DataManager.getInstance().getSharedQuestion(this);
			sharedQuestion = DataManager.getInstance().getSharedQuestion(this); // >.<
			if(currentQuestion == null)
				currentQuestion = DataManager.getInstance().getQuestionForId(data.getInt("vraag_id"), parent); 

			if(parent.roundType == RoundType.Puzzle) { //TODO: load in Puzzle round?
				sharedQuestions = DataManager.getInstance().getSharedQuestions(this); 
			}
			//TODO: turn id
			playerAnswers = DataManager.getInstance().getPlayerAnswers(gameId, parent.getRoundType(), turnId);
		} catch (SQLException e) {
			System.err.println("Error initializing Turn");
			System.err.println(e.getMessage());
		}
	}
	
	public int getTotalActualTime() {
		int secondsEarnedInTheGame = DataManager.getInstance().getTotalSecondsEarnedInAGame(parent.getGame().getId(), player.getName()) + getSecondsEarned();
		int finalAf = DataManager.getInstance().getTotalSecFinaleAfOtherPlayer(this);
		System.err.println("Actual time = " + (secondsEarnedInTheGame - finalAf)); 
		return secondsEarnedInTheGame - finalAf;
	}
	
	public int getPlayerTime() {
		return totalActualTime;
	}
	
	public void startTurn() {
		playerAnswers = new ArrayList<PlayerAnswer>();
		sharedQuestions = new ArrayList<SharedQuestion>();
	}
	
	public Question initSkippedQuestion() {
		Turn lastTurn = getRound().getLastTurn();
		
		//add wrong also as state of skippedQuestion for the ThreeSixNine round
		if(lastTurn != null && !Game.isCurrentUser(lastTurn.getPlayer().getName()) && (lastTurn.getTurnState() == TurnState.Pass || 
				lastTurn.getRound().getRoundType() == RoundType.ThreeSixNine && lastTurn.getTurnState() == TurnState.Wrong)) 
			return lastTurn.getCurrentQuestion();
		else
			return null;
	}
	
	public void setSkippedQuestion(Question question) {
		skippedQuestion = question;
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
	
	public SharedQuestion getSharedSkippedQuestion() {
		return sharedSkippedQuestion;
	}

	public void setSharedSkippedQuestion(SharedQuestion sharedSkippedQuestion) {
		this.sharedSkippedQuestion = sharedSkippedQuestion;
	}

	public void setCurrentQuestion(Question question) {
		currentQuestion = question;
	}
	
	public void setSharedQuestion(SharedQuestion sharedQuestion) {
		this.sharedQuestion = sharedQuestion;
	}
	
//	public void setCurrentQuestion() {
// 		currentQuestion = DataManager.getInstance().getRandomQuestionForRoundType(parent);
//	}
	
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
	
	public ArrayList<PlayerAnswer> getPlayerAnswers() {
		if(playerAnswers == null)
			playerAnswers = new ArrayList<PlayerAnswer>();
		return playerAnswers;
	}
	
	public void setPlayerAnswers(ArrayList<PlayerAnswer> playerAnswers) {
		this.playerAnswers = playerAnswers;
	}
	
	public void addSecondsEarnd(int value) {
		secondsEarnd += value;
	}
	
	private void executeTimer(int value) {
		secondsEarnd -= value;
		moment++;
		totalActualTime-=1;
		parent.getGame().updateView();
		
		if (totalActualTime <= 0) {
			parent.playerTimeIsOver();
			timer.cancel();
		}
		
	}
	
	public void startTimer() {
		moment = 0;
		if(timer != null)
			timer.cancel();
		timer = new MyTimer().schedule(() -> executeTimer(1), 1000);
	}
	
	public void startQuestionTimer(int startValue) {
		questionTime = startValue;
		moment = 0;
		if(timer != null)
			timer.cancel();
		timer = new MyTimer().schedule(() -> executeQuestionTimer(), 1000);
	}
	
	public int getQuestionTime() {
		return questionTime;
	}
	
	private void executeQuestionTimer() {
		questionTime--;
		moment++;
		if(questionTime == 0) {
			getRound().onPass();
			questionTime = 25;
			timer.cancel();
		}
		
		parent.updateView();
	}
	
	public void stopTimer() {
		timer.cancel();
	}
	
	public void submitTurn() {
		//TODO: submit to the database
	}
	
	public int getMoment() {
		return moment;
	}
	
	public void addPlayerAnswer(PlayerAnswer answer) {
		if (playerAnswers == null)
			playerAnswers = new ArrayList<>();
		playerAnswers.add(answer);
	}
	
	public void addPlayerAnswer(String answer) {
		int index = playerAnswers == null ? 1 : playerAnswers.size() + 1;
		PlayerAnswer playerAnswer = new PlayerAnswer(this, index, answer, moment);
		addPlayerAnswer(playerAnswer);
	}
	
	public int getAmountAnswers() {
		return playerAnswers == null ? 0 : playerAnswers.size();
	}
/*	
	public String getPlayerName() {
		return playerName;
	}*/
	
	public Round getRound() {
		return parent;
	}
	
	public Question getSkippedQuestion() {
		return skippedQuestion;
	}
	
	public void deleteSkippedQuestion() {
		skippedQuestion = null;
	}

	public static void pushTurn(Turn turn, TurnState turnState, String answer) {
		turn.setTurnState(turnState);
		DataManager.getInstance().updateTurn(turn);
		
		if(turn.getRound().roundType == RoundType.ThreeSixNine) {
			if(answer != null && !answer.isEmpty())
				DataManager.getInstance().updateSharedQuestionAnswer(turn.getSharedQuestion(), answer);
		}
	}
}
