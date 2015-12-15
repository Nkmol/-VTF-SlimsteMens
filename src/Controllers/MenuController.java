package Controllers;

import View.MenuBar;
import Utilities.ComponentUtility;

public class MenuController {
	
	MainController parent;
	MenuBar view;
	
	public MenuController(MainController parent) {
		this.parent = parent;
		view = new MenuBar();
		
		ComponentUtility.addActionListener(view, "menuExit", (e) -> Exit_Click());
		ComponentUtility.addActionListener(view, "menuAccount", (e) -> Account_Click());
	}
	
	private void Account_Click() {
		AccountController accountPanel = new AccountController(parent);
		parent.SetViewCategoryPanel(accountPanel.getView());
	}
	
	private void Exit_Click() {
		System.exit(0);
	}
	
	public MenuBar getView() {
		return view;
	}
}
