package Controllers;

import javax.swing.JPanel;

import Models.Main;
import View.*;

public class MainController {

	private MainFrame mainFrame;
	private Main mainModel;
	
	public MainController() {
		mainModel = new Main();
		mainFrame = new MainFrame();
		
		showLogin();
	}
	
	public void addMenu() {
		MenuController menu = new MenuController(this);
		mainFrame.setJMenuBar(menu.getView());
	}
	
	public void showLogin() {
		LoginController login = new LoginController(this);
		mainFrame.setContentPane(login.getView());
		mainFrame.setVisible(true);
	}
	
	public void showRegister() {
		RegisterController register = new RegisterController(this);
		mainFrame.setContentPane(register.getView());
		mainFrame.setVisible(true);
	}
	
	public void showActiveGames() {
		ActiveGameController activeGames = new ActiveGameController(this);
		SetViewSidebar(activeGames.getView());
	}

	
	// TODO niet beginnen met hoofdletters bij functie namen!!!!
	public void SetViewCategoryPanel(JPanel panel) {
		mainFrame.getMainPanel().setCategoryPanel(panel);
		mainFrame.setVisible(true);
	}
	
	public void SetViewSidebar(ActiveGameListView panel) {
		mainFrame.getMainPanel().setSidebar(panel);
		mainFrame.setVisible(true);
	}
	
	public void SetViewMainPanel(MainPanel panel) {
		mainFrame.setMainPanel(panel);
		mainFrame.setVisible(true);
	}
	
	public MainPanel GetViewMainPanel() {
		return mainFrame.getMainPanel();
	}
	
	public JPanel GetViewCategoryPanel() {
		return GetViewMainPanel().getCategoryPanel();
	}
	
	public JPanel GetViewSidebar() {
		return GetViewMainPanel().getSidebar();
	}
	
	public void ShowMainPanel() {
		//TODO: Only instantiate this once, when main screen is shown for the first time.
		addMenu();
		showActiveGames();
		mainFrame.ShowMainPanel();
		mainFrame.setVisible(true);
	}
	
	public void RefreshView() {
		mainFrame.getContentPane().repaint();
	}
	
	public MainFrame getView() {
		return mainFrame;
	}
	
	public static void main(String[] args) {
		new MainController();
	}
}
