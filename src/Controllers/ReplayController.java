package Controllers;

import View.ReplayPanel;

public class ReplayController {
	MainController parent;
	ReplayPanel View;
	
	public ReplayController(int id, MainController parent) {
		this.parent = parent;
		View = new ReplayPanel(this);
	}
	
	public void ShowView() {
		parent.SetViewCategoryPanel(View);
	}
}
