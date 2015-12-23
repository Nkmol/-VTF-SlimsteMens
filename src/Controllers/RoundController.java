package Controllers;

import javax.swing.JPanel;

import Models.Round;
import Models.ThreeSixNine;
import View.ThreeSixNineView;

public interface RoundController {
	
	public JPanel getView();
	public Round getModel();
}
