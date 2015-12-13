package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

public class PlayerView extends JPanel {
	private JLabel playerNameLabel;
	private JButton challengeButton;

	public PlayerView(String playerName, Boolean enabled) {

		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);

		setPreferredSize(new Dimension(100,100));

		playerNameLabel = new JLabel();
		challengeButton = new JButton();

		try {
			Image img = ImageIO.read(new File("Assets/duel.png"));
			Image img2 = ImageIO.read(new File("Assets/duelPressed.png"));
			Image img3 = ImageIO.read(new File("Assets/duelSelected.png"));
			Image img4 = ImageIO.read(new File("Assets/duelDisabled.png"));
			challengeButton.setIcon(new ImageIcon(img));
			challengeButton.setPressedIcon(new ImageIcon(img2));
			challengeButton.setRolloverIcon(new ImageIcon(img3));
			challengeButton.setDisabledIcon(new ImageIcon(img4));
		} catch (IOException ex) { }

		challengeButton.setBorder(BorderFactory.createEmptyBorder());
		challengeButton.setContentAreaFilled(false);

		
		challengeButton.addActionListener(e -> handleButtonClick());


		playerNameLabel.setText(playerName);
		playerNameLabel.setFont(new Font("Serif", Font.ITALIC, 15));

		GridBagConstraints gridBagConstraintsLabel = new GridBagConstraints();
		gridBagConstraintsLabel.gridx = 0;
		gridBagConstraintsLabel.gridy = 0;
		gridBagConstraintsLabel.insets = new Insets(5,5,20,5);
		gridBagConstraintsLabel.fill = GridBagConstraints.HORIZONTAL;

		add(playerNameLabel, gridBagConstraintsLabel);

		challengeButton.setText("");

		GridBagConstraints gridBagConstraintsButton = new GridBagConstraints();
		gridBagConstraintsButton.gridx = 0;
		gridBagConstraintsButton.gridy = 1;
		gridBagConstraintsButton.fill = GridBagConstraints.HORIZONTAL;

		add(challengeButton, gridBagConstraintsButton);

		challengeButton.setEnabled(enabled);

		//249, 191, 147
		setBackground(new Color(135,171,255));
	}
	
	private void handleButtonClick() {
		challengeButton.setEnabled(false);
		sendChallenge();
	}
	
	private void sendChallenge() {
		// TODO Send a challenge to the opponent.
	}

}
