package View;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import Controllers.ChallengeListController;
import Models.Game;
import Models.GameInfo;
import Utilities.ComponentUtility;

public class ChallengeListView extends JPanel implements Observer {

	ChallengeView[] challengeViews;
	ChallengeListController controller;
	
	public ChallengeListView(ChallengeListController controller) {
		this.controller = controller;
		
		setPreferredSize(new Dimension(500,500));
		
		setBackground(new Color(193,212,255));
		
	}

	private void updateChallenges(ArrayList<GameInfo> games) {
		
		if(challengeViews != null) {
			for(int i = 0; i < challengeViews.length; i++) {
				remove(challengeViews[i]);
			}
		}
		
		challengeViews = new ChallengeView[games.size()];
		
		for(int i = 0; i < games.size(); i++) {
			GameInfo game = games.get(i);
			challengeViews[i] = new ChallengeView(game.getGameId(), game.getPlayer1().getName(), controller); // TODO add a check to see if the player has already been challenged.
			final int index = i;
			ComponentUtility.addActionListener(challengeViews[i], "declineButton", (e) -> rejectButtonClick(game.getGameId(), challengeViews[index]));
			//controller.setRejectButtonClick(challengeViews[i],  (e) -> rejectButtonClick(game.getGameId(), challengeViews[index]));
			add(challengeViews[i]);
		}
		
		repaint();
	}
	
	private void rejectButtonClick(int gameId, ChallengeView challengeView) {
		remove(challengeView); // TODO check op nullpointers
		validate();
		repaint();
		controller.handleRejectButtonClick(gameId);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updateChallenges((ArrayList<GameInfo>) arg); // TODO UNCHECKED CAST FIX
		validate();
	}
}
