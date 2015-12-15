package Controllers;

import Models.Main;
import View.MainFrame;

import View.Ronde2;

public class MainController {

	private MainFrame mainFrame;
	private Main mainModel;
	
	public MainController() {
		mainModel = new Main();
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
	
	public static void main(String[] args) {
		new MainController();
	}
}
