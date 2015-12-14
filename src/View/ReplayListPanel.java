package View;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ReplayListPanel extends JPanel {

	public ReplayListPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	
	
	public ReplayListPanel(ArrayList<ReplayListItem> list)
	{
		this();
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
			ReplayListItem item = (ReplayListItem)e.getSource();
			int Id = item.getGameId();
			// TODO: Start replay with Id
		}
	}
}
