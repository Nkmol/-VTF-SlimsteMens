package Models;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerGame {

	private int time;
	private TimerTask timer;
	private Player player;
	
	public PlayerGame(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getTime() {
		return time;
	}
	
	public void startTimer() {
		timer = new MyTimer().schedule(() -> time--, 1000);
	}
	
	public void stopTimer() {
		timer.cancel();
	}
}
