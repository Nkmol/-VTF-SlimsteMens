package Models;

import Managers.DataManager;

public class ChallengedPlayer {
	private Player challengedPlayer;
	private Boolean challenged;
	private CompetitionRankItem rank;
	
	public ChallengedPlayer(Player challenger, Player challengedPlayer) {
		this.challengedPlayer = challengedPlayer;
		
		checkChallenged(challenger);
		checkRank();
	}
	
	private void checkChallenged(Player challenger) {
		if (DataManager.getInstance().gameExistsBetween(challenger.getName(), challengedPlayer.getName(), GameState.Invited)) {
			challenged = true;
		}
		else if (DataManager.getInstance().gameExistsBetween(challenger.getName(), challengedPlayer.getName(), GameState.Busy)) {
			challenged = true;
		}
		else if (DataManager.getInstance().gameExistsBetween(challengedPlayer.getName(), challenger.getName(), GameState.Invited)) {
			challenged = true;	
		}
		else {
			challenged = false;
		}
	}
	
	private void checkRank() {
		rank = DataManager.getInstance().getCompetitionRankForPlayer(challengedPlayer.getName());
	}
	
	public String getName() {
		return challengedPlayer.getName();
	}
	
	public Player getPlayer() {
		return challengedPlayer;
	}
	
	public Boolean isChallenged() {
		return challenged;
	}
	
	public CompetitionRankItem getRank() {
		return rank;
	}
	
}
