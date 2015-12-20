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
		ComponentUtility.addActionListener(view, "menuPlayers", (e) -> Players_Click());
		ComponentUtility.addActionListener(view, "menuFile", (e) -> Exit_Click());
		ComponentUtility.addActionListener(view, "menuChallenges", (e) -> Challenges_Click());
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
	
	public MenuBar getView() {
		return view;
	}
}
