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
		//startRound();
		this.game = game;
		this.roundType = roundType;
		
		currentTurn = initNewTurn(this);
		
		currentTurn.startTimer();
	}
	
	public Round(ResultSet data, Game game) {
		//startRound();
		try {
			roundType = RoundType.fromString(data.getString("rondenaam"));
			questions = DataManager.getInstance().getQuestions(this);
			this.game = game;
			//turns = DataManager.getInstance().getTurns(this);
			//currentTurn = DataManager.getInstance().getLastTurnForGame(game.getId());
			currentTurn = initNewTurn(this);
		} catch (SQLException e) {
			System.err.println("Error initializing round");
		}

		currentTurn.startTimer();
	}
	
	public static Turn initNewTurn(Round round) {
		//TODO optimalisatie
		
		Turn currentTurn = DataManager.getInstance().getLastTurnForGame(round);
		/*
		 * When it is the players turn
		 * AND the last turn is the player
		 * AND the last turn has the state busy
		 * RESULT: last turn continues
		 */
		if(Game.isCurrentPlayerTurn(round.getGame().getId()) && Game.isCurrentUser(currentTurn.getPlayerName()) && currentTurn.getTurnState() == TurnState.Busy) {
			System.out.println("continue last turn as it was on TurnState.BUSY");
			return currentTurn;
		}
		/*
		 * When it is his turn, but TurnState is not BUSY
		 * RESULT: player has a good answer and may continue with a new turn
		 * TODO: Is this in every round?
		 */
		else if(Game.isCurrentPlayerTurn(round.getGame().getId()) && Game.isCurrentUser(currentTurn.getPlayerName()) && currentTurn.getTurnState() != TurnState.Busy) {
			System.out.println("continue answering");
			Turn turn = new Turn(round.getRoundType(), DataManager.getInstance().getCurrentUser(), round);
			turn.setTurnId(currentTurn.getTurnId() + 1);
			turn.setTurnState(TurnState.Busy);
			return turn;
		}
		/*
		 * But when it is not the current player it means the other player had ended its turn
		 * RESULT it is your first turn
		 */
		else if(Game.isCurrentPlayerTurn(round.getGame().getId()) && !Game.isCurrentUser(currentTurn.getPlayerName()) && currentTurn.getTurnState() != TurnState.Busy) {
			System.out.println("player first turn of round");
			Turn turn = new Turn(round.getRoundType(), DataManager.getInstance().getCurrentUser(), round);
			turn.setTurnId(currentTurn.getTurnId() + 1);
			turn.setTurnState(TurnState.Busy);
			return turn;
		}
		else {
			System.err.println("error while init new turn");
			return null;
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
