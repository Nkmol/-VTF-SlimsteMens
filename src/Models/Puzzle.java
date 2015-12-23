package Models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import Managers.DataManager;

public class Puzzle extends Round {

	private final static int QuestionAmount;
	private final static int AnswerPerQuestionAmount;
	private final static int AnswerAmount;
	static {
		QuestionAmount = 3;
		AnswerPerQuestionAmount = 4;
		AnswerAmount = QuestionAmount * AnswerPerQuestionAmount;
	}
	
	private int CurrentSharedQuestion;
	
	private ArrayList<Answer> PossibleAnswers;
	
	public Puzzle(Game game) {
		super(game);
		PossibleAnswers = new ArrayList<>();
		CurrentSharedQuestion = 0;
	}
	
	public Puzzle(ResultSet data, Game game) {
		super(data, game);
		PossibleAnswers = new ArrayList<>();
		CurrentSharedQuestion = 0;
	}
	
	public static int getQuestionAmount() {
		return QuestionAmount;
	}
	
	public static int getAnswerPerQuestionAmount() {
		return AnswerPerQuestionAmount;
	}
	
	public static int getAnswerAmount() {
		return AnswerAmount;
	}

	public void Submit(String SubmittedAnswer) {
		PlayerAnswer playerAnswer = new PlayerAnswer(currentTurn, generateAnswerId(), SubmittedAnswer, currentTurn.getTime());
		currentTurn.addPlayerAnswer(playerAnswer);
		if (GetQuestion().isPlayerAnswerCorrect(playerAnswer)) {
			NextQuestion();
		} else {
			SubmitTurn();
		}
		setChanged();
		notifyObservers();
	}
	
	public void GeneratePossibleAnswers() {
		// TODO: Generate the list of answers shown in the puzzle
		
		// just do it randomly for now
		SharedQuestion question;
		Random r = new Random();
		for (int I = 0; I < QuestionAmount; I++) {
			int Index = r.nextInt(getQuestions().size());
			question = new SharedQuestion(
					game.getId(), 
					RoundType.Puzzle, 
					currentTurn.getTurnId(), 
					I+1, 
					getQuestions().get(Index).getId(), 
					null);
			currentTurn.getSharedQuestions().add(question);
		}
	}
	
	private void NextQuestion() {
		if (CurrentSharedQuestion == QuestionAmount) {
			SubmitTurn();
			return;
		}
		CurrentSharedQuestion++;
	}
	
	private void SubmitTurn() {
		DataManager.getInstance().pushTurn(currentTurn);
	}
	
	public Question[] getPuzzleQuestions() {
		Question[] puzzleQuestions = new Question[QuestionAmount];

		for (int I = 0; I < puzzleQuestions.length && I < currentTurn.getSharedQuestions().size(); I++)
			for (Question question : getQuestions())
				if (currentTurn.getSharedQuestions().get(I).getQuestionId() == question.getId()) {
					 puzzleQuestions[I] = question;
					 break;
				}
		return puzzleQuestions;
	}
	
	public ArrayList<Answer> GetPossibleAnswers() {
		return PossibleAnswers;
	}
	
	public Question GetQuestion() {
		SharedQuestion sharedQuestion = getCurrentTurn().getSharedQuestions().get(CurrentSharedQuestion);
		for (Question question : getQuestions())
			if (sharedQuestion.getQuestionId() == question.getId())
				return question;
		return null;
	}

	public void onSubmit(String str) {
		// TODO Auto-generated method stub
		
	}
	
	// TODO: Delete method
	public void OnSubmit() {
		
	}
}
