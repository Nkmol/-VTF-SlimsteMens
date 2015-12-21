package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Models.Game;
import Models.PlayerGame;
import Models.Round;

public class GamePanel extends JPanel implements Observer{
	
	private JPanel middle;
	private JLabel lblPlayer1, lblPlayer2, lblRoundType;
	
	public GamePanel() {
		setLayout(new BorderLayout());
		lblRoundType = new JLabel();
		lblPlayer1 = new JLabel();
		lblPlayer2 = new JLabel();
		
		// [Begin] Top view
		JPanel top = new JPanel(new BorderLayout());
		top.setBorder(new EmptyBorder(10, 10, 10, 10));
			top.add(lblPlayer1, BorderLayout.LINE_START);
			//TODO: Center alignment w/e
			lblRoundType.setBorder(new EmptyBorder(10, 50, 10, 10));
			top.add(lblRoundType, BorderLayout.CENTER);
			top.add(lblPlayer2, BorderLayout.LINE_END);
		top.setPreferredSize(new Dimension(0, 100));
		add(top, BorderLayout.PAGE_START);
		// [End] Top view 
		
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
		Game model = (Game)arg1;
		
		PlayerGame playerGame1, playerGame2;
		playerGame1 = model.getPlayerGame1();
		playerGame2 = model.getPlayerGame2();
		
		//TODO: Not sure which round i should choose to determ the round
		lblRoundType.setText(model.getRounds().get(0).getRoundType().toString());
		lblPlayer1.setText(playerGame1.getPlayer().getName() + " : " + playerGame1.getTime());
		lblPlayer2.setText(playerGame2.getPlayer().getName() + " : " + playerGame2.getTime());
	}
	
}
