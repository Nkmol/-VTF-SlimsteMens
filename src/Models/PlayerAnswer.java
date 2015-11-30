package Models;

public class PlayerAnswer {

	private int answerId;
	private String answer;
	private int moment;
	
	public PlayerAnswer(int answerId, String answer, int moment) {
		this.answerId = answerId;
		this.answer = answer;
		this.moment = moment;
	}
	
	public void submit(Turn turn) {
		//TODO: extract round name and turn id from turn
	}
	
}
