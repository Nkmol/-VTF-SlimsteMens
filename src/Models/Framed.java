package Models;

import java.sql.ResultSet;

public class Framed extends Round {

	public Framed(Game game) {
		super(game);	
	}
	
	public Framed(ResultSet data, Game game) {
		super(data,game);
	}

	@Override
	public void onSubmit(String answer) {
		// TODO Auto-generated method stub
		
	}

}
