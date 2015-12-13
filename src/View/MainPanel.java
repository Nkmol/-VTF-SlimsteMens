package View;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import Controllers.PlayerListController;
   
public class MainPanel extends JPanel {
	
	//private JPanel CategoryPanel;
	private PlayerListView playerlist;
	private JPanel ActiveGamesPanel;
	
	/**
	 * Create the panel. 
	 */
	public MainPanel() {
		setLayout(new BorderLayout());
		//CategoryPanel = new CategoryPanel();
		
		PlayerListController temp = new PlayerListController(null);
		
		playerlist = temp.temp();
		// TODO: change JPanel constructor to actual View for Active games
		ActiveGamesPanel = new JPanel();
		ActiveGamesPanel.setPreferredSize(new Dimension(100, getHeight()));
		add(playerlist, BorderLayout.CENTER);
		add(ActiveGamesPanel, BorderLayout.EAST);
	}

}
