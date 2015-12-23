package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
	public JButton btnSubmit, btnPass;
	public JTextField txtInput;
	
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
		
		// CHAT PART
		//JPanel chat = new JPanel();
		//chat.setPreferredSize(new Dimension(80, 0));
		//add(chat, BorderLayout.LINE_END);
		
		JPanel bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(0, 50));

		txtInput = new JTextField();
		txtInput.setPreferredSize(new Dimension(100, 20));
		bottom.add(txtInput);
		btnSubmit = new JButton("Submit");
		bottom.add(btnSubmit);
		btnPass = new JButton("Pass");
		bottom.add(btnPass);
		add(bottom, BorderLayout.PAGE_END);
	}
	
	public void setRound(JPanel round) {
		middle.add(round, BorderLayout.CENTER);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Game model = (Game)arg1;
		
		PlayerGame playerGame1, playerGame2;
		playerGame1 = model.getPlayerGame1();
		playerGame2 = model.getPlayerGame2();
		
		//TODO: Not sure which round i should choose to determ the round
		
		if(model.getRounds().size() > 0) 
			lblRoundType.setText(model.getCurrentRound().getRoundType().toString()); // TODO fix Exception in thread "AWT-EventQueue-0" java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
		lblPlayer1.setText(playerGame1.getPlayer().getName() + " : " + playerGame1.getTime());
		lblPlayer2.setText(playerGame2.getPlayer().getName() + " : " + playerGame2.getTime());
	}
	
	public void setChatPanel(ChatMessageView chatPanel){
		JPanel chat = chatPanel;
		chat.setPreferredSize(new Dimension(200, 200));
		chat.setBackground(Color.yellow);
		add(chat, BorderLayout.LINE_END);
	}
	
}
