package Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import Managers.DataManager;
import View.ReplayListItem;

public class ReplayList extends Observable {
	
	private ArrayList<GameScore> score;
	
	public ReplayList() {
		UpdateScoreList(false);
	}
	
	public ArrayList<GameScore> getScore() {
		return score;
	}

	public void UpdateScoreList() {
		UpdateScoreList(true);
	}
	
	private void UpdateScoreList(boolean Notify) {
		String PlayerName = DataManager.getInstance().getCurrentUser().getName();
		score = DataManager.getInstance().getGameScoresForPlayer(PlayerName);
		if (Notify) {
			setChanged();
			notifyObservers();
		}
	}
	
	public static ArrayList<ReplayListItem> GameScoreListToReplayItemList(Collection<GameScore> scores) {
		ArrayList<ReplayListItem> items = new ArrayList<>();
		for (GameScore s : scores) {
			if (s.getGameState() == GameState.Finished)
				items.add(new ReplayListItem(s));
		}
		return items;
	}
}
