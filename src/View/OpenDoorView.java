package View;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import Models.OpenDoor;
import net.miginfocom.swing.MigLayout;

public class OpenDoorView extends JPanel implements Observer{

	public OpenDoorView(OpenDoor currentRound) {
		createRoundOnePanel();
	}
	
	private void createRoundOnePanel() {
		this.setLayout(new MigLayout());

	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		OpenDoor openDoor = (OpenDoor)arg;
	}

}
