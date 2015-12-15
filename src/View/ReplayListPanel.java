package View;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Controllers.ReplayListController;
import Models.GameScore;
import Models.ReplayList;

@SuppressWarnings("serial")
public class ReplayListPanel extends JPanel {

	private ReplayListController controller;
	private Collection<ReplayListItem> itemList;
	
	private ReplayListPanel(ReplayListController controller)
	{
		this.controller = controller;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public ReplayListPanel(Collection<ReplayListItem> list, ReplayListController controller)
	{
		this(controller);
		addList(list);
	}
	
	public ReplayListPanel(ArrayList<GameScore> scores, ReplayListController controller) {
		this(GameScoreListToReplayItemList(scores), controller);
	}
	
	private void setItemList(Collection<ReplayListItem> list) {
		removeCurrentList();
		addList(list);
	}
	
	private void removeCurrentList() {
		for(ReplayListItem item : itemList)
		{
			remove(item);
		}
		itemList.clear();
	}
	
	private void addList(Collection<ReplayListItem> list) {
		Dimension PrefferedItemDimension = new Dimension(getWidth(), 200);
		for(ReplayListItem item : list)
		{
			item.addMouseListener(new ReplayMouseAdapter());
			item.setPreferredSize(PrefferedItemDimension);
			add(item);
			itemList.add(item);
		}
	}
	
	public void Observe(Observable observable) {
		observable.addObserver(this::update);
	}

	private void update(Observable observable, Object object) {
		setItemList(GameScoreListToReplayItemList(((ReplayList)observable).getScore()));
	}
	
	public static ArrayList<ReplayListItem> GameScoreListToReplayItemList(ArrayList<GameScore> scores) {
		ArrayList<ReplayListItem> items = new ArrayList<>();
		for (GameScore s : scores) {
			items.add(new ReplayListItem(s));
		}
		return items;
	}
	
	private class ReplayMouseAdapter extends MouseAdapter {
		
		public void mouseClicked(MouseEvent e){
			controller.StartReplay(((ReplayListItem)e.getSource()).getGameId());
		}
	}
}