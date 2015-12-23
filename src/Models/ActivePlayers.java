package Models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import Managers.DataManager;

public class ActivePlayers extends Observable{
	
	private ArrayList<ChallengedPlayer> activePlayers;
	private Timer syncTimer;
	
	public ActivePlayers() {
		startSyncTimer();
	}
	
	private void startSyncTimer() {
		syncTimer = new Timer();
		syncTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				getActivePlayers();
			}
			
		}, 0L, 1000L);
	}
	
	private void getActivePlayers() {
		ArrayList<Player> playerList = DataManager.getInstance().getAllPlayers();
		ArrayList<ChallengedPlayer> players = new ArrayList<ChallengedPlayer>();
		
		for(int i = 0; i < playerList.size(); i++) {
			if(!playerList.get(i).getName().equals(DataManager.getInstance().getCurrentUser().getName())) {
				if(playerList.get(i).getRole() == Role.Player) {
				ChallengedPlayer challengedPlayer = new ChallengedPlayer(DataManager.getInstance().getCurrentUser(), playerList.get(i));
				players.add(challengedPlayer);
				}
			}
		}
		
		
		activePlayers = players;
		
		notifyObs();
	}
	
	private void notifyObs() {
		setChanged();
		notifyObservers(activePlayers);
	}
	
	public void sendChallenge(Player player2) {
		// TODO Use player classes instead of names
		Player player1 = DataManager.getInstance().getCurrentUser();
		DataManager.getInstance().pushNewGame(player1, player2);
	}
	
}
