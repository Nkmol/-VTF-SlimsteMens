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
		ArrayList<CompetitionRankItem> ranks = new ArrayList<CompetitionRankItem>();

		for(int i = 0; i < playerList.size(); i++) {
			if(!playerList.get(i).getName().equals(DataManager.getInstance().getCurrentUser().getName())) {
				if(playerList.get(i).getRole() == Role.Player) {
					ChallengedPlayer challengedPlayer = new ChallengedPlayer(DataManager.getInstance().getCurrentUser(), playerList.get(i));
					players.add(challengedPlayer);
				}
			}
		}		
		players = sortByRank(players);
		
		activePlayers = players;
		notifyObs();
	}
	
	private ArrayList<ChallengedPlayer> sortByRank(ArrayList<ChallengedPlayer> players) {
		
	    int i, j;
	    ChallengedPlayer tijdelijk;
	    for (j = 0; j < players.size(); j++) {
	        for (i = 1; i < players.size() - j; i++) {
	            if (isHigherScore(players.get(i),players.get(i-1))) {
	                tijdelijk = players.get(i);
	                players.set(i, players.get(i-1));
	                players.set(i-1, tijdelijk);
	            }
	        }
	    }
	    return players;
	}
	
	private Boolean isHigherScore(ChallengedPlayer player, ChallengedPlayer player2) {	
		Long player1TotalGames = (long)player.getRank().getAmountPlayedGames();
		Long player1Wins = (long)player.getRank().getAmountGamesWon();
		Long player1Loses = (long)player.getRank().getAmountGamesLost();
		
		Long player2TotalGames = (long)player2.getRank().getAmountPlayedGames();
		Long player2Wins = (long)player2.getRank().getAmountGamesWon();
		Long player2Loses = (long)player2.getRank().getAmountGamesLost();
		
		if (player1TotalGames == 0) {
			if(player2TotalGames == 0) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (player2TotalGames == 0) {
			return true;
		}
		else {
		Long winPercent1 = player1Wins / player1TotalGames;
		Long winPercent2 = player2Wins / player2TotalGames;
		
		if(winPercent1 > winPercent2) {
			return true;
		}
		else if(winPercent1 == winPercent2) {
			if(player1Wins > player2Wins) {
				return true;
			}
			else if(player1Wins == player2Wins) {
				if(player1Loses < player2Loses) {
					return true;
				}
				else if(player1Loses == player2Loses) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
		
		}
		
		
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
