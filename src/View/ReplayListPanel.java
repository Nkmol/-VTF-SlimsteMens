package View;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Controllers.ReplayListController;
import Models.GameScore;
import Models.ReplayList;

@SuppressWarnings("serial")
public class ReplayListPanel extends JPanel {

	ReplayListController controller;
	private ReplayListPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public ReplayListPanel(Collection<ReplayListItem> list, ReplayListController controller)
	{
		this();
		this.controller = controller;
		Dimension PrefferedItemDimension = new Dimension(getWidth(), 200);
		for(ReplayListItem item : list)
		{
			item.addMouseListener(new ReplayMouseAdapter());
			item.setPreferredSize(PrefferedItemDimension);
			add(item);
		}
	}
	
	public ReplayListPanel(ArrayList<GameScore> scores, ReplayListController controller) {
		this(ReplayList.GameScoreListToReplayItemList(scores), controller);
	}
	
	private class ReplayMouseAdapter extends MouseAdapter {
		
		public void mouseClicked(MouseEvent e){
			controller.StartReplay(((ReplayListItem)e.getSource()).getGameId());
		}
	}
}