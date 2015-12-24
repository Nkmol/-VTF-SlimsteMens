package View;

import javax.swing.JPanel;

import Controllers.ReplayController;

@SuppressWarnings("serial")
public class ReplayPanel extends JPanel {

	ReplayController controller;
	public ReplayPanel(ReplayController controller) {
		this.controller = controller;
	}
}