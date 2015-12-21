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
		//TODO: Zorgen dat de de ingelogde speler de challenges heeft van de andere spelers. Niet challenges die hij zelf gestuurd heeft.
		activeChallenges = DataManager.getInstance().getAllGamesForPlayer(DataManager.getInstance().getCurrentUser().getName());
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
