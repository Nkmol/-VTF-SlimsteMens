package Controllers;

import Utilities.ComponentUtility;
import View.LoginPanel;
		

public class LoginController {
	private LoginPanel view;
	
	public LoginController() {
		view = new LoginPanel();
		
		ComponentUtility.addActionListener(view, "btnLogin", (e) -> btnLogin_Press());
		ComponentUtility.addActionListener(view, "btnReg", (e) -> btnReg_Press());
	}
	
	private void btnLogin_Press() {
		System.out.println("Pressed btnLogin");
	}
	
	private void btnReg_Press() {
		System.out.println("Pressed btnReg");
	}
	
	public LoginPanel getView() {
		return view;
	}
}
