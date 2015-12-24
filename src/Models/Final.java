package Models;

import java.sql.ResultSet;

public class Final extends Round {

	public Final(Game game) {
		super(game, RoundType.Final);
	}
	
	public Final(ResultSet data, Game game) {
		super(data, game);
	}

	@Override
	public void onSubmit(String answer) {
		// TODO Auto-generated method stub
		
	}

}
