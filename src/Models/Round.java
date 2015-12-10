package Models;

import java.util.ArrayList;

public abstract class Round {

	protected String name;
	protected ArrayList<Question> questions;
	protected Turn turn;
	protected Game game;
	
	public Round(Game game) {
		this.game = game;
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
	
}
