package Utilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuListener;

public final class ComponentUtility {
	
	/**
	 * Adds an ActionListener to a field by string name through reflection <br />
	 * <b>Note: The fields you want to access should be public</b>
	 * @param to The object it should be assigned to
	 * @param fieldName [AbstractButton] Field it should be using
	 * @param l The ActionListener it should add to the field
	 * @return returns if it has successfully completed the method 
	 */
	public static boolean addActionListener(JComponent to, String fieldName, ActionListener l) {
		try {
			AbstractButton component = null;
			component = (AbstractButton) to.getClass().getDeclaredField(fieldName).get(to);
			if(component instanceof JMenu) {
				JMenu menuComp = (JMenu)component;
				menuComp.addMenuListener(new MenuListener() {
					@Override
					public void menuCanceled(MenuEvent arg0) { }
					@Override
					public void menuDeselected(MenuEvent arg0) { }
					@Override
					public void menuSelected(MenuEvent arg0) {
						// TODO Auto-generated method stub
						l.actionPerformed(new ActionEvent(to, 0, ""));
					} });
			}
			else {
				component.addActionListener(l);
			}
			return true;
		} catch (IllegalArgumentException|IllegalAccessException|NoSuchFieldException|SecurityException e) {
			e.printStackTrace();
			return false;
		}
	}
}

/*menuComp.addMenuKeyListener(new MenuKeyListener() {

	@Override
	public void menuKeyPressed(MenuKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuKeyReleased(MenuKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuKeyTyped(MenuKeyEvent arg0) {
		// TODO Auto-generated method stub
		l.actionPerformed(new ActionEvent(to, 0, ""));
	} });*/
