package View;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private MainPanel mainPanel;
	
	public MainFrame() {
		this(new MainPanel());
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public MainFrame(MainPanel mainPanel){
		setMainPanel(new MainPanel());
	}
	
	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	public void ShowMainPanel(){
		setContentPane(this.mainPanel);
	}
}
