package Controllers;

import Models.Player;
import Utilities.ComponentUtility;
import View.LoginPanel;
		

public class LoginController {
	private MainController parent;
	private LoginPanel view;
	private Player model;
	
	public LoginController(MainController parent) {
		this.parent = parent;
		view = new LoginPanel();
		model = new Player();
		model.addObserver(view);
		
		ComponentUtility.addActionListener(view, "btnLogin", (e) -> btnLogin_Press());
		ComponentUtility.addActionListener(view, "btnReg", (e) -> btnReg_Press());
	}
	
	private void btnLogin_Press() {
		if(model.login(view.txtUsername.getText(), view.txtPassword.getText())) {
			System.out.println("Succesvol ingelogd!");
		}
	}
	
	private void btnReg_Press() {
		parent.showRegister();
	}
	
	public LoginPanel getView() {
		return view;
	}
}
