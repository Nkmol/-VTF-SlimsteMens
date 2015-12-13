package View;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel MainPanel;
	
	public MainFrame() {
		MainPanel = new MainPanel();
		setContentPane(MainPanel);
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void showCustom() {
		setContentPane(MainPanel);
	}
}
