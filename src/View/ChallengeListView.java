package View;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import Models.Game;

public class ChallengeListView extends JPanel implements Observer {

	ChallengeView[] challengeViews;
	
	public ChallengeListView() {
		
		this.setPreferredSize(new Dimension(500,500));
		
		/*
		for(int i = 0; i < challengeViews.length; i++)
		{
			ChallengeView challengeView = challengeViews[i];
			
			add(challengeView);
		}
		*/
		this.setBackground(new Color(193,212,255));
		
	}

	private void updateChallenges(ArrayList<Game> games) {
		
		if(challengeViews != null) {
			for(int i = 0; i < challengeViews.length; i++) {
				this.remove(challengeViews[i]);
			}
		}
		
		challengeViews = new ChallengeView[games.size()];
		
		for(int i = 0; i < games.size(); i++) {
			challengeViews[i] = new ChallengeView(games.get(i).getPlayerGame1().getPlayer().getName()); // TODO add a check to see if the player has already been challenged.
			add(challengeViews[i]);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updateChallenges((ArrayList<Game>) arg); // TODO UNCHECKED CAST FIX
		validate();
	}
}
