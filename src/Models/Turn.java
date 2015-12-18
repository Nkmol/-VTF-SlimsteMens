package Models;

import java.util.TimerTask;

public class Turn {

	private Round round;
	private Question question;
	private Question sharedQuestion;
	private TurnState turnState;
	private Player player;
	private TimerTask timer;
	private int time;
	
	public Turn(Round round, Player player) {
		this.round = round; 
		this.player = player;
	}
	
	public void startTimer() {
		timer = new MyTimer().schedule(() -> time--, 1000);
	}
	
	public void stopTimer() {
		timer.cancel();
	}
	
	public Round getRound() {
		return round;
	}
	
	public int getTime() {
		return time;
	}
	
}
