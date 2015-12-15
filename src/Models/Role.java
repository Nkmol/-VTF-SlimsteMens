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
	
	public static Role fromString(String value) {
		switch (value) {
		case "player":
			return Player;
		case "observer":
			return Observer;
		default:
			return null;
		}
	}
}