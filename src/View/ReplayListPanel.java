package View;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Controllers.ReplayListController;

@SuppressWarnings("serial")
public class ReplayListPanel extends JPanel {

	ReplayListController controller;
	private ReplayListPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public ReplayListPanel(ArrayList<ReplayListItem> list, ReplayListController controller)
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
	
	private class ReplayMouseAdapter extends MouseAdapter {
		
		public void mouseClicked(MouseEvent e){
			controller.StartReplay(((ReplayListItem)e.getSource()).getGameId());
		}
	}
}