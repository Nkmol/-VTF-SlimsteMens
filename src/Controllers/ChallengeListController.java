package Controllers;

import javax.swing.JFrame;

import Models.ActiveChallenges;
import Models.ActivePlayers;
import View.ChallengeListView;
import View.ChallengeView;
import View.PlayerListView;

public class ChallengeListController {

	JFrame main;
	
	public ChallengeListController(JFrame main) {
		
		ChallengeController challengeController = new ChallengeController();
		ChallengeView[] challengesView = challengeController.getChallengeViews();
		
		this.main = main;
		
		//ChallengeListView challengeListView = new ChallengeListView(challengesView);
		
		//main.add(playerListView);
		//main.pack();
	}
	
	public ChallengeListView temp()
	{
		
		ChallengeListView challengeListView = new ChallengeListView();
		ActiveChallenges activeChallenges = new ActiveChallenges();
		
		activeChallenges.addObserver(challengeListView);
		
		return challengeListView;
	}
	
}
