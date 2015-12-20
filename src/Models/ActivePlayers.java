package Models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import Managers.DataManager;

public class ActivePlayers extends Observable{
	
	ArrayList<ChallengedPlayer> activePlayers;
	Timer syncTimer;
	private static String TESTCURRENTUSERNAME = "Test";
	private static Player TESTCURRENTUSER = new Player("Test", Role.Player);
	
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
		ArrayList<Player> playerList = DataManager.getInstance().getAllPlayers();
		ArrayList<ChallengedPlayer> players = new ArrayList<ChallengedPlayer>();
		
		for(int i = 0; i < playerList.size(); i++) {
			if(!playerList.get(i).getName().equals(TESTCURRENTUSERNAME)) {
				ChallengedPlayer challengedPlayer = new ChallengedPlayer(TESTCURRENTUSER, playerList.get(i));
				players.add(challengedPlayer);
			}
		}
		
		activePlayers = players;
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
