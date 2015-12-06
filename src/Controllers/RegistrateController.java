package Controllers;

import View.RegistratePanel;

public class RegistrateController {
	private RegistratePanel view;
	
	public RegistrateController() {
		view = new RegistratePanel();
	}
	
	public RegistratePanel getView() {
		return view;
	}
}
