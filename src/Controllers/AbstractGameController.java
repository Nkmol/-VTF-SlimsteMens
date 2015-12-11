package Controllers;

import javax.swing.JFrame;

import View.AbstractGameBottomView;
import View.AbstractGameTopView;
import View.AbstractGameView;

public class AbstractGameController {
	private JFrame main;
	
	public AbstractGameController(JFrame main) {	
		this.main = main;
		
		AbstractGameBottomView agbv = new AbstractGameBottomView();
		AbstractGameTopView agtv = new AbstractGameTopView();
		AbstractGameView agv = new AbstractGameView(agtv, agbv);
		
		main.add(agv);
		main.pack();
	}
	
}
