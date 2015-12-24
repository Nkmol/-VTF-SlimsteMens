package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Managers.DataManager;

public abstract class Round extends Observable {

	protected RoundType roundType;
	protected ArrayList<Question> questions;
	protected Question skippedQuestion;
	protected Turn currentTurn;
	protected ArrayList<Turn> turns;
	protected Game game;
	
	public Round(Game game, RoundType roundType) {
		startRound();
		this.game = game;
		this.roundType = roundType;
		
		//TODO Push to database
		Turn turn = new Turn(roundType, DataManager.getInstance().getCurrentUser(), game.getId());
		turn.setTurnState(TurnState.Busy);
		turn.setTurnId(1);
		setCurrrentTurn(turn);
	}
	
	public Round(ResultSet data, Game game) {
		startRound();
		try {
			roundType = RoundType.fromString(data.getString("rondenaam"));
			//questions = DataManager.getInstance().getQuestions(this);
			this.game = game;
			//turns = DataManager.getInstance().getTurns(this);
		} catch (SQLException e) {
			System.err.println("Error initializing round");
		}
	}
	
	public void startRound() {
		turns = new ArrayList<Turn>();
		questions = new ArrayList<Question>();
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setCurrrentTurn(Turn turn) {
		this.currentTurn = turn;
	}
	
	public Turn getCurrentTurn() {
		return currentTurn;
	}
	
	public ArrayList<Turn> getTurns() {
		return turns;
	}
	
	public Question getSkippedQuestion() {
		return skippedQuestion;
	}
	
	public void setSkippedQuestion(Question question) {
		skippedQuestion = question;
	}
	
	public void refreshTurn() {
		//TODO: fetch turn from the database
	}
	
	public RoundType getRoundType() {
		return roundType;
	}
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
    public void updateView() {
        setChanged();
        notifyObservers(this);
    }
    
    public int getTurnAmount() {
    	return turns.size();
    }
	
	public int generateAnswerId() {
		return currentTurn.getAmountAnswers() == 0 ? 1 : currentTurn.getTurnId() + 1;
	}
	
	public void updateAnswer(String answer) {
		getCurrentTurn().addPlayerAnswer(new PlayerAnswer(currentTurn, game.getId(), answer, generateAnswerId()));
	}
	
	public static Round createRound(RoundType type, Game game) {
		switch (type) {
		case ThreeSixNine:
			return new ThreeSixNine(game);
		case OpenDoor:
			return new OpenDoor(game);
		case Puzzle:
			return new Puzzle(game);
		case Framed:
			return new Framed(game);
		case Final:
			return new Final(game);
		default:
			return null;
		}
	}
	
	public abstract void onSubmit(String answer);

	private void updateCurrentTurn(TurnState pass, int i) {
		currentTurn.setTurnState(pass);
		currentTurn.setSecondsEarned(i);
		DataManager.getInstance().updateTurn(currentTurn);
	}
	
	public void endTurn(TurnState pass, int i) {
		updateCurrentTurn(pass, i);
		//DataManager.getInstance().updateGa
	}
}
