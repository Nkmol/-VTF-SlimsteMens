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
	
	Border DefaultBorder;
    Border HoverBorder;
	int Id;
	public ReplayListItem (
			int Id, 
			String Opponent, 
			Boolean won, 
			int PlayerScore, 
			int OpponentScore) {
		DefaultBorder = BorderFactory.createLineBorder(Color.BLACK);
	    HoverBorder = BorderFactory.createLineBorder(Color.RED,5);
		
		setBorder(DefaultBorder);
		setLayout(new GridLayout(2, 2, 0, 0));
		this.Id = Id;
		
		// TODO: Fix layout
		
		JLabel lblOpponent = new JLabel("Tegen: " + Opponent);
		add(lblOpponent);
		
		JLabel lblScore = new JLabel("jouw score: " + PlayerScore + " - OpponentScore: " + OpponentScore);
		add (lblScore);
		
		JLabel lblGewonnen = new JLabel((won) ? "Gewonnen" : "Verloren");
		add(lblGewonnen);
		
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
