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

import Controllers.ActiveGameController;
import Models.Player;

public class ActiveGameView extends JPanel {
	private JLabel playerNameLabel;
	public JButton goButton, chatButton;
	private Player player;
	private int gameId;
	private ActiveGameController controller;

	public ActiveGameView(Player player, int gameId, ActiveGameController controller) {
		this.player = player;
		this.gameId = gameId;
		this.controller = controller;

		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);

		setPreferredSize(new Dimension(145, 80));

		playerNameLabel = new JLabel();
		goButton = new JButton();
		chatButton = new JButton();

		// goButton.setBorder(BorderFactory.createEmptyBorder());

		playerNameLabel.setText(player.getName());
		playerNameLabel.setFont(new Font("Serif", Font.ITALIC, 15));

		GridBagConstraints gridBagConstraintsLabel = new GridBagConstraints();
		gridBagConstraintsLabel.gridx = 0;
		gridBagConstraintsLabel.gridy = 0;
		gridBagConstraintsLabel.insets = new Insets(5, 5, 20, 5);
		gridBagConstraintsLabel.fill = GridBagConstraints.HORIZONTAL;

		add(playerNameLabel, gridBagConstraintsLabel);

		goButton.setText("Ga");
		goButton.addActionListener((e) -> handleButtonClick());
		
		chatButton.setText("Chat");
		chatButton.addActionListener((e) -> handleChatButtonClick());
		chatButton.setVisible(false);
		
		GridBagConstraints gridBagConstraintsButton = new GridBagConstraints();
		gridBagConstraintsButton.gridx = 0;
		gridBagConstraintsButton.gridy = 1;
		gridBagConstraintsButton.fill = GridBagConstraints.HORIZONTAL;

		add(chatButton, gridBagConstraintsButton);
		add(goButton, gridBagConstraintsButton);

		// 135, 171, 255
		setBackground(new Color(135, 171, 255));
	}
	
/*	public void hideGoButton() {
		goButton.setVisible(false);
	}*/
	
	public void unhideGoButton() {
		goButton.setVisible(true);
	}

	private void handleButtonClick() { // TODO TEMP
		controller.handleButtonClick(gameId);
	}
	
	private void handleChatButtonClick() { // TODO TEMP
		controller.handleChatButtonClick(gameId);
	}

	public void showChatButton() {
		goButton.setVisible(false);
		chatButton.setVisible(true);
	}
	
	public void hideChatButton() {
		goButton.setVisible(true);
		chatButton.setVisible(false);
	}
}
