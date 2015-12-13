package View;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import Models.Player;

public class PlayerListView extends JPanel implements Observer{
	
	private PlayerView[] playerViews;
	
	public PlayerListView() {
		
		this.setPreferredSize(new Dimension(500,500));
		
		//GridBagLayout gridBagLayout = new GridBagLayout();
		//this.setLayout(gridBagLayout);
		
		/*
		for(int i = 0; i < playerViews.length; i++)
		{
			PlayerView playerView = playerViews[i];
			
			//GridBagConstraints gridBagConstraints = new GridBagConstraints();
			//gridBagConstraints.gridx = i;
			//gridBagConstraints.gridy = 0;
			//gridBagConstraints.weightx = 0.5;
			//gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			
			//add(playerView, gridBagConstraints);
			add(playerView);
		}
		*/
		
		this.setBackground(new Color(193,212,255));
		
	}

	private void updatePlayers(ArrayList<Player> players) {
		
		if(playerViews != null) {
			for(int i = 0; i < playerViews.length; i++) {
				this.remove(playerViews[i]);
			}
		}
		
		playerViews = new PlayerView[players.size()];
		
		for(int i = 0; i < players.size(); i++) {
			playerViews[i] = new PlayerView(players.get(i).getName(), true); // TODO add a check to see if the player has already been challenged.
			add(playerViews[i]);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updatePlayers((ArrayList<Player>) arg); // TODO UNCHECKED CAST FIX
		validate();
	}
}
