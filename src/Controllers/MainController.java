package Controllers;

import java.awt.EventQueue;
import javax.swing.JFrame;
import View.MainFrame;

public class MainController {

	private MainFrame mainFrame;
	
	MainController() {
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainController();
	}
}
