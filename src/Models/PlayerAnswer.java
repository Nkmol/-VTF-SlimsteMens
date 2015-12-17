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
		if (StringUtility.CalculateMatchPercentage(getAnswer(), answer.getAnswer()) >=  MinimumAnswerPercentage)
			return true;
		for (String alternative : answer.getAlternatives())
			if (StringUtility.CalculateMatchPercentage(getAnswer(), alternative) >=  MinimumAnswerPercentage)
				return true;
		return false;
	}
	
	public boolean IsCorrect(Collection<Answer> answers) {
		for (Answer answer : answers) {
			if (StringUtility.CalculateMatchPercentage(getAnswer(), answer.getAnswer()) >=  MinimumAnswerPercentage)
				return true;
			for (String alternative : answer.getAlternatives())
				if (StringUtility.CalculateMatchPercentage(getAnswer(), alternative) >=  MinimumAnswerPercentage)
					return true;
		}
		return false;
	}
	
	public void submit(Turn turn) {
		//TODO: extract round name and turn id from turn
	}
	
}
