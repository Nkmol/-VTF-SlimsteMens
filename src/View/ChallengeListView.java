package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Controllers.ChallengeListController;
import Models.Game;
import Models.GameInfo;
import Utilities.ComponentUtility;

public class ChallengeListView extends JPanel implements Observer {

	ChallengeView[] challengeViews;
	ChallengeListController controller;
	JPanel container;
	
	public ChallengeListView(ChallengeListController controller) {
		this.controller = controller;
		
		setPreferredSize(new Dimension(500,500));
		setLayout(new BorderLayout());
		setBackground(new Color(193,212,255));
		
		container = new JPanel();
		container.setPreferredSize(new Dimension(0,0));
		
		JScrollPane scroll = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll, BorderLayout.CENTER);
		
	}

	private void updateChallenges(ArrayList<GameInfo> games) {
		
		if(challengeViews != null) {
			for(int i = 0; i < challengeViews.length; i++) {
				container.remove(challengeViews[i]);
			}
		}
		
		challengeViews = new ChallengeView[games.size()];
		
		for(int i = 0; i < games.size(); i++) {
			GameInfo game = games.get(i);
			challengeViews[i] = new ChallengeView(game.getGameId(), game.getPlayer1().getName(), controller); // TODO add a check to see if the player has already been challenged.
			final int index = i;
			ComponentUtility.addActionListener(challengeViews[i], "declineButton", (e) -> rejectButtonClick(game.getGameId(), challengeViews[index]));
			//controller.setRejectButtonClick(challengeViews[i],  (e) -> rejectButtonClick(game.getGameId(), challengeViews[index]));
			container.add(challengeViews[i]);
		}
		
		repaint();
	}
	
	private void rejectButtonClick(int gameId, ChallengeView challengeView) {
		container.remove(challengeView); // TODO check op nullpointers
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
