package View;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
   
public class MainPanel extends JPanel {
	
	private CategoryPanel categoryPanel;
	private JPanel sidebarPanel;
	
	public MainPanel() {
		setLayout(new BorderLayout());
		
<<<<<<< HEAD
		CategoryPanel = new AccountPanel();
		// TODO: change JPanel constructor to actual View for Active games
		ActiveGamesPanel = new JPanel();
		ActiveGamesPanel.setPreferredSize(new Dimension(100, getHeight()));
		add(CategoryPanel, BorderLayout.CENTER);
		add(ActiveGamesPanel, BorderLayout.EAST);
=======
		categoryPanel = new CategoryPanel();
		sidebarPanel = new JPanel();
		sidebarPanel.setPreferredSize(new Dimension(100, getHeight()));
		
		add(categoryPanel, BorderLayout.CENTER);
		add(sidebarPanel, BorderLayout.EAST);
>>>>>>> develop
	}
	
	public MainPanel(JPanel sidebarPanel, JPanel categoryPanel) {
		setLayout(new BorderLayout());
		
		this.categoryPanel = new CategoryPanel(categoryPanel); 
		this.sidebarPanel = sidebarPanel;
		this.sidebarPanel.setPreferredSize(new Dimension(100, getHeight()));
		
		add(this.categoryPanel, BorderLayout.CENTER);
		add(this.sidebarPanel, BorderLayout.EAST);
	}
	
	public void setCategoryPanel(JPanel newPanel){
		categoryPanel.setPanel(newPanel);
	}
	
	public JPanel getCategoryPanel() {
		return categoryPanel.getPanel();
	}
	
	public void setSidebar(JPanel newPanel) {
		remove(sidebarPanel);
		sidebarPanel = newPanel;
		if (newPanel != null)
		{
			this.sidebarPanel.setPreferredSize(new Dimension(100, getHeight()));
			add(sidebarPanel, BorderLayout.EAST);
		}
	}
	
	public JPanel getSidebar() {
		return sidebarPanel;
	}
}
