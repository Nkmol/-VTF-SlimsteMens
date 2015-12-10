import Managers.DataManager;
import Models.Game;

public class Main {

	public static void main(String[] args) {
		Game game = DataManager.getInstance().getGame(511);
		if (game != null) {
			System.out.println(game.getGameId());
			System.out.println(game.getPlayer1().getName());
			System.out.println(game.getPlayer2().getName());
			System.out.println(game.getGameState().getValue());
		}
	}

}
