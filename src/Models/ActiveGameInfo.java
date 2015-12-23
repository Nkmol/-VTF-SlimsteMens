package Models;

public class ActiveGameInfo {
	private Player challengedPlayer;
	private Player currentPlayer;
	private int gameId;
	private Boolean myTurn;
	
	public ActiveGameInfo(Player currentPlayer, Player challengedPlayer, int gameId, Boolean myTurn) {
		this.challengedPlayer = challengedPlayer;
		this.currentPlayer = currentPlayer;
		this.gameId = gameId;
		this.myTurn = myTurn;
	}
	
	public Player getChallengedPlayer() {
		return challengedPlayer;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public int getGameId() {
		return gameId;
	}
	
	public Boolean isMyTurn() {
		return myTurn;
	}
}
