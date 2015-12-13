package Controllers;

import Models.Player;
import Utilities.ComponentUtility;
import View.RegisterPanel;

public class RegisterController {
	
	private RegisterPanel view;
	private MainController parent;
	
	public RegisterController(MainController parent) {
		this.parent = parent;
		view = new RegisterPanel();
		
		ComponentUtility.addActionListener(view, "btnReg", (e) -> btnReg_Press());
	}
	
	public void btnReg_Press() {
		String error = Player.register(view.txtUsername.getText(), view.txtPassword.getText());
		view.setError(error);
	}
	
	public RegisterPanel getView() {
		return view;
	}
}
