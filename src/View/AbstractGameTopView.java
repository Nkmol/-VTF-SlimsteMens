package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AbstractGameTopView extends JPanel {
	
	private JLabel playerOneNameLabel;
	private JLabel playerTwoNameLabel;
	
	private JLabel playerOneTimeLabel;
	private JLabel playerTwoTimeLabel;
	
	private JLabel currentRoundLabel;

	public AbstractGameTopView() {

		setLayout(null);
		setPreferredSize(new Dimension(450,100));

		playerOneNameLabel = new JLabel();
		playerOneNameLabel.setBounds(30,15,50,15);	
		playerOneNameLabel.setFont(new Font("Serif", Font.ITALIC, 18));
		playerOneNameLabel.setForeground(Color.WHITE);
		playerOneNameLabel.setText("karel");
		add(playerOneNameLabel);
		
		playerTwoNameLabel = new JLabel();
		playerTwoNameLabel.setBounds(380,15,50,15);	
		playerTwoNameLabel.setFont(new Font("Serif", Font.ITALIC, 20));
		playerTwoNameLabel.setForeground(Color.WHITE);
		playerTwoNameLabel.setText("Henk");
		add(playerTwoNameLabel);
		
		playerOneTimeLabel = new JLabel();
		playerOneTimeLabel.setBounds(25,55,50,16);	
		playerOneTimeLabel.setFont(new Font("Serif", Font.ITALIC, 20));
		playerOneTimeLabel.setForeground(Color.BLACK);
		playerOneTimeLabel.setText("00:10");
		add(playerOneTimeLabel);
		
		playerTwoTimeLabel = new JLabel();
		playerTwoTimeLabel.setBounds(377,55,50,15);	
		playerTwoTimeLabel.setFont(new Font("Serif", Font.ITALIC, 20));
		playerTwoTimeLabel.setForeground(Color.BLACK);
		playerTwoTimeLabel.setText("00:20");
		add(playerTwoTimeLabel);
		
		currentRoundLabel = new JLabel();
		currentRoundLabel.setBounds(192,42,80,15);	
		currentRoundLabel.setFont(new Font("Serif", Font.ITALIC, 20));
		currentRoundLabel.setForeground(Color.WHITE);
		currentRoundLabel.setText("Round 1");
		add(currentRoundLabel);

		//249, 191, 147
		setBackground(Color.BLACK);
	}

	@Override
	  protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	    
	    Image img = null;
	    
		try {
			img = ImageIO.read(new File("Assets/GameTop.png"));
		} catch (IOException ex) {
		}
		 
	     g.drawImage(img, 0, 0, null);
	}
}
