package View;

import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuBar extends JMenuBar {
	public JMenu menuFile, menuAccount, menuHighscrs, menuPlayers;
	public JMenuItem menuExit;
	
	public MenuBar() {
		menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		add(menuFile);
		
			menuExit = new JMenuItem("Exit");
			menuFile.add(menuExit);
		
		menuAccount = new JMenu("My account");
		menuAccount.setMnemonic(KeyEvent.VK_A);
		add(menuAccount);
		
		menuPlayers = new JMenu("Players");
		menuPlayers.setMnemonic(KeyEvent.VK_H);
		add(menuPlayers);
		
		menuHighscrs = new JMenu("Highscores");
		menuAccount.setMnemonic(KeyEvent.VK_H);
		add(menuHighscrs);
	}
}
