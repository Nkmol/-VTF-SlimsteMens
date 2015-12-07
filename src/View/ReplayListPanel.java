package View;

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
		// with a GridLayout of 1 by 1 the element will be filling the panel
		setLayout(new GridLayout(1,1));
		
		JScrollPane scrollPane = new JScrollPane();;
		scrollPane.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// TODO: get played games via databasemanager
		ArrayList<ReplayListItem> list = new ArrayList<ReplayListItem>();
		
		// add listitems with temporary testdata
		list.add(new ReplayListItem(1, "Vincent", false, 4, 2));
		list.add(new ReplayListItem(2, "Temmie", false, 5, 150));
		list.add(new ReplayListItem(3, "Temmie", false, 5, 150));
		
		for(ReplayListItem item : list)
		{
			item.addMouseListener(new ReplayMouseAdapter());
			scrollPane.add(item);
		}
		
		add(scrollPane);
	}
	
	private class ReplayMouseAdapter extends MouseAdapter {
		
		public void mouseClicked(MouseEvent e){
			ReplayListItem item = (ReplayListItem)e.getSource();
			int Id = item.getGameId();
			// TODO: Start replay with Id
		}
	}
}
