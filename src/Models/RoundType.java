package Models;

public enum RoundType {
	
	ThreeSixNine,
	OpenDoor,
	Puzzle,
	Framed,
	Final;
	
	public static RoundType fromString(String value) {
		switch (value) {
		case "369":
			return ThreeSixNine;
		case "opendeur":
			return OpenDoor;
		case "puzzel":
			return Puzzle;
		case "ingelijst":
			return Framed;
		case "finale":
			return Final;
		default:
			return null;
		}
	}
	
	public String getValue() {
		switch (this) {
		case ThreeSixNine:
			return "369";
		case OpenDoor:
			return "opendeur";
		case Puzzle:
			return "puzzel";
		case Framed:
			return "ingelijst";
		case Final:
			return "finale";
		default:
			return null;
		}
	}
	
	public int getIndex() {
		switch (this) {
		case ThreeSixNine:
			return 1;
		case OpenDoor:
			return 2;
		case Puzzle:
			return 3;
		case Framed:
			return 4;
		case Final:
			return 5;
		default:
			return 0;
		}
	}
	
}
