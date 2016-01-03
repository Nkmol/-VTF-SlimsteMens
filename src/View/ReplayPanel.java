package View;

import java.util.Observable;

import Controllers.ReplayController;
import Models.Replay;

@SuppressWarnings("serial")
public class ReplayPanel extends GamePanel {

	ReplayController controller;
	public ReplayPanel(ReplayController controller) {
		super();
		this.controller = controller;
	}
	
	@Override 
	public void update(Observable arg0, Object arg1){
		super.update(arg0, arg1);
		Replay replay = (Replay)arg1;
		txtInput.setText(replay.getCurrentAnswer());
	}
	
}