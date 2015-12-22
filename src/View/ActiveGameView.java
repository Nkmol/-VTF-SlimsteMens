package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Models.Player;

public class ActiveGameView extends JPanel {
	private JLabel playerNameLabel;
	public JButton goButton;
	private Player player;

	public ActiveGameView(Player player) {
		this.player = player;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);

		setPreferredSize(new Dimension(145,80));

		playerNameLabel = new JLabel();
		goButton = new JButton();

		//goButton.setBorder(BorderFactory.createEmptyBorder());

		playerNameLabel.setText(player.getName());
		playerNameLabel.setFont(new Font("Serif", Font.ITALIC, 15));

		GridBagConstraints gridBagConstraintsLabel = new GridBagConstraints();
		gridBagConstraintsLabel.gridx = 0;
		gridBagConstraintsLabel.gridy = 0;
		gridBagConstraintsLabel.insets = new Insets(5,5,20,5);
		gridBagConstraintsLabel.fill = GridBagConstraints.HORIZONTAL;

		add(playerNameLabel, gridBagConstraintsLabel);

		goButton.setText("Go");

		GridBagConstraints gridBagConstraintsButton = new GridBagConstraints();
		gridBagConstraintsButton.gridx = 0;
		gridBagConstraintsButton.gridy = 1;
		gridBagConstraintsButton.fill = GridBagConstraints.HORIZONTAL;

		add(goButton, gridBagConstraintsButton);

		// 135, 171, 255
		setBackground(new Color(135,171,255));
	}
}
