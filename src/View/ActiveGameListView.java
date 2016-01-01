package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Controllers.ActiveGameController;
import Models.ActiveGameInfo;
import Utilities.ComponentUtility;

public class ActiveGameListView extends JPanel implements Observer {
	
	private ActiveGameView[] activeGameViews;
	private ActiveGameController controller;
	
	private JPanel topTitle;
	private JPanel topPanel;
	
	private JPanel bottomTitle;
	private JPanel bottomPanel;
	
	private JPanel topContainer;
	private JPanel bottomContainer;
	
	public ActiveGameListView(ActiveGameController controller) {
		
		this.controller = controller;
		setPreferredSize(new Dimension(150,500));
		setBackground(new Color(193,212,255));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		createLayout();
	}
	
	private void createLayout() {
		
		setLayout(new BorderLayout());
		
		topContainer = new JPanel();
		topContainer.setLayout(new BorderLayout());
		add(topContainer); // TODO oplossen op een andere manier
		
		bottomContainer = new JPanel();
		bottomContainer.setLayout(new BorderLayout());
		add(bottomContainer, BorderLayout.SOUTH);
		
		topTitle = new JPanel();
		topTitle.setPreferredSize(new Dimension(150, 40));
		topTitle.setBackground(new Color(47,109,255));
		JLabel topTitleLabel = new JLabel();
		topTitleLabel.setText("Your turn");
		topTitle.add(topTitleLabel);
		
		topContainer.add(topTitle, BorderLayout.NORTH);
		
		topPanel = new JPanel();
		topPanel.setBackground(new Color(193,212,255));
		topContainer.add(topPanel, BorderLayout.CENTER);
		
		bottomTitle = new JPanel();
		bottomTitle.setPreferredSize(new Dimension(150, 40));
		bottomTitle.setBackground(new Color(47,109,255));
		JLabel bottomTitleLabel = new JLabel();
		bottomTitleLabel.setText("Opponents turn");
		bottomTitle.add(bottomTitleLabel);
		
		bottomContainer.add(bottomTitle, BorderLayout.NORTH);
		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(193,212,255));
		bottomContainer.add(bottomPanel, BorderLayout.CENTER);
	}

	private void updatePlayers(ArrayList<ActiveGameInfo> activeGames) {
		
		if(activeGameViews != null) {
			for(int i = 0; i < activeGameViews.length; i++) {
				topPanel.remove(activeGameViews[i]);
				bottomPanel.remove(activeGameViews[i]);
			}
		}
		
		activeGameViews = new ActiveGameView[activeGames.size()];
		
		for(int i = 0; i < activeGames.size(); i++) {
			activeGameViews[i] = new ActiveGameView(activeGames.get(i).getChallengedPlayer(), activeGames.get(i).getGameId(), controller); // TODO add a check to see if the player has already been challenged.
			if(activeGames.get(i).isMyTurn()) {
				topPanel.add(activeGameViews[i]);
			}
			else {
				activeGameViews[i].hideGoButton();
				bottomPanel.add(activeGameViews[i]);
			}
		}
		
		repaint();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updatePlayers((ArrayList<ActiveGameInfo>) arg); // TODO UNCHECKED CAST FIX
		validate();
	}
}
