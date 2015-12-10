package Controller;

import java.util.ArrayList;

import Models.Player;
import Models.Role;
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
		
		playersList[0] = new Player("Henk", Role.Player);
		playersList[1] = new Player("Piet", Role.Player);
		playersList[2] = new Player("Jan", Role.Player);
		playersList[3] = new Player("Willem", Role.Player);
		playersList[4] = new Player("Peter", Role.Player);
		playersList[5] = new Player("Felix", Role.Player);
		playersList[6] = new Player("Max", Role.Player);
		playersList[7] = new Player("Jonathan", Role.Player);
		playersList[8] = new Player("Erik", Role.Player);
		playersList[9] = new Player("Karel", Role.Player);
		
		return playersList;
	}
	
	public PlayerView[] getPlayerViews() {
		return playerViews.toArray(new PlayerView[playerViews.size()]);
	}
}
