package Models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import Managers.DataManager;

public class ActivePlayers extends Observable{
	
	ArrayList<Player> activePlayers;
	Timer syncTimer;
	
	public ActivePlayers() {
		startSyncTimer();
	}
	
	private void startSyncTimer() {
		syncTimer = new Timer();
		syncTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				getActivePlayers();
				notifyObs();
			}
			
		}, 0L, 1000L);
	}
	
	private void getActivePlayers() {
		activePlayers = DataManager.getInstance().getAllPlayers();
	}
	
	private void notifyObs() {
		setChanged();
		notifyObservers(activePlayers);
	}
	
}
