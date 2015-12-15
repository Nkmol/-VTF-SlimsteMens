package View;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;



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
		setLayout(new GridLayout(2, 2, 0, 0));
	}
	
	public ReplayListItem (
			int Id, 
			String Opponent, 
			int PlayerScore, 
			int OpponentScore) {
		this();
		this.Id = Id;
		
		JLabel lblOpponent = new JLabel("Tegen: " + Opponent);
		add(lblOpponent);
		
		JLabel lblScore = new JLabel("jouw score: " + PlayerScore + " - OpponentScore: " + OpponentScore);
		add (lblScore);
		
		JLabel lblResult = new JLabel();
		
		if (PlayerScore == OpponentScore) {
			lblResult.setText("Gelijkspel");
		}
		else if (PlayerScore > OpponentScore) {
			lblResult.setText("Gewonnen");
		}
		else {
			lblResult.setText("Verloren");
		}
			
		add(lblResult);
		
		addMouseListener(new ReplayMouseAdapter());
	}
	
	public int getGameId()
	{
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
