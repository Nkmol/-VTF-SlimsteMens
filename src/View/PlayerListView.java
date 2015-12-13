package View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class PlayerListView extends JPanel {
	
	public PlayerListView(PlayerView[] playerViews) {
		
		this.setPreferredSize(new Dimension(500,500));
		
		//GridBagLayout gridBagLayout = new GridBagLayout();
		//this.setLayout(gridBagLayout);
		
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
		
		this.setBackground(new Color(193,212,255));
		
	}
}
