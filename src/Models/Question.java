package Models;

public class Question {
	
	private int id;
	private Round round;
	private String text;
	private Answer answer;
	
	public Question(int id, Round round, String text) {
		this.id = id;
		this.round = round;
		this.text = text;
	}
	
}
