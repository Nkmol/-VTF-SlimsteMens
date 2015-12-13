package Models;

public class Turn {

	private Round round;
	private Question question;
	private Question sharedQuestion;
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
