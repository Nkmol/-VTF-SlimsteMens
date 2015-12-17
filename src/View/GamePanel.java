package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class GamePanel extends JPanel implements Observer{
	
	JPanel middle;
	
	public GamePanel() {
		setLayout(new BorderLayout());
		
		// Top view
		JPanel top = new JPanel();
		top.setPreferredSize(new Dimension(0, 100));
		add(top, BorderLayout.PAGE_START);
		
		middle = new JPanel();
		add(middle, BorderLayout.CENTER);
		
		JPanel chat = new JPanel();
		chat.setPreferredSize(new Dimension(80, 0));
		chat.setBackground(Color.yellow);
		add(chat, BorderLayout.LINE_END);
		
		JPanel bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(0, 50));
		add(bottom, BorderLayout.PAGE_END);
	}
	
	public void setRound(JPanel round) {
		remove(middle);
		add(round, BorderLayout.CENTER);
		middle = round;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
