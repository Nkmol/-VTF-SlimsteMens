package Controllers;

import javax.swing.JFrame;

import View.ChallengeListView;
import View.ChallengeView;
import View.PlayerListView;

public class ChallengeListController {

	JFrame main;
	
	public ChallengeListController(JFrame main) {
		
		ChallengeController challengeController = new ChallengeController();
		ChallengeView[] challengesView = challengeController.getChallengeViews();
		
		this.main = main;
		
		ChallengeListView playerListView = new ChallengeListView(challengesView);
		
		main.add(playerListView);
		main.pack();
	}
	
}
