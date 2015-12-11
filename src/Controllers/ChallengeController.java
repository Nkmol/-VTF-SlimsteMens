package Controllers;

import java.util.ArrayList;

import View.ChallengeView;

public class ChallengeController {

	ArrayList<ChallengeView> challengeViews = new ArrayList<ChallengeView>();
	String[] challengeList;
	
	public ChallengeController() {
		challengeList = getChallenges();
			
		for(int i = 0; i < challengeList.length; i++) {
			ChallengeView challengeView = new ChallengeView(challengeList[i]);
			challengeViews.add(challengeView);
		}
	}
	
	public String[] getChallenges() {
		String[] challengesList = new String[5];
		
		challengesList[0] = new String("Henk");
		challengesList[1] = new String("Piet");
		challengesList[2] = new String("Jan");
		challengesList[3] = new String("Willem");
		challengesList[4] = new String("Peter");
		
		return challengesList;
	}
	
	public ChallengeView[] getChallengeViews() {
		return challengeViews.toArray(new ChallengeView[challengeViews.size()]);
	}
}
