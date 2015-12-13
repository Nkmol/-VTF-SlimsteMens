package Controllers;

import View.MainFrame;

public class MainController {

	private MainFrame mainFrame;
	
	MainController() {
		mainFrame = new MainFrame();
		
		LoginController login = new LoginController();
		mainFrame.setContentPane(login.getView());
		
		mainFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainController();
	}
}
