package View;

import java.awt.event.KeyEvent;

import javax.swing.*;

public class MenuBar extends JMenuBar {
	public JMenu menuFile, menuAccount, menuHighscrs, menuPlayers;
	public JMenuItem menuExit;
	
	public MenuBar() {
		menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		this.add(menuFile);
		
			menuExit = new JMenuItem("Exit");
			menuFile.add(menuExit);
		
		menuAccount = new JMenu("My account");
		menuFile.setMnemonic(KeyEvent.VK_A);
		this.add(menuAccount);
		
		menuPlayers = new JMenu("Players");
		menuPlayers.setMnemonic(KeyEvent.VK_H);
		this.add(menuPlayers);
		
		menuHighscrs = new JMenu("Highscores");
		menuFile.setMnemonic(KeyEvent.VK_H);
		this.add(menuHighscrs);
	}
}
