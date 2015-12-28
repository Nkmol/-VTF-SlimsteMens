package Models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import Managers.DataManager;

public class ActiveChallenges extends Observable {
	
	private ArrayList<GameInfo> activeChallenges;
	private Timer syncTimer;
	
	public ActiveChallenges() {
		startSyncTimer();
	}
	
	private void startSyncTimer() {
		syncTimer = new Timer();
		syncTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				getChallengesForPlayer();
			}
			
		}, 0L, 1000L);
	}
	
	private void getChallengesForPlayer() {
		//TODO: Zorgen dat de datamanger functie niet vastloopt of heel lang gaat duren
		ArrayList<GameInfo> challenges = DataManager.getInstance().getAllGameInfosForPlayer(DataManager.getInstance().getCurrentUser().getName());
		activeChallenges = new ArrayList<GameInfo>();
		
		for(int i = 0; i < challenges.size(); i++) {
			if(challenges.get(i).getPlayer2().getName().equals(DataManager.getInstance().getCurrentUser().getName())) {
				if(challenges.get(i).getGameState() == GameState.Invited) {
					activeChallenges.add(challenges.get(i));
				}
			}
		}
		
		notifyObs();
	}
	
	private void notifyObs() {
		setChanged();
		notifyObservers(activeChallenges);
	}
	
	public void rejectChallenge(int gameId) {
		// TODO reject challenge func
		DataManager.getInstance().updateGameState(GameState.Rejected, gameId);
	}
	
	public void acceptChallenge(int gameId) {
		// TODO accept challenge func
		DataManager.getInstance().updateGameState(GameState.Busy, gameId);
	} 
}
