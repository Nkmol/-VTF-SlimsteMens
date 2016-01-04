package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controllers.ChallengeListController;

public class ChallengeView extends JPanel {

	ChallengeListController controller;
	private int gameId;
	public JButton declineButton;
	
	public ChallengeView(int gameId, String name, ChallengeListController controller) {
		
		this.controller = controller;
		this.gameId = gameId;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);	
		
		setPreferredSize(new Dimension(400,100));
		
		JLabel messageLabel = new JLabel();
		messageLabel.setText("You have been challenged by " + name + "!");
		messageLabel.setForeground(Color.white);
		messageLabel.setFont(new Font("Serif", Font.ITALIC, 18));
		
		GridBagConstraints gridBagConstraintsMessage = new GridBagConstraints();
		gridBagConstraintsMessage.gridx = 0;
		gridBagConstraintsMessage.gridy = 1;
		gridBagConstraintsMessage.weightx = 0.5;
		gridBagConstraintsMessage.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintsMessage.insets = new Insets(0, 20, 0, 0);
		
		add(messageLabel, gridBagConstraintsMessage);
		
		JButton acceptButton = new JButton();
		acceptButton.setText("Accept");
		acceptButton.setBackground(Color.GREEN);
		acceptButton.setBounds(300, 50, 100, 50);
		
		GridBagConstraints gridBagConstraintsAccept = new GridBagConstraints();
		gridBagConstraintsAccept.gridx = 5;
		gridBagConstraintsAccept.gridy = 0;
		gridBagConstraintsAccept.weightx = 0.5;
		gridBagConstraintsAccept.fill = GridBagConstraints.VERTICAL;
		
		acceptButton.addActionListener(e -> acceptChallenge());
		
		add(acceptButton, gridBagConstraintsAccept);
		
		declineButton = new JButton();
		declineButton.setText("Decline");
		declineButton.setBackground(Color.RED);
		acceptButton.setBounds(300, 150, 100, 50);
		
		GridBagConstraints gridBagConstraintsDecline = new GridBagConstraints();
		gridBagConstraintsDecline.gridx = 5;
		gridBagConstraintsDecline.gridy = 2;
		gridBagConstraintsDecline.weightx = 0.5;
		gridBagConstraintsDecline.fill = GridBagConstraints.VERTICAL;
		
		declineButton.addActionListener(e -> rejectChallenge());
		
		add(declineButton, gridBagConstraintsDecline);
	}
	
	@Override
	  protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	    
	    Image img = null;
	    
		try {
			img = ImageIO.read(getClass().getResource("/ChallengeMessage.png"));
		} catch (IOException ex) {
		}
		 
	     g.drawImage(img, 0, 0, null);
	}
	
	private void acceptChallenge() {
		controller.handleAcceptButtonClick(gameId);
	}
	
	private void rejectChallenge() {
		//controller.handleRejectButtonClick(gameId);
	}
}
