package Controllers;

import java.util.ArrayList;

import Managers.DataManager;
import Models.Game;
import Models.GameScore;
import View.ReplayListPanel;
import View.ReplayListItem;

public class ReplayListController {

	private MainController parent;
	private ReplayListPanel view;

	public ReplayListController(MainController parent) {
		this.parent = parent;
		ArrayList<Game> games = DataManager.getInstance().getAllGamesForPlayer(DataManager.getInstance().getCurrentUser().getName());
		ArrayList<GameScore> scores = new ArrayList<>();
		for (Game g : games) {
			scores.add(DataManager.getInstance().getGameScore(g.getId()));
		}
		view = new ReplayListPanel(GameScoreListToReplayItemList(scores), this);
	}
	
	public void StartReplay(int Id) {
		new ReplayController(Id, parent);
	}
	
	private ArrayList<ReplayListItem> GameScoreListToReplayItemList(ArrayList<GameScore> scores) {
		ArrayList<ReplayListItem> items = new ArrayList<>();
		for (GameScore s : scores) {
			items.add(new ReplayListItem(s));
		}
		return items;
	} 
}