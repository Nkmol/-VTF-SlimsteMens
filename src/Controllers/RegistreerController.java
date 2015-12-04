package Controllers;

import View.RegistreerPanel;

public class RegistreerController {
	private RegistreerPanel view;
	
	public RegistreerController() {
		view = new RegistreerPanel();
	}
	
	public RegistreerPanel getView() {
		return view;
	}
}
