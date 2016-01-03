package Controllers;

import View.MenuBar;
import Managers.DataManager;
import Utilities.ComponentUtility;

public class MenuController {
	
	MainController parent;
	MenuBar view;
	
	public MenuController(MainController parent) {
		this.parent = parent;
		view = new MenuBar();
		
		ComponentUtility.addActionListener(view, "menuExit", (e) -> Exit_Click());
		ComponentUtility.addActionListener(view, "menuAccount", (e) -> Account_Click());
		ComponentUtility.addActionListener(view, "menuPlayers", (e) -> Players_Click());
		ComponentUtility.addActionListener(view, "menuChallenges", (e) -> Challenges_Click());
		ComponentUtility.addActionListener(view, "menuReplays", (e) -> Replay_Click());
		ComponentUtility.addActionListener(view, "menuLogout", (e) -> Logout_Click());
	}
	
	private void Challenges_Click() {
		parent.SetViewCategoryPanel(new ChallengeListController(parent).getView());
	}
	
	private void Players_Click() {
		parent.SetViewCategoryPanel(new PlayerListController(parent).getView());
	}
	
	private void Account_Click() {
		parent.SetViewCategoryPanel(new AccountController(parent).getView());
	}
	
	private void Exit_Click() {
		System.exit(0);
	}
	
	private void Logout_Click() {
		DataManager.getInstance().getCurrentUser();
		parent.deleteMenu();
		parent.showLogin();
	}
	
	private void Replay_Click() {
		parent.SetViewCategoryPanel(new ReplayListController(parent).getView());
	}
	
	public MenuBar getView() {
		return view;
	}
}
