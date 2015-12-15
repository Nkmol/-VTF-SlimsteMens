package Controllers;

import Models.ReplayList;
import View.ReplayListPanel;

public class ReplayListController {

	private MainController parent;
	private ReplayListPanel view;
	private ReplayList model;
	
	public ReplayListController(MainController parent) {
		this.parent = parent;
		model = new ReplayList();
		view = new ReplayListPanel(model.getScore(), this);
	}
	
	public void ShowView() {
		parent.SetViewCategoryPanel(view);
	}
	
	public void StartReplay(int Id) {
		new ReplayController(Id, parent).ShowView();
	}
}