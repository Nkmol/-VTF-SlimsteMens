package Controllers;

import javax.swing.JFrame;

import Models.ActivePlayers;
import View.ChallengeView;
import View.PlayerListView;
import View.PlayerView;

public class PlayerListController {
	private JFrame main;
	
	public PlayerListController(JFrame main) {
		
		PlayerController playerController = new PlayerController();
		PlayerView[] playerViews = playerController.getPlayerViews();
		
		this.main = main;
		
		//PlayerListView playerListView = new PlayerListView(playerViews);
		
		ChallengeView challengeView = new ChallengeView("Henk");
		
		//main.add(playerListView);
		//main.pack();
		
	}
	
	public PlayerListView temp()
	{
		
		PlayerListView playerListView = new PlayerListView();
		ActivePlayers activePlayers = new ActivePlayers();
		
		activePlayers.addObserver(playerListView);
		
		return playerListView;
	}
}
