package Controllers;

import View.RegisterPanel;

public class RegisterController {
	private RegisterPanel view;
	
	public RegisterController() {
		view = new RegisterPanel();
	}
	
	public RegisterPanel getView() {
		return view;
	}
}
