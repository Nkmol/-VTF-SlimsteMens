package Models;

import java.util.ArrayList;

public class Answer {

	private int questionId;
	private String answer;
	private ArrayList<String> alternatives;
	
	public Answer(int questionId, String answer, ArrayList<String> alternatives) {
		this.questionId = questionId;
		this.answer = answer;
		this.alternatives = alternatives;
	}
	
	public int getQuestionId() {
		return questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public ArrayList<String> getAlternatives() {
		return alternatives;
	}
}
