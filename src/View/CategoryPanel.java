package View;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class CategoryPanel extends JPanel {
	
	private JPanel currentPane;
	
	public CategoryPanel() {
		setLayout(new GridLayout(1,1));
		addPlaceHolder();
	}
	
	public CategoryPanel(JPanel currentPane) {
		setLayout(new GridLayout(1,1));
		setPanel(currentPane);
	}
	
	private void addPlaceHolder() {
		JLabel lblPlaceHolder = new JLabel("Kies iets in het menu bovenaan om hier iets weer te geven");
		add(lblPlaceHolder);
	}
	
	public void setPanel(JPanel newPane) {
		removeAll();
		
		if (newPane == null)
			addPlaceHolder();
		else
			add(newPane);
		
		currentPane = newPane;
	}
	
	public JPanel getPanel() {
		return currentPane;
	}
}
