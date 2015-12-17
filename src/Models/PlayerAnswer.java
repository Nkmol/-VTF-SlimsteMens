package Models;

import java.util.ArrayList;
import java.util.Collection;

import Utilities.StringUtility;

public class PlayerAnswer {

	private int answerId;
	private String answer;
	private int moment;
	
	private static final int MinimumAnswerPercentage = 80;
	
	public PlayerAnswer(int answerId, String answer, int moment) {
		this.answerId = answerId;
		this.answer = answer;
		this.moment = moment;
	}
	
	public int getAnswerId() {
		return answerId;
	}

	public String getAnswer() {
		return answer;
	}

	public int getMoment() {
		return moment;
	}
	
	public boolean IsCorrect(Answer answer) {
		return Game.IsPlayerAnswerCorrect(getAnswer(), answer);
	}
	
	public boolean IsCorrect(Collection<Answer> answers) {
		return Game.IsPlayerAnswerCorrect(getAnswer(), answers);
	}
	
	public void submit(Turn turn) {
		//TODO: extract round name and turn id from turn
	}
	
}
