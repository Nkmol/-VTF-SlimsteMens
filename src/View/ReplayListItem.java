package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Models.GameScore;

@SuppressWarnings("serial")
public class ReplayListItem extends JPanel {
	
	private Border DefaultBorder;
    private Border HoverBorder;
	private int Id;
	
	private ReplayListItem()
	{
		DefaultBorder = BorderFactory.createLineBorder(Color.BLACK);
	    HoverBorder = BorderFactory.createLineBorder(Color.RED,2);
		
		setBorder(DefaultBorder);
		setPreferredSize(new Dimension(getWidth(), 50));
		setLayout(new GridLayout(2, 2, 0, 0));
		setToolTipText("Klik om het verloop van deze match te zien.");
	}
	
	public ReplayListItem(GameScore gameScore) {		
		this(
			gameScore.getGameId(), 
			gameScore.getOpponentName(),
			gameScore.getWinner(),
			gameScore.getPlayer1Seconds(), 
			gameScore.getPlayer2Seconds()
		);
	}
	
	public ReplayListItem (
			int Id, 
			String Opponent, 
			String Result,
			int Score1, 
			int Score2) {
		this();
		this.Id = Id;
		
		JLabel lblOpponent = new JLabel("Tegen: " + Opponent);
		add(lblOpponent);
		
		JLabel lblScore = new JLabel("Score: " + Score1 + " - " + Score2);
		add (lblScore);
		
		JLabel lblResult = new JLabel();
		
		if (Result == null)
			lblResult.setText("Gelijk spel");
		else if (Result.equals(Opponent))
			lblResult.setText("Verloren");
		else
			lblResult.setText("Gewonnen");
		
		add(lblResult);
		
		addMouseListener(new ReplayMouseAdapter());
	}
	
	public int getGameId() {
		return Id;
	}
	
	private class ReplayMouseAdapter extends MouseAdapter {
		
		public void mouseEntered(MouseEvent e){
			setBorder(HoverBorder);
		}
		
		public void mouseExited(MouseEvent e){
			setBorder(DefaultBorder);
		}
	}
}
