package Controllers;

import java.util.ArrayList;

import Managers.DataManager;
import Models.Player;
import Models.PlayerAnswer;
import Models.RoundType;
import Models.SharedQuestion;
import Models.Turn;
import Models.TurnState;
import Utilities.ComponentUtility;
import View.LoginPanel;
		

public class LoginController {
	private MainController parent;
	private LoginPanel view;
	private Player model;
	
	public LoginController(MainController parent) {
		this.parent = parent;
		view = new LoginPanel();
		model = new Player();
		model.addObserver(view);
		
		ComponentUtility.addActionListener(view, "btnLogin", (e) -> btnLogin_Press());
		ComponentUtility.addActionListener(view, "btnReg", (e) -> btnReg_Press());
	}
	
	private void btnLogin_Press() {
		if(model.login(view.txtUsername.getText(), view.txtPassword.getText())) {
			// Show main frame
			parent.ShowMainPanel();
			
			// Save logged player to file
			model.saveName();

			//TODO: Use ScreenManager?
			
			// Test value for the category
			//parent.SetViewCategoryPanel(new AccountController(parent).getView());
			
			// Right Screen 
		}
	}
	
	private void pushTestTurn() {
		Player currentUser = DataManager.getInstance().getCurrentUser();
		System.out.println(currentUser);
		
		Turn testTurn = new Turn(RoundType.ThreeSixNine, currentUser, 511);
		testTurn.setTurnId(7);
		testTurn.setQuestionId(33);
		testTurn.setTurnState(TurnState.Correct);
		testTurn.setSecondsEarned(20);
		
		SharedQuestion sharedQuestion = new SharedQuestion(testTurn, 1, "Test antwoord");
		ArrayList<SharedQuestion> sharedQuestions = new ArrayList<>();
		sharedQuestions.add(sharedQuestion);
		testTurn.setSharedQuestions(sharedQuestions);
		
		PlayerAnswer playerAnswer = new PlayerAnswer(testTurn, 1, "Test antwoord", 44);
		ArrayList<PlayerAnswer> playerAnswers = new ArrayList<>();
		playerAnswers.add(playerAnswer);
		testTurn.setPlayerAnswers(playerAnswers);
		
		DataManager.getInstance().pushTurn(testTurn);
	}
	
	private void btnReg_Press() {
		parent.showRegister();
	}
	
	public LoginPanel getView() {
		return view;
	}
}
