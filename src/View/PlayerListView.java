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
	
	//private PlayerView[] playerViews;
	private ArrayList<PlayerView> playerViews;
	private PlayerListController controller;
	private JPanel container;
	private JScrollPane scroll;
	private ArrayList<ChallengedPlayer> activePlayers;
	
	public PlayerListView(PlayerListController controller) {
		
		this.controller = controller;
		
		setPreferredSize(new Dimension(500,500));
		setLayout(new BorderLayout());
		setBackground(new Color(193,212,255));
		
		container = new JPanel(new CustomFlowLayout());
		//container.setPreferredSize(new Dimension(0,0));
		
		scroll = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll, BorderLayout.CENTER);
		
		playerViews = new ArrayList<PlayerView>();
		
	}

	private void updatePlayers(ArrayList<ChallengedPlayer> players) {

		
		calculateDifference(players);
		
		//playerViews = new PlayerView[players.size()];
		
		JPanel containerTemp = new JPanel(new CustomFlowLayout());
		/*
		for(int i = 0; i < players.size(); i++) {
			playerViews[i] = new PlayerView(players.get(i).getPlayer(), players.get(i).getRank(), !players.get(i).isChallenged(), controller); // TODO add a check to see if the player has already been challenged.
			container.add(playerViews[i]);
		}
		*/
		repaint();
	}
	
	private void addAllPlayers(ArrayList<ChallengedPlayer> players) {
		for(int i = 0; i < players.size(); i++) {
			PlayerView playerView = new PlayerView(players.get(i).getPlayer(), players.get(i).getRank(), !players.get(i).isChallenged(), controller); // TODO add a check to see if the player has already been challenged.
			playerViews.add(playerView);
			container.add(playerView);
		}
	}
	
	private void calculateDifference(ArrayList<ChallengedPlayer> players) {
		if(activePlayers == null) {
			activePlayers = players;
			addAllPlayers(players);		
		}
		else {
			
			for(int i = 0; i < activePlayers.size(); i++) {
				for(int n = 0; n < players.size(); n++) {
					// TODO function for deleting removed players.
				}
			}
			
			
			
			for(int i = 0; i < players.size(); i++) {
				ChallengedPlayer player = players.get(i);
				
				if (!activePlayers.contains(player)) {
					
					for(int n = 0; n < activePlayers.size(); n++) {
						ChallengedPlayer activePlayer = activePlayers.get(n);
						
						if(player.getName().equals(activePlayer.getName())) {
							activePlayer = player;
							playerViews.get(n).updateView(player);
						}
						else {
							activePlayers.add(player);
							PlayerView tempView = new PlayerView(players.get(i).getPlayer(), players.get(i).getRank(), !players.get(i).isChallenged(), controller); // TODO add a check to see if the player has already been challenged.
							playerViews.add(tempView);	
							container.add(tempView);
						}
					}
					
				}
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updatePlayers((ArrayList<ChallengedPlayer>) arg); // TODO UNCHECKED CAST FIX
		validate();
	}
}
