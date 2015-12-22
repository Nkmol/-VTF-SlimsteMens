package View;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Controllers.ActiveGameController;
import Models.ActiveGameInfo;

public class ActiveGameListView extends JPanel implements Observer {
	
	private ActiveGameView[] activeGameViews;
	private ActiveGameController controller;
	
	public ActiveGameListView(ActiveGameController controller) {
		
		this.controller = controller;
		setPreferredSize(new Dimension(150,500));
		setBackground(new Color(193,212,255));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
	}

	private void updatePlayers(ArrayList<ActiveGameInfo> activeGames) {
		
		if(activeGameViews != null) {
			for(int i = 0; i < activeGameViews.length; i++) {
				this.remove(activeGameViews[i]);
			}
		}
		
		activeGameViews = new ActiveGameView[activeGames.size()];
		
		for(int i = 0; i < activeGames.size(); i++) {
			activeGameViews[i] = new ActiveGameView(activeGames.get(i).getChallengedPlayer()); // TODO add a check to see if the player has already been challenged.
			add(activeGameViews[i]);
		}
		
		repaint();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updatePlayers((ArrayList<ActiveGameInfo>) arg); // TODO UNCHECKED CAST FIX
		validate();
	}
}
