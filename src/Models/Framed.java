package Models;

import java.sql.ResultSet;
import java.util.ArrayList;

import Managers.DataManager;

public class Framed extends Round {

	private final static int AMOUNT_QUESTIONS = 9,
							 BONUS_ITERATION = 3,
							 POINTS_QUESTION = 20,
							 AMOUNT_ANSWERS = 10;
	private int amountCorrectAnswers = 0;
	private int secondsEarned = 0;
	private ArrayList<PlayerAnswer> playerAnswers;
	
	public Framed(Game game) {
		super(game, RoundType.Framed);	
		init();
	}
	
	public Framed(ResultSet data, Game game) {
		super(data,game);
		init();
	}
	
	public void init() {
		//questions = DataManager.getInstance().getQuestions(this);
		updateView();
		DataManager.getInstance().pushTurn(currentTurn);
	}
	
	public void initNewTurn() {
		amountCorrectAnswers = 0;
		currentTurn = initCurrentTurn(this);
		DataManager.getInstance().pushTurn(currentTurn);
		updateView();
	}

	@Override
	public void onSubmit(String answer) {
		// TODO Auto-generated method stub
		System.out.println("your answers is " + answer);
		if (currentTurn.getCurrentQuestion().isPlayerAnswerCorrect(answer)) 
			Turn.pushTurn(currentTurn, TurnState.Correct, answer);
		else 
			Turn.pushTurn(currentTurn, TurnState.Wrong, answer);
		

		if (playerAnswers == null)
			playerAnswers = new ArrayList<>();
		
		int answerId = playerAnswers.size() + 1;
		PlayerAnswer playerAnswer = new PlayerAnswer(currentTurn, answerId, answer, 10); //TODO: change the moment
		DataManager.getInstance().pushPlayerAnswer(playerAnswer);
		playerAnswers.add(playerAnswer);
		
		Question currentQuestion = (currentTurn.getSkippedQuestion() != null) ? currentTurn.getSkippedQuestion() : currentTurn.getCurrentQuestion();
		
		System.out.println("Question id: " + currentQuestion.getId());
		
		if (currentQuestion != null) {
			if (currentQuestion.isPlayerAnswerCorrect(answer)) {
				amountCorrectAnswers++; 
				secondsEarned+=POINTS_QUESTION;
			}
		}		
			
		updateView();
		
		if (amountCorrectAnswers == AMOUNT_ANSWERS) {
			currentTurn.setTurnState(TurnState.Correct);
			currentTurn.setSecondsEarned(secondsEarned);
			DataManager.getInstance().updateTurn(currentTurn);
			amountCorrectAnswers = 0;
			if (currentTurn.getSkippedQuestion() == null)
				getGame().getController().endTurn();
			else 
				initNewTurn();
		}
	
	}

	@Override
	public void onPass() {
		//TODO: further implementation
		currentTurn.setTurnState(TurnState.Pass);
		DataManager.getInstance().updateTurn(currentTurn);
		if (currentTurn.getSkippedQuestion() == null)
			getGame().getController().endTurn();
		else 
			initNewTurn();
	}
	
	public ArrayList<PlayerAnswer> getSubmittedAnswers() {
		return playerAnswers;
	}

}
