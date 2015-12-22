package Models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import Managers.DataManager;

public class ActiveGames extends Observable {
	
	private ArrayList<ActiveGameInfo> activeGames;
	private Timer syncTimer;
	private Player currentUser;
	
	public ActiveGames() {
		currentUser = DataManager.getInstance().getCurrentUser(); // TODO let op experimental, niet overal gedaan.
		startSyncTimer();
	}
	
	private void startSyncTimer() {
		syncTimer = new Timer();
		syncTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				getActiveGames();
			}
			
		}, 0L, 1000L);
	}
	
	private void getActiveGames() {
		ArrayList<GameInfo> games = DataManager.getInstance().getAllGameInfosForPlayer(currentUser.getName()); // TODO make the names for variables better.
		ArrayList<ActiveGameInfo> activeGameList = new ArrayList<ActiveGameInfo>();
		
		for(int i = 0; i < games.size(); i++) {
			GameInfo gameInfo = games.get(i);
			
			if(gameInfo.getGameState() == GameState.Busy) {
				if(gameInfo.getPlayer1().getName().equals(currentUser.getName())) {
					activeGameList.add(new ActiveGameInfo(gameInfo.getPlayer1(), gameInfo.getPlayer2(), gameInfo.getGameId(), true)); // TODO get the current turn
				}
				else if(gameInfo.getPlayer2().getName().equals(currentUser.getName())) {
					activeGameList.add(new ActiveGameInfo(gameInfo.getPlayer2(), gameInfo.getPlayer1(), gameInfo.getGameId(), true)); // TODO get the current turn
				}
			}
		}
		
		
		activeGames = activeGameList;
		
		notifyObs();
	}
	
	private void notifyObs() {
		setChanged();
		notifyObservers(activeGames);
	}

}
