package Controllers;

import Managers.DataManager;
import Models.Player;
import View.AccountPanel;

public class AccountController {
	
	private MainController parent;
	private Player model;
	private AccountPanel view;
	
	public AccountController(MainController parent) {
		this.parent = parent;
		view = new AccountPanel();
		model = DataManager.getInstance().getCurrentUser();
		model.addObserver(view);
		
		//init update view
		model.updateView();
		
		//set ActionListener
		Utilities.ComponentUtility.addActionListener(view, "btnConfirm", (e) -> changePassword());
	}
	
	public void changePassword() {
		model.changePassword(new String(view.fieldOldPass.getPassword()), new String(view.fieldNewPass.getPassword()), new String(view.fieldNewPassRe.getPassword()));
	}
	
	public AccountPanel getView() {
		return view;
	}
}
