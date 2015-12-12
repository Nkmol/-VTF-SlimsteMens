package View;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
   
public class MainPanel extends JPanel {
	
	private JPanel CategoryPanel;
	private JPanel ActiveGamesPanel;
	
	/**
	 * Create the panel. 
	 */
	public MainPanel() {
		setLayout(new BorderLayout());
		CategoryPanel = new CategoryPanel();
		// TODO: change JPanel constructor to actual View for Active games
		ActiveGamesPanel = new JPanel();
		ActiveGamesPanel.setPreferredSize(new Dimension(100, getHeight()));
		add(CategoryPanel, BorderLayout.CENTER);
		add(ActiveGamesPanel, BorderLayout.EAST);
	}

}
