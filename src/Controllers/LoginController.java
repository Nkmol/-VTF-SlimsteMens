package Controllers;

import View.LoginPanel;

public class LoginController {
	private LoginPanel view;
	
	public LoginController() {
		view = new LoginPanel();
	}
	
	public LoginPanel getView() {
		return view;
	}
}
