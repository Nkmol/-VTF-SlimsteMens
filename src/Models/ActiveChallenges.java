package Models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import Managers.DataManager;

public class ActiveChallenges extends Observable {
	
	ArrayList<Game> activeChallenges;
	Timer syncTimer;
	
	public ActiveChallenges() {
		startSyncTimer();
	}
	
	private void startSyncTimer() {
		syncTimer = new Timer();
		syncTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				getChallengesForPlayer();
				notifyObs();
			}
			
		}, 0L, 1000L);
	}
	
	private void getChallengesForPlayer() {
		activeChallenges = DataManager.getInstance().getAllGamesForPlayer("Test");
	}
	
	private void notifyObs() {
		setChanged();
		notifyObservers(activeChallenges);
	}
}
