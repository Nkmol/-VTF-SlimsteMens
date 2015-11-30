package Models;

import java.util.ArrayList;

public class Turn {

	private Round round;
	private ArrayList<Question> questions; //Duplicate TODO: delete!
	private TurnState turnState;
	private Player player;
	
	public Turn(Round round, Player player) {
		this.round = round; 
		this.player = player;
	}
	
	public Round getRound() {
		return round;
	}
	
}
