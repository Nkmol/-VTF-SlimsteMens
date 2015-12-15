package Utilities;

import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JComponent;

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
		AbstractButton component = null;
		try {
			component = (AbstractButton) to.getClass().getDeclaredField(fieldName).get(to);
			component.addActionListener(l);
			return true;
		} catch (IllegalArgumentException|IllegalAccessException|NoSuchFieldException|SecurityException e) {
			e.printStackTrace();
			return false;
		}
	}
}
