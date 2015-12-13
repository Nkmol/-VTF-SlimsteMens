import Managers.DataManager;
import Models.Answer;
import Models.Game;
import Models.Question;
import Models.Round;

public class Main {

	public static void main(String[] args) {
		Game game = DataManager.getInstance().getGame(511);
		if (game != null) {
			System.out.println("====== Game ======");
			System.out.println(game.getId());
			System.out.println(game.getPlayer1().getName());
			System.out.println(game.getPlayer2().getName());
			System.out.println(game.getGameState().getValue());
			System.out.println("\\:--Rounds--://");
			for (Round r : game.getRounds()) {
				System.out.println(r.getRoundType().getValue());
				for (Question q : r.getQuestions()) {
					System.out.println("");
					System.out.println("Question: " + q.getText());
					System.out.println("Answers:");
					for (Answer a : q.getAnswers()) {
						System.out.println("Answer: " + a.getAnswer());
						System.out.println("Alternatives: ");
						for (String alt : a.getAlternatives()) {
							System.out.println("Alternative: " + alt);
						}
					}
				}
			}
			System.out.println("");
		}
	}

}
