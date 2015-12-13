package Models;

import java.sql.ResultSet;

public class Final extends Round {

	public Final(Game game) {
		super(game);
	}
	
	public Final(ResultSet data, Game game) {
		super(data, game);
	}

}
