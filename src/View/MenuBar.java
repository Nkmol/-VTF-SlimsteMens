package View;

import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import net.miginfocom.swing.MigLayout;

public class MenuBar extends JMenuBar {
	//public JMenu menuFile, menuAccount, menuHighscrs, menuPlayers, menuChallenges;
	public JButton menuExit, menuAccount, menuReplays, menuPlayers, menuChallenges;
	//public JMenuItem menuExit;
	
	/*
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
		
		menuChallenges = new JMenu("Challenges");
		menuChallenges.setMnemonic(KeyEvent.VK_H);
		add(menuChallenges);
	}
	*/
	
	public MenuBar() {
		this.setLayout(new MigLayout("fill, insets 0", "[fill]")); // TODO maybe think of a better solution
		
		menuExit = new JButton("Exit");
		menuExit.setFont(new Font("Serif", Font.ITALIC, 15));
		//menuExit.setBorder(BorderFactory.createEmptyBorder());
		menuExit.setContentAreaFilled(false);
		menuExit.setMnemonic(KeyEvent.VK_F);
		add(menuExit);

		menuAccount = new JButton("My account");
		menuAccount.setFont(new Font("Serif", Font.ITALIC, 15));
		//menuAccount.setBorder(BorderFactory.createEmptyBorder());
		menuAccount.setContentAreaFilled(false);
		menuAccount.setMnemonic(KeyEvent.VK_A);
		add(menuAccount);
		
		menuPlayers = new JButton("Players");
		menuPlayers.setFont(new Font("Serif", Font.ITALIC, 15));
		//menuPlayers.setBorder(BorderFactory.createEmptyBorder());
		menuPlayers.setContentAreaFilled(false);
		menuPlayers.setMnemonic(KeyEvent.VK_H);
		add(menuPlayers);
		
		menuReplays = new JButton("Replays");
		menuReplays.setFont(new Font("Serif", Font.ITALIC, 15));
		//menuHighscrs.setBorder(BorderFactory.createEmptyBorder());
		menuReplays.setContentAreaFilled(false);
		menuAccount.setMnemonic(KeyEvent.VK_H);
		add(menuReplays);
		
		menuChallenges = new JButton("Challenges");
		menuChallenges.setFont(new Font("Serif", Font.ITALIC, 15));
		//menuChallenges.setBorder(BorderFactory.createEmptyBorder());
		menuChallenges.setContentAreaFilled(false);
		menuChallenges.setMnemonic(KeyEvent.VK_H);
		add(menuChallenges);
	}
}
