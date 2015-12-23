package Models;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerGame extends Observable {

	public boolean isActive = true;
	private Game parent;
	private int time = 1000;
	private TimerTask timer;
	private Player player;
	
	public PlayerGame(Player player, Game parent) {
		this.parent = parent;
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getTime() {
		return time;
	}
	
	public void substractTime(int value) {
		time -= value;

		parent.updateView();
	}
	
	public void startTimer() {
		timer = new MyTimer().schedule(() -> substractTime(1), 1000);
	}
	
	public void stopTimer() {
		timer.cancel();
	}
}
