package View;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ReplayListPanel extends JPanel {

	public ReplayListPanel()
	{
		// TODO: get played games via databasemanager
		ArrayList<ReplayListItem> list = new ArrayList<ReplayListItem>();
		
		// add listitems with temporary testdata
		list.add(new ReplayListItem(1, "Vincent", false, 4, 2));
		list.add(new ReplayListItem(2, "Temmie", false, 5, 150));
		list.add(new ReplayListItem(3, "Temmie", false, 5, 150));
		
		for(JPanel item : list)
		{
			item.addMouseListener(new ReplayMouseAdapter());
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
