package View;

import java.awt.Color;
import java.util.Observable;

import javax.swing.JPanel;

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
		System.out.println(replay.getCurrentRound().getCurrentTurn().getTurnState().getValue());
		if (replay.getCurrentRound().getCurrentTurn().getTurnState() == TurnState.Pass) {
			txtInput.setBackground(Color.RED);
			txtInput.setText("PAS");
		} else if (replay.getCurrentRound().getCurrentTurn().getTurnState() == TurnState.Timeout) {
			txtInput.setBackground(Color.RED);
			txtInput.setText("Time-out");
		} else {
			txtInput.setBackground(null);
			txtInput.setText(replay.getCurrentAnswer());
		}
		
		btnPass.setText(">");
		btnSubmit.setText("<");
		
		lblPlayer1.setText(""+replay.GetSecondsEarnedPlayer1());
		lblPlayer2.setText(""+replay.GetSecondsEarnedPlayer2());
		if (replay.getCurrentRound().getCurrentTurn().getPlayer().getName().equals(lblPlayer1.getText())) {
			lblPlayer1.setForeground(Color.RED);
			lblPlayer2.setForeground(null);
		} else {
			lblPlayer2.setForeground(Color.RED);
			lblPlayer1.setForeground(null);
		}
	}
}