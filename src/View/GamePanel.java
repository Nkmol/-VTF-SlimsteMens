package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Managers.DataManager;
import Models.Game;
import Models.Player;
import Models.Round;

public class GamePanel extends JPanel implements Observer{
	
	private JPanel middle;
	private JLabel lblPlayer1, lblPlayer2, lblRoundType;
	public JButton btnSubmit, btnPass;
	public JTextField txtInput;
	private boolean initOpponentTurn;
	
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
		middle.setLayout(new BorderLayout());
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

	public void setOponnonentTime(Player player1, Player player2, int gameId) {
		if(!Game.isCurrentUser(player1.getName())) {
			String strPlayer1 = player1.getName() + " : " + DataManager.getInstance().getTotalSecondsEarnedInAGame(gameId, player1.getName());
			lblPlayer1.setText(strPlayer1); 
		}
		else if(!Game.isCurrentUser(player2.getName())) {
			String strPlayer2 = player2.getName() + " : " + DataManager.getInstance().getTotalSecondsEarnedInAGame(gameId, player2.getName());
			lblPlayer2.setText(strPlayer2);
		}
		
		initOpponentTurn = true;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		Game model = (Game)arg1;
		
		Player player1, player2;
		player1 = model.getPlayer1();
		player2 = model.getPlayer2();
		
		if(model.getCurrentRound() != null && model.getCurrentRound().getCurrentTurn() != null) {
			lblRoundType.setText(model.getCurrentRound().getRoundType().toString());

		
			if(Game.isCurrentUser(player1.getName())) {
				String strPlayer1 = player1.getName() + " : " + (DataManager.getInstance().getTotalSecondsEarnedInAGame(model.getId(), player1.getName()) + model.getCurrentRound().getCurrentTurn().getSecondsEarned());
				lblPlayer1.setText(strPlayer1); 
			}
			else if(Game.isCurrentUser(player2.getName())) {
				String strPlayer2 = player2.getName() + " : " + (DataManager.getInstance().getTotalSecondsEarnedInAGame(model.getId(), player2.getName()) + model.getCurrentRound().getCurrentTurn().getSecondsEarned());
				lblPlayer2.setText(strPlayer2);
			}
		}
		
		if(!initOpponentTurn)
			setOponnonentTime(player1, player2, model.getId());
	}
	
	public void setChatPanel(ChatMessageView chatPanel){
		JPanel chat = chatPanel;
		chat.setPreferredSize(new Dimension(200, 200));
		add(chat, BorderLayout.LINE_END);
	}
	
}
