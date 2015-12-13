package Controllers;

import Models.Player;
import Utilities.ComponentUtility;
import View.RegisterPanel;

public class RegisterController {
	
	private RegisterPanel view;
	private MainController parent;
	private Player model;
	
	public RegisterController(MainController parent) {
		this.parent = parent;
		view = new RegisterPanel();
		model = new Player();
		model.addObserver(view);
		
		ComponentUtility.addActionListener(view, "btnReg", (e) -> btnReg_Press());
		ComponentUtility.addActionListener(view, "btnLogin", (e) -> btnLogin_Press());
	}
	
	public void btnLogin_Press() {
		parent.showLogin();
	}
	
	public void btnReg_Press() {
	    model.register(view.txtUsername.getText(), view.txtPassword.getText());
	}
	
	public RegisterPanel getView() {
		return view;
	}
}
