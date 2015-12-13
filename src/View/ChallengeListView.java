package View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class ChallengeListView extends JPanel {

	public ChallengeListView(ChallengeView[] challengeViews) {
		
		this.setPreferredSize(new Dimension(500,500));
		
		for(int i = 0; i < challengeViews.length; i++)
		{
			ChallengeView challengeView = challengeViews[i];
			
			add(challengeView);
		}
		
		this.setBackground(new Color(193,212,255));
		
	}
}
