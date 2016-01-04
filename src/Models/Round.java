package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Managers.DataManager;

public abstract class Round extends Observable {

	protected RoundType roundType;
	//protected ArrayList<Question> questions;
	//protected SharedQuestion sharedQuestion;
	protected boolean continueCurrentTurn;
	protected Turn lastTurn;
	protected Turn currentTurn;
	protected ArrayList<Turn> turns;
	protected Game game;
	
	public Round(Game game, RoundType roundType) {
		//startRound();
		this.game = game;
		this.roundType = roundType;
		//questions =  DataManager.getInstance().getQuestions(this);
		// We've new round so we need to push id to the database immediately
		// So we can init the current turn and push it to this round
		DataManager.getInstance().pushRound(this);  
		currentTurn = initCurrentTurn(this);
	}
	
	public Round(ResultSet data, Game game) {
		//startRound();
		try {
			roundType = RoundType.fromString(data.getString("rondenaam"));
			//questions = DataManager.getInstance().getQuestions(this);
			this.game = game;
			//turns = DataManager.getInstance().getTurns(this);
			//currentTurn = DataManager.getInstance().getLastTurnForGame(game.getId());
			currentTurn = initCurrentTurn(this);
		} catch (SQLException e) {
			System.err.println("Error initializing round");
		}
	}
	
	//Generating beginning of turn
	//TODO add other round func
	public Turn initCurrentTurn(Round round) {
		lastTurn = DataManager.getInstance().getLastTurnForGame(round);
		Turn turn = lastTurn;
		
		Player player = null;
		if(round.getRoundType() == RoundType.Final)
			player = round.getGame().getLowestScorePlayer();
		else
			player = DataManager.getInstance().getCurrentUser();
		
		// Make sure we have a last turn
		if (lastTurn != null) {
			/*
			 * When it is the players turn
			 * AND the last turn is the player
			 * AND the last turn has the state busy
			 * RESULT: last turn continues
			 */
			if(Game.isCurrentPlayerTurn(round.getGame().getId()) && Game.isCurrentUser(lastTurn.getPlayer().getName()) && (lastTurn.getTurnState() == TurnState.Busy || lastTurn.getTurnState() == TurnState.Bonus)) {
				System.out.println("continue last turn as it was on TurnState.BUSY or TurnState.Bonus");
				// MEEH ...
				continueCurrentTurn = true;
			}
			/*
			 * When it is current player turn, but TurnState is not Busy
			 * RESULT: player has a good answer and may continue with a new turn
			 * TODO: Is this in every round?
			 */
			else if(Game.isCurrentPlayerTurn(round.getGame().getId()) && Game.isCurrentUser(lastTurn.getPlayer().getName()) && lastTurn.getTurnState() != TurnState.Busy) {
				System.out.println("continue answering");
				turn = new Turn(player, round);
				turn.setTurnId(lastTurn.getTurnId()+1);
				turn.setSkippedQuestion(null);
				turn.setTurnState(TurnState.Busy);
				//DataManager.getInstance().pushTurn(turn);
			}
			/*
			 * But when it is not the current player it means the other player had ended its turn
			 * RESULT it is your first turn
			 */
			else if(Game.isCurrentPlayerTurn(round.getGame().getId()) && !Game.isCurrentUser(lastTurn.getPlayer().getName()) && lastTurn.getTurnState() != TurnState.Busy) {
				System.out.print("Other player ended turn ");
				turn = new Turn(player, round);
				
				turn.setTurnId(lastTurn.getTurnId() + 1);
				turn.setTurnState(TurnState.Busy);
				
				//DataManager.getInstance().pushTurn(turn);	
			}
			else {
				System.err.println("error while init new turn");
			}
		} else {
			/*
			 * We don't have a turn so we need to push a new turn to the database
			 */
			turn = new Turn(game.getLowestScorePlayer(), this);
			turn.setTurnState(TurnState.Busy);
			turn.setTurnId(1);

			//DataManager.getInstance().pushTurn(turn);
		}
		
		return turn;
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
	
	public void refreshTurn() {
		//TODO: fetch turn from the database
	}
	
	public RoundType getRoundType() {
		return roundType;
	}
	
/*	public ArrayList<Question> getQuestions() {
		return questions;
	}*/
	
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
	
	public abstract void onPass();
	
	public abstract boolean isCompleted();
	
	public abstract void playerTimeIsOver();
	
	private void updateCurrentTurn(TurnState pass, int i) {
		currentTurn.setTurnState(pass);
		currentTurn.setSecondsEarned(i);
		DataManager.getInstance().updateTurn(currentTurn);
	}
	
	public void endTurn(TurnState pass, int i) {
		updateCurrentTurn(pass, i);
		//DataManager.getInstance().updateGa
	}

	public Turn getLastTurn() {
		return lastTurn;
	}
}
