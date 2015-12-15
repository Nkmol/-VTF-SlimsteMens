package View;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import Controllers.ChallengeListController;
import Controllers.PlayerListController;
   
public class MainPanel extends JPanel {
	
	//private JPanel CategoryPanel;
	private PlayerListView playerList;
	private ChallengeListView challengeList;
	private JPanel activeGamesPanel;
	
	/**
	 * Create the panel. 
	 */
	public MainPanel() {
		setLayout(new BorderLayout());
		//CategoryPanel = new CategoryPanel();
		
		PlayerListController temp = new PlayerListController(null);
		ChallengeListController temp2 = new ChallengeListController(null);
		
		playerList = temp.temp();
		challengeList = temp2.temp();
		
		// TODO: change JPanel constructor to actual View for Active games
		activeGamesPanel = new JPanel();
		activeGamesPanel.setPreferredSize(new Dimension(100, getHeight()));
		//add(playerList, BorderLayout.CENTER);
		add(challengeList, BorderLayout.CENTER);
		add(activeGamesPanel, BorderLayout.EAST);
	}

}
