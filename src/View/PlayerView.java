package View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controllers.PlayerListController;
import Models.ChallengedPlayer;
import Models.CompetitionRankItem;
import Models.Player;

public class PlayerView extends JPanel {
	private JLabel playerNameLabel;
	private JLabel totalGamesLabel;
	private JLabel winsLabel;
	private JLabel losesLabel;
	private JLabel timeLeftLabel;
	private JButton challengeButton;
	private Player player;
	private CompetitionRankItem rank;
	private PlayerListController controller;

	public PlayerView(Player player, CompetitionRankItem rank, Boolean enabled, PlayerListController controller) {
		this.player = player;
		this.rank = rank;
		this.controller = controller;
		
		// Main Layout
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		add(container, BorderLayout.CENTER);

		container.setPreferredSize(new Dimension(400,70));
		
		// Left and Right Layout
		JPanel leftView = new JPanel();
		leftView.setLayout(new BorderLayout());	
		
		JPanel rightView = new JPanel();
		rightView.setLayout(new BorderLayout());
		
		container.add(leftView, BorderLayout.CENTER);
		container.add(rightView, BorderLayout.EAST);
		
		// Left Layout
		JPanel leftLeftView = new JPanel();
		leftLeftView.setLayout(new BorderLayout());
		JPanel leftRightView = new JPanel();
		leftRightView.setLayout(new BorderLayout());
		
		leftView.add(leftLeftView, BorderLayout.CENTER);
		leftView.add(leftRightView, BorderLayout.EAST);
		
		// Right Layout
		JPanel rightLeftView = new JPanel();
		rightLeftView.setLayout(new BorderLayout());
		JPanel rightRightView = new JPanel();
		rightRightView.setLayout(new BorderLayout());
		
		rightView.add(rightLeftView, BorderLayout.WEST);
		rightView.add(rightRightView, BorderLayout.EAST);
		
		// Content
		JPanel nameView = createNameView();
		leftLeftView.add(nameView, BorderLayout.CENTER);
		
		JPanel totalGamesView = createTotalGamesView();
		leftRightView.add(totalGamesView, BorderLayout.WEST);
		
		JPanel winsView = createWinsView();
		leftRightView.add(winsView, BorderLayout.EAST);
		
		JPanel losesView = createLosesView();
		rightLeftView.add(losesView, BorderLayout.WEST);
		
		JPanel timeLeftView = createTimeLeftView();
		rightLeftView.add(timeLeftView, BorderLayout.EAST);
		
		JPanel buttonView = createButtonView(enabled);
		rightRightView.add(buttonView, BorderLayout.CENTER);		

		// 135, 171, 255
		//setBackground(new Color(135,171,255));
	}
	
	
	private JPanel createNameView() {
		JPanel nameView = new JPanel();
		nameView.setLayout(new BorderLayout());
		
		JPanel nameTitleView = new JPanel();
		nameTitleView.setBackground(new Color(47,109,255));
		
		JLabel playerNameTitleLabel = new JLabel();
		playerNameTitleLabel.setText("Naam");
		playerNameTitleLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		nameTitleView.add(playerNameTitleLabel);
		
		JPanel nameContentView = new JPanel();
		nameContentView.setBackground(new Color(135,171,255));
		
		playerNameLabel = new JLabel();
		playerNameLabel.setText(player.getName());
		playerNameLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		nameContentView.add(playerNameLabel);	
		
		nameView.add(nameTitleView, BorderLayout.NORTH);
		nameView.add(nameContentView, BorderLayout.CENTER);
		
		return nameView;
	}
	
	private JPanel createTotalGamesView() {
		JPanel totalGamesView = new JPanel();
		totalGamesView.setLayout(new BorderLayout());
		
		JPanel totalGamesTitleView = new JPanel();
		totalGamesTitleView.setBackground(new Color(47,109,255));
		
		JLabel totalGamesTitleLabel = new JLabel();
		totalGamesTitleLabel.setText("Aantal games");
		totalGamesTitleLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		totalGamesTitleView.add(totalGamesTitleLabel);
		
		JPanel totalGamesContentView = new JPanel();
		totalGamesContentView.setBackground(new Color(135,171,255));
		
		totalGamesLabel = new JLabel();
		totalGamesLabel.setText(String.valueOf(rank.getAmountPlayedGames()));
		totalGamesLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		totalGamesContentView.add(totalGamesLabel);	
		
		totalGamesView.add(totalGamesTitleView, BorderLayout.NORTH);
		totalGamesView.add(totalGamesContentView, BorderLayout.CENTER);
		
		return totalGamesView;
	}
	
