package Models;

public enum GameState {
	Invited,
	Rejected,
	Busy,
	Finished;
	
	public static GameState fromString(String value) {
		switch (value) {
		case "uitgedaagd":
			return Invited;
		case "verworpen":
			return Rejected;
		case "bezig":
			return Busy;
		case "afgelopen":
			return Finished;
		default:
			return null;
		}
	}
	
	public String getValue() {
		switch (this) {
		case Invited:
			return "uitgedaagd";
		case Rejected:
			return "verworpen";
		case Busy:
			return "bezig";
		case Finished:
			return "afgelopen";
		default:
			return null;
		}
	}
}
