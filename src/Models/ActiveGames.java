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
	
/*	private void getActiveGames() {
		ArrayList<GameInfo> games = DataManager.getInstance().getAllGameInfosForPlayer(currentUser.getName()); // TODO make the names for variables better.
		ArrayList<ActiveGameInfo> activeGameList = new ArrayList<ActiveGameInfo>();
		
		for(int i = 0; i < games.size(); i++) {
			GameInfo gameInfo = games.get(i);
			
			if(gameInfo.getGameState() == GameState.Busy) {
				int gameId = gameInfo.getGameId();	
				Turn currentTurn = DataManager.getInstance().getLastTurnForGame(gameId);
				Player player1 = gameInfo.getPlayer1();
				Player player2 = gameInfo.getPlayer2();
				
				if(currentTurn != null) {
					if(player1.getName().equals(currentUser.getName())) {
						if(currentTurn.getPlayerName().equals(player1.getName())) {
							activeGameList.add(new ActiveGameInfo(player1, player2, gameId, true)); // TODO get the current turn
						}
						else {
							activeGameList.add(new ActiveGameInfo(player1, player2, gameId, false)); // TODO get the current turn
						}
					}
					else if(player2.getName().equals(currentUser.getName())) {
						if(currentTurn.getPlayerName().equals(player2.getName())) {
							activeGameList.add(new ActiveGameInfo(player1, player2, gameId, true)); // TODO get the current turn
						}
						else {
							activeGameList.add(new ActiveGameInfo(player1, player2, gameId, false)); // TODO get the current turn
						}
					}
				}
			}
		}
	}*/
		
	private void getActiveGames() {
		ArrayList<GameInfo> games = DataManager.getInstance().getAllGameInfosForPlayer(currentUser.getName()); // TODO make the names for variables better.
		ArrayList<ActiveGameInfo> activeGameList = new ArrayList<ActiveGameInfo>();
		
		for(int i = 0; i < games.size(); i++) {
			GameInfo gameInfo = games.get(i);
			
			if(gameInfo.getGameState() == GameState.Busy) {
				int gameId = gameInfo.getGameId();	
				Player player1 = gameInfo.getPlayer1();
				Player player2 = gameInfo.getPlayer2(); 

				if(Game.isCurrentPlayerTurn(gameId)) {
					if(Game.isCurrentUser(player1.getName()))
						activeGameList.add(new ActiveGameInfo(player1, player2, gameId, true)); // TODO get the current turn
					else 
						activeGameList.add(new ActiveGameInfo(player2, player1, gameId, true)); // TODO get the current turn
				}
				else {
					if(Game.isCurrentUser(player1.getName()))
						activeGameList.add(new ActiveGameInfo(player1, player2, gameId, false));
					else
						activeGameList.add(new ActiveGameInfo(player2, player1, gameId, false));
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
