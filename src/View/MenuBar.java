package View;

import java.awt.event.KeyEvent;

import javax.swing.*;

public class MenuBar extends JMenuBar {
	public MenuBar() {
		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		this.add(menuFile);
		
			JMenuItem menuExit = new JMenuItem("Exit");
			menuFile.add(menuExit);
		
		JMenu menuAccount = new JMenu("My account");
		menuFile.setMnemonic(KeyEvent.VK_A);
		this.add(menuAccount);
		
		JMenu menuHighscrs = new JMenu("Highscores");
		menuFile.setMnemonic(KeyEvent.VK_H);
		this.add(menuHighscrs);
	}
}
