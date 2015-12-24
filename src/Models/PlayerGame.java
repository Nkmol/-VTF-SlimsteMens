package Models;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerGame extends Observable {

	public boolean isActive = false;
	private Game parent;
	private int time = 0;
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
	
	public void addTime(int time) {
		this.time += time; // TODO: check if this works
	}
	
	public void substractTime(int value) {
		time -= value;

		parent.updateView();
	}
	
	public void startTimer() {
		if(!isActive) {
			isActive = true;
			timer = new MyTimer().schedule(() -> substractTime(1), 1000);
		}
	}
	
	public void stopTimer() {
		isActive = false;
		timer.cancel();
	}
}
