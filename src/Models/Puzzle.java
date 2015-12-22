package Models;

import java.sql.ResultSet;

public class Puzzle extends Round {

	private final static int AnswerAmount = 4;
	
	public Puzzle(Game game) {
		super(game);
		
	}
	
	public Puzzle(ResultSet data, Game game) {
		super(data, game);
	}
	
	public static int getAnswerAmount() {
		return AnswerAmount;
	}
	
	public Question GetQuestion() {
		SharedQuestion sharedQuestion = turn.getSharedQuestions().get(turn.getSharedQuestions().size() - 1);
		for (Question question : getQuestions())
			if (sharedQuestion.getQuestionId() == question.getId())
				return question;
		return null;
	}
}
