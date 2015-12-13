package Controllers;

import View.MainFrame;

import View.AccountPanel;

public class MainController {

	private MainFrame mainFrame;
	
	MainController() {
		mainFrame = new MainFrame();
		showLogin();
	}
	
	public void showLogin() {
		LoginController login = new LoginController(this);
		mainFrame.setContentPane(login.getView());
		mainFrame.setVisible(true);
	}
	
	public void showRegister() {
		RegisterController register = new RegisterController(this);
		mainFrame.setContentPane(register.getView());
		mainFrame.setVisible(true);
	}
	
	public void showMain() {
		//TODO: replace setContentPane to this method
		mainFrame.showCustom();
	}
	
	public static void main(String[] args) {
		new MainController();
	}
}
