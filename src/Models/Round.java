package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Managers.DataManager;

public abstract class Round {

	protected RoundType roundType;
	protected ArrayList<Question> questions;
	protected Turn turn;
	protected Game game;
	
	public Round(Game game) {
		this.game = game;
	}
	
	public Round(ResultSet data, Game game) {
		try {
			roundType = RoundType.fromString(data.getString("rondenaam"));
			questions = DataManager.getInstance().getQuestions(this);
			this.game = game;
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
	
	public Turn getTurn() {
		return turn;
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
