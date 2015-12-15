package View;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
   
public class MainPanel extends JPanel {
	
	private CategoryPanel categoryPanel;
	private JPanel sidebarPanel;
	
	public MainPanel() {
		setLayout(new BorderLayout());
		
		categoryPanel = new CategoryPanel();
		sidebarPanel = new JPanel();
		sidebarPanel.setPreferredSize(new Dimension(100, getHeight()));
		
		add(categoryPanel, BorderLayout.CENTER);
		add(sidebarPanel, BorderLayout.EAST);
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