package View;

import java.awt.Color;
import java.util.Observable;

import Controllers.ReplayController;
import Models.Replay;
import Models.TurnState;

@SuppressWarnings("serial")
public class ReplayPanel extends GamePanel {

	ReplayController controller;
	public ReplayPanel(ReplayController controller) {
		super();
		this.controller = controller;
		txtInput.setEnabled(false);
	}
	
	@Override 
	public void update(Observable arg0, Object arg1){
		super.update(arg0, arg1);
		Replay replay = (Replay)arg1;
		lblPlayer1.setText(""+replay.GetSecondsEarnedPlayer1());
		lblPlayer2.setText(""+replay.GetSecondsEarnedPlayer2());
		
		if (replay.getCurrentRound().getCurrentTurn().getTurnState() == TurnState.Pass) {
			btnPass.setEnabled(true);
			btnSubmit.setEnabled(false);
			txtInput.setBackground(Color.RED);
			txtInput.setText("PAS");
		} else if (replay.getCurrentRound().getCurrentTurn().getTurnState() == TurnState.Timeout) {
			btnPass.setEnabled(true);
			btnSubmit.setEnabled(false);
			txtInput.setBackground(Color.RED);
			txtInput.setText("Time-out");
		} else {
			btnPass.setEnabled(false);
			btnSubmit.setEnabled(true);
			txtInput.setBackground(null);
			txtInput.setText(replay.getCurrentAnswer());
		}
		
		if (replay.getCurrentRound().getCurrentTurn().getPlayer().getName().equals(lblPlayer1.getText())) {
			lblPlayer1.setForeground(Color.RED);
			lblPlayer2.setForeground(null);
		} else {
			lblPlayer2.setForeground(Color.RED);
			lblPlayer1.setForeground(null);
		}
	}
}