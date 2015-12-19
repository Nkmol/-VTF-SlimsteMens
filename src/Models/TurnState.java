package Models;

public enum TurnState {
	Busy,
	Timeout,
	Pass,
	Correct,
	Wrong,
	Bonus;
	
	public static TurnState fromString(String value) {
		switch (value) {
		case "bezig":
			return Busy;
		case "timeout":
			return Timeout;
		case "pas":
			return Pass;
		case "goed":
			return Correct;
		case "fout":
			return Wrong;
		case "bonus":
			return Bonus;
		default:
			return null;
		}
	}
	
	public String getValue() {
		switch (this) {
		case Busy:
			return "bezig";
		case Timeout:
			return "timeout";
		case Pass:
			return "pas";
		case Correct:
			return "goed";
		case Wrong:
			return "fout";
		case Bonus:
			return "bonus";
		default:
			return null;
		}
	}
}