	private JPanel createWinsView() {	
		JPanel winsView = new JPanel();
		winsView.setLayout(new BorderLayout());
		
		JPanel winsTitleView = new JPanel();
		winsTitleView.setBackground(new Color(47,109,255));
		
		JLabel winsTitleLabel = new JLabel();
		winsTitleLabel.setText("wins");
		winsTitleLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		winsTitleView.add(winsTitleLabel);
		
		JPanel winsContentView = new JPanel();
		winsContentView.setBackground(new Color(135,171,255));
		
		winsLabel = new JLabel();
		winsLabel.setText(String.valueOf(rank.getAmountGamesWon()));
		winsLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		winsContentView.add(winsLabel);	
		
		winsView.add(winsTitleView, BorderLayout.NORTH);
		winsView.add(winsContentView, BorderLayout.CENTER);
		
		return winsView;
	}
	
	private JPanel createLosesView() {
		JPanel losesView = new JPanel();
		losesView.setLayout(new BorderLayout());
		
		JPanel losesTitleView = new JPanel();
		losesTitleView.setBackground(new Color(47,109,255));
		
		JLabel losesTitleLabel = new JLabel();
		losesTitleLabel.setText("loses");
		losesTitleLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		losesTitleView.add(losesTitleLabel);
		
		JPanel losesContentView = new JPanel();
		losesContentView.setBackground(new Color(135,171,255));
		
		losesLabel = new JLabel();
		losesLabel.setText(String.valueOf(rank.getAmountGamesLost()));
		losesLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		losesContentView.add(losesLabel);	
		
		losesView.add(losesTitleView, BorderLayout.NORTH);
		losesView.add(losesContentView, BorderLayout.CENTER);
		
		return losesView;
	}
	
	private JPanel createTimeLeftView() {
		JPanel timeLeftView = new JPanel();
		timeLeftView.setLayout(new BorderLayout());
		
		JPanel timeLeftTitleView = new JPanel();
		timeLeftTitleView.setBackground(new Color(47,109,255));
		
		JLabel timeLeftTitleLabel = new JLabel();
		timeLeftTitleLabel.setText("Tijd over");
		timeLeftTitleLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		timeLeftTitleView.add(timeLeftTitleLabel);
		
		JPanel timeLeftContentView = new JPanel();
		timeLeftContentView.setBackground(new Color(135,171,255));
		
		timeLeftLabel = new JLabel();
		timeLeftLabel.setText(String.valueOf(rank.getAverageSecondsLeft()));
		timeLeftLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		timeLeftContentView.add(timeLeftLabel);	
		
		timeLeftView.add(timeLeftTitleView, BorderLayout.NORTH);
		timeLeftView.add(timeLeftContentView, BorderLayout.CENTER);
		
		return timeLeftView;
	}
	
	private JPanel createButtonView(Boolean enabled) {
		JPanel buttonView = new JPanel();
		buttonView.setLayout(new BorderLayout());
		
		buttonView.setBackground(new Color(47,109,255));
		
		challengeButton = new JButton();

		try {
			Image img = ImageIO.read(getClass().getResource("/duel.png"));
			Image img2 = ImageIO.read(getClass().getResource("/duelPressed.png"));
			Image img3 = ImageIO.read(getClass().getResource("/duelSelected.png"));
			Image img4 = ImageIO.read(getClass().getResource("/duelDisabled.png"));
			challengeButton.setIcon(new ImageIcon(img));
			challengeButton.setPressedIcon(new ImageIcon(img2));
			challengeButton.setRolloverIcon(new ImageIcon(img3));
			challengeButton.setDisabledIcon(new ImageIcon(img4));
		} catch (IOException ex) { }

		challengeButton.setBorder(BorderFactory.createEmptyBorder());
		challengeButton.setContentAreaFilled(false);
		challengeButton.addActionListener(e -> handleButtonClick());
		challengeButton.setText("");
		challengeButton.setEnabled(enabled);
		
		buttonView.add(challengeButton, BorderLayout.SOUTH);
		
		return buttonView;
	}
	
	private void handleButtonClick() {
		challengeButton.setEnabled(false);
		sendChallenge();
	}
	
	private void sendChallenge() {
		// TODO use classes instead of names.
		controller.handleButtonClick(player);
	}


	public void updateView(ChallengedPlayer playerUpdate) {
		playerNameLabel.setText(playerUpdate.getName());
		totalGamesLabel.setText(String.valueOf(playerUpdate.getRank().getAmountPlayedGames()));
		winsLabel.setText(String.valueOf(playerUpdate.getRank().getAmountGamesWon()));
		losesLabel.setText(String.valueOf(playerUpdate.getRank().getAmountGamesLost()));
		timeLeftLabel.setText(String.valueOf(playerUpdate.getRank().getAverageSecondsLeft()));		
		challengeButton.setEnabled(!playerUpdate.isChallenged());
		
		if(playerUpdate.getInviter()) {
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.red, 1), "Uitgedaagd door", 0, 0, new Font("Serif", Font.ITALIC, 15)));
		}
		else {
			setBorder(null);
		}
	}

}
