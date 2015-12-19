package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Managers.DataManager;

public abstract class Round {

	protected RoundType roundType;
	protected ArrayList<Question> questions;
	protected Turn turn; //TODO: don't sure if we want to keep this (new turn)
	protected ArrayList<Turn> turns;
	protected Game game;
	
	public Round(Game game) {
		this.game = game;
	}
	
	public Round(ResultSet data, Game game) {
		try {
			roundType = RoundType.fromString(data.getString("rondenaam"));
			questions = DataManager.getInstance().getQuestions(this);
			this.game = game;
			turns = DataManager.getInstance().getTurns(this);
		} catch (SQLException e) {
			System.err.println("Error initializing round");
		}
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setTurn(Turn turn) {
		this.turn = turn;
	}
	
	public Turn getTurn() { //TODO: not useful?
		return turn;
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
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
}
