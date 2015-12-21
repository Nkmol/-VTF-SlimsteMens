package View;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import Controllers.PlayerListController;
import Models.ChallengedPlayer;

public class PlayerListView extends JPanel implements Observer{
	
	private PlayerView[] playerViews;
	private PlayerListController controller;
	
	public PlayerListView(PlayerListController controller) {
		
		this.controller = controller;
		
		this.setPreferredSize(new Dimension(500,500));
		
		this.setBackground(new Color(193,212,255));
		
	}

	private void updatePlayers(ArrayList<ChallengedPlayer> players) {
		
		if(playerViews != null) {
			for(int i = 0; i < playerViews.length; i++) {
				this.remove(playerViews[i]);
			}
		}
		
		playerViews = new PlayerView[players.size()];
		
		for(int i = 0; i < players.size(); i++) {
			playerViews[i] = new PlayerView(players.get(i).getPlayer(), !players.get(i).isChallenged(), controller); // TODO add a check to see if the player has already been challenged.
			add(playerViews[i]);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updatePlayers((ArrayList<ChallengedPlayer>) arg); // TODO UNCHECKED CAST FIX
		validate();
	}
}
