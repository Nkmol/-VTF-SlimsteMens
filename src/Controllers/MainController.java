package Controllers;

import View.MainFrame;

import View.AccountPanel;

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
