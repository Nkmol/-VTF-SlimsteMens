package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Controllers.PlayerListController;
import Models.ChallengedPlayer;

public class PlayerListView extends JPanel implements Observer{
	
	private PlayerView[] playerViews;
	private PlayerListController controller;
	private JPanel container;
	
	public PlayerListView(PlayerListController controller) {
		
		this.controller = controller;
		
		setPreferredSize(new Dimension(500,500));
		setLayout(new BorderLayout());
		setBackground(new Color(193,212,255));
		
		container = new JPanel();
		container.setPreferredSize(new Dimension(0,0));
		
		JScrollPane scroll = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//scroll.add(container, BorderLayout.CENTER);
		add(scroll, BorderLayout.CENTER);
		
	}

	private void updatePlayers(ArrayList<ChallengedPlayer> players) {
		
		if(playerViews != null) {
			for(int i = 0; i < playerViews.length; i++) {
				container.remove(playerViews[i]);
			}
		}
		
		playerViews = new PlayerView[players.size()];
		
		for(int i = 0; i < players.size(); i++) {
			playerViews[i] = new PlayerView(players.get(i).getPlayer(), players.get(i).getRank(), !players.get(i).isChallenged(), controller); // TODO add a check to see if the player has already been challenged.
			container.add(playerViews[i]);
		}
		
		repaint();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updatePlayers((ArrayList<ChallengedPlayer>) arg); // TODO UNCHECKED CAST FIX
		validate();
	}
}
