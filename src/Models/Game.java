package Models;

import java.util.ArrayList;

import Utilities.StringUtility;

public class Game {
	
	private int id;
	private Player player1;
	private Player player2;
	private GameState gameState;
	private Round currentRound;
	private ArrayList<ChatMessage> chatMessages;
	
	private final int MinimumAnswerPercentage = 80;
	
	public Game(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
	}
	
	public boolean IsPlayerAnswerCorrect(PlayerAnswer player, Answer answer){
		
		if (StringUtility.CalculateMatchPercentage(player.getAnswer(), answer.getAnswer()) >=  MinimumAnswerPercentage)
			return true;
		
		for (String Alternative : answer.getAlternatives())
			if (StringUtility.CalculateMatchPercentage(player.getAnswer(), Alternative) >=  MinimumAnswerPercentage)
				return true;
		
		return false;
	}
	
}
