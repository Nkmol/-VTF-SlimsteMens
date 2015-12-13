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
			return "Uitgenodigd";
		case Rejected:
			return "Verworpen";
		case Busy:
			return "Bezig";
		case Finished:
			return "Afgelopen";
		default:
			return null;
		}
	}
}
