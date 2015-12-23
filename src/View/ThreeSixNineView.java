package View;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controllers.ThreeSixNineController;
import Models.Question;
import Models.ThreeSixNine;
import net.miginfocom.swing.MigLayout;

public class ThreeSixNineView extends JPanel implements Observer {

	public ThreeSixNineView(ThreeSixNine currentRound)
	{
		createRoundOnePanel();
	}
	
	private void createRoundOnePanel()
	{
		this.setLayout(new MigLayout());

	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		ThreeSixNine threesixnine = (ThreeSixNine)arg;
		
	}
}
