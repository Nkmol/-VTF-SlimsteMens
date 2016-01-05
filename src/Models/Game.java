package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import org.w3c.dom.css.ElementCSSInlineStyle;

import Controllers.GameController;
import Managers.DataManager;
import Utilities.StringUtility;

public class Game extends Observable {
	
	public static final int MinimumAnswerPercentage = 80,
							BeginAmountTime = 0;
	
	private int id;
	private Player player1;
	private Player player2; 
	private GameState gameState;
	protected ArrayList<Round> rounds;
	protected Round currentRound;
	private ArrayList<ChatMessage> chatMessages;
	private GameController controller;
	
	public Game(int gameId, Player player1, Player player2, GameState gameState) {
		this.id = gameId;
		this.player1 = player1;
		this.player2 = player2;
		
		this.gameState = gameState;
		//rounds = DataManager.getInstance().getRounds(this);
		chatMessages = DataManager.getInstance().getChatMessages(id);
	}
	
	public Game(ResultSet data) {
		try {
			id = data.getInt("spel_id");
			player1 = DataManager.getInstance().getPlayer(data.getString("speler1"));
			player2 = DataManager.getInstance().getPlayer(data.getString("speler2"));
			gameState = GameState.fromString(data.getString("toestand_type"));
			//rounds = DataManager.getInstance().getRounds(this); // TODO only needed on replay?
			//currentRound = DataManager.getInstance().getLastRoundForGame(this); // TODO should be done exclusively, as this normally needs a view and model (MVC)
			chatMessages = DataManager.getInstance().getChatMessages(id);
		} catch (SQLException e) {
			System.err.println("Error initializing game");
			System.err.println(e.getMessage());
		}
	}
	
	public void setController(GameController controller) {
		this.controller = controller;
	}
	
	public GameController getController() {
		return controller;
	}
	
	public static boolean isCurrentUser(String playerName) {
		return playerName.equals(DataManager.getInstance().getCurrentUser().getName());
	}

	public void stopGame(){
		DataManager.getInstance().updateGameState(GameState.Finished, id);
	}
	
	public Player getLowestScorePlayer() {
		int player1score,
			player2score;
		
		player1score = DataManager.getInstance().getTotalSecondsEarnedInAGame(id, player1.getName());
		player2score = DataManager.getInstance().getTotalSecondsEarnedInAGame(id, player2.getName());
		
		if(player1score == player2score || player1score < player2score)
			return player1;
		else 
			return player2;
	}
	
	public static boolean isCurrentPlayerTurn(int gameId) {
		TurnInfo lastTurn = DataManager.getInstance().getLastInfoTurnForGame(gameId);
		TurnInfo beforeLastTurn = null;
		if(lastTurn != null)
			 beforeLastTurn = DataManager.getInstance().getTurInfonBeforeATurnInfo(lastTurn);
		
		if(lastTurn != null) {
			if(playerAnsweredASkippedQuestion(lastTurn)) 
				return true;
			else if (lastTurn.getRoundType() == RoundType.ThreeSixNine) {
				if (lastTurn.getTurnState() == TurnState.Correct && isCurrentUser(lastTurn.getPlayer().getName()))
					return true;
			}
			else if(isCurrentUser(lastTurn.getPlayer().getName()) && (lastTurn.getTurnState() == TurnState.Busy || lastTurn.getTurnState() == TurnState.Bonus))
				return true;
			else if(beforeLastTurn != null && lastTurn.getsharedQuestionId() != beforeLastTurn.getsharedQuestionId() && !isCurrentUser(lastTurn.getPlayer().getName()) && lastTurn.getTurnState() != TurnState.Busy && lastTurn.getTurnState() != TurnState.Bonus)
				return true;
			else if(!isCurrentUser(lastTurn.getPlayer().getName()) && lastTurn.getTurnState() != TurnState.Busy && lastTurn.getTurnState() != TurnState.Bonus)
				return true;
			else 
				return false; 
		}
		else {
			GameInfo gameInfo = DataManager.getInstance().getGameInfoForGame(gameId);
			if(isCurrentUser(gameInfo.getPlayer1().getName()))
				return true;
		}
		
		return false;	
	}
	
	public static boolean playerAnsweredASkippedQuestion(TurnInfo lastTurn) {
		TurnInfo turnBeforeLastTurn = DataManager.getInstance().getTurInfonBeforeATurnInfo(lastTurn);
		if (turnBeforeLastTurn != null) {
			boolean lastTurnWasCurrentUser = isCurrentUser(lastTurn.getPlayer().getName());
			boolean turnBeforeLastTurnWasPassed = turnBeforeLastTurn.getTurnState() == TurnState.Pass;
			boolean turnBeforeLastTurnIsTSN = turnBeforeLastTurn.getRoundType() == RoundType.ThreeSixNine;
			boolean turnBeforeLastTurnWasWrong = turnBeforeLastTurn.getTurnState() == TurnState.Wrong;
			boolean turnBeforeLastTurnWasNotForCurrentUser = !isCurrentUser(turnBeforeLastTurn.getPlayer().getName());
			if (lastTurn.getQuestionId() == 0 && turnBeforeLastTurn.getQuestionId() == 0) {
				if (lastTurnWasCurrentUser 
						&& (turnBeforeLastTurnWasPassed || (turnBeforeLastTurnIsTSN && turnBeforeLastTurnWasWrong))
						&& turnBeforeLastTurnWasNotForCurrentUser) { // now we know that the current player has answered a skipped question
					return true;
				}
			}
			else if (lastTurnWasCurrentUser
					&& (turnBeforeLastTurnWasPassed || (turnBeforeLastTurnIsTSN && turnBeforeLastTurnWasWrong))
					&& lastTurn.getQuestionId() == turnBeforeLastTurn.getQuestionId()
					&& turnBeforeLastTurnWasNotForCurrentUser) { // now we know that the current player has answered a skipped question
				return true;
			}
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
	
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
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
		if (rounds == null)
			rounds = new ArrayList<>();
		rounds.add(model);
		setCurrentRound(model);
		
		//DataManager.getInstance().pushRound(getCurrentRound());
		//DataManager.getInstance().pushTurn(getCurrentRound().getCurrentTurn());
		
		setChanged();
		notifyObservers(this);
	}
	
	public void setRound(Round model) {
		setCurrentRound(model);
	}
}
