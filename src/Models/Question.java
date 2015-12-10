package Models;

public class Question {
	
	private int id;
	private Round round;
	private String text;
	
	public Question(int id, Round round, String text) {
		this.id = id;
		this.round = round;
		this.text = text;
	}
	
}
