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
		
		repaint();
	}
	
	private void addAllPlayers(ArrayList<ChallengedPlayer> players) {
		for(int i = 0; i < players.size(); i++) {
			addPlayer(players.get(i));
		}
	}
	
	private void addPlayer(ChallengedPlayer player) {
		PlayerView playerView = new PlayerView(player.getPlayer(), player.getRank(), !player.isChallenged(), controller);
		playerViews.add(playerView);
		container.add(playerView);
	}
	
	private void updatePlayer(ChallengedPlayer newPlayer, int viewIndex) {
		playerViews.get(viewIndex).updateView(newPlayer);
	}
	
	
	private void calculateDifference(ArrayList<ChallengedPlayer> players) {
		if (activePlayers == null) {
			activePlayers = players;
			addAllPlayers(activePlayers);
		}
		else {
			for (int i = 0; i < players.size(); i++) {
				Boolean playerExists = false;
				ChallengedPlayer newPlayer = players.get(i);
				
				for(int n = 0; n < activePlayers.size(); n++) {
					ChallengedPlayer oldPlayer = activePlayers.get(n);
					
					if(comparePlayerNames(oldPlayer, newPlayer)) {
						playerExists = true;
						
						
						if(!comparePlayerActivity(oldPlayer, newPlayer) || !comparePlayerRanks(oldPlayer, newPlayer)) {
							updatePlayer(newPlayer, n);
							activePlayers.set(n, newPlayer);
						}
					}
				}
				
				if(!playerExists) {
					activePlayers.add(newPlayer);
				}
			}
		}
	}
	
	private Boolean comparePlayerNames(ChallengedPlayer oldPlayer, ChallengedPlayer newPlayer) {
		if(oldPlayer.getPlayer().getName().equals(newPlayer.getPlayer().getName())) {
			if(oldPlayer.getPlayer().getRole().equals(newPlayer.getPlayer().getRole())) {
				return true;
			}
		}
		return false;
	}
	
	
	private Boolean comparePlayerRanks(ChallengedPlayer oldPlayer, ChallengedPlayer newPlayer) {
		if(oldPlayer.getRank().getAmountPlayedGames() == newPlayer.getRank().getAmountPlayedGames()) {
			if(oldPlayer.getRank().getAmountGamesWon() == newPlayer.getRank().getAmountGamesWon()) {
				if(oldPlayer.getRank().getAmountGamesWon() == newPlayer.getRank().getAmountGamesWon()) {
					if(oldPlayer.getRank().getAverageSecondsLeft() == newPlayer.getRank().getAverageSecondsLeft()) {
						return true;
					}	
				}	
			}
		}
		return false;
	}
	
	private Boolean comparePlayerActivity(ChallengedPlayer oldPlayer, ChallengedPlayer newPlayer) {
		if(oldPlayer.isChallenged() == newPlayer.isChallenged()) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		updatePlayers((ArrayList<ChallengedPlayer>) arg); // TODO UNCHECKED CAST FIX
		validate();
	}
}
