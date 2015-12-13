package Models;

public enum Role {
	Player,
	Observer;
	
	public String getValue() {
		switch (this) {
		case Player:
			return "player";
		case Observer:
			return "observer";
		default:
			return null;
		}
	}
}