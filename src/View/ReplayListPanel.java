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
		
		// TODO: get played games via databasemanager
		ArrayList<ReplayListItem> list = new ArrayList<ReplayListItem>();
		
		// add listitems with temporary testdata
		list.add(new ReplayListItem(3, "Temmie", false, 5, 150));
		list.add(new ReplayListItem(3, "Marco", true, 5, 150));
		list.add(new ReplayListItem(3, "Potato", true, 5, 42));
		list.add(new ReplayListItem(3, "Aaron", true, 5, 150));
		list.add(new ReplayListItem(3, "Temmie", false, 5, 150));
		list.add(new ReplayListItem(3, "Temmie", false, 5, 150));
		list.add(new ReplayListItem(1, "Vincent", false, 4, 2));
		list.add(new ReplayListItem(2, "Temmie", false, 5, 150));
		list.add(new ReplayListItem(3, "Temmie", false, 99, 42));
		list.add(new ReplayListItem(3, "Temmie", false, 7, 150));
		list.add(new ReplayListItem(3, "Temmie", false, 5, 150));
		
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
