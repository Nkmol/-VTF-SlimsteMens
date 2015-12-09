package Controller;

import java.util.ArrayList;

import Model.Player;
import View.PlayerView;

public class PlayerController {

	ArrayList<PlayerView> playerViews = new ArrayList<PlayerView>();
	Player[] playerList;
	
	public PlayerController() {
		playerList = getPlayers();
		
		for(int i = 0; i < playerList.length; i++) {
			PlayerView playerView = new PlayerView(playerList[i].getName());
			playerViews.add(playerView);
		}
		
	}
	
	public Player[] getPlayers() {
		Player[] playersList = new Player[10];
		
		playersList[0] = new Player("Henk");
		playersList[1] = new Player("Piet");
		playersList[2] = new Player("Jan");
		playersList[3] = new Player("Willem");
		playersList[4] = new Player("Peter");
		playersList[5] = new Player("Felix");
		playersList[6] = new Player("Max");
		playersList[7] = new Player("Jonathan");
		playersList[8] = new Player("Erik");
		playersList[9] = new Player("Karel");
		
		return playersList;
	}
	
	public PlayerView[] getPlayerViews() {
		return playerViews.toArray(new PlayerView[playerViews.size()]);
	}
}
