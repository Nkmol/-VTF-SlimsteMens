package Models;

import java.sql.ResultSet;

public class OpenDoor extends Round {

	public OpenDoor(Game game) {
		super(game);
	}
	
	public OpenDoor(ResultSet data, Game game) {
		super(data, game);
	}

}
