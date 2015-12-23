package Models;

import java.sql.ResultSet;

public class Puzzle extends Round {

	public Puzzle(Game game) {
		super(game);	
	}
	
	public Puzzle(ResultSet data, Game game) {
		super(data, game);
	}

	@Override
	public void onSubmit() {
		// TODO Auto-generated method stub
		
	}

}
