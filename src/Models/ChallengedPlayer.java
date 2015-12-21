package Models;

import Managers.DataManager;

public class ChallengedPlayer {
	private Player challengedPlayer;
	private Boolean challenged;
	
	public ChallengedPlayer(Player challenger, Player challengedPlayer) {
		this.challengedPlayer = challengedPlayer;
		
		checkChallenged(challenger);
	}
	
	private void checkChallenged(Player challenger) {
		if (DataManager.getInstance().gameExistsBetween(challenger.getName(), challengedPlayer.getName(), GameState.Invited)) {
			challenged = true;
		}
		else {
			challenged = false;
		}
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
	
}
