package Controllers;

import View.MainFrame;

public class MainController {

	private MainFrame mainFrame;
	MainController() {
		mainFrame = new MainFrame();
	}
	
	public static void main(String[] args) {
		new MainController();
	}

}
