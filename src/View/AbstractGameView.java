package View;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class AbstractGameView extends JPanel {

	public AbstractGameView(AbstractGameTopView gameTopView, AbstractGameBottomView gameBottomView) {
		//GridBagLayout gridBagLayout = new GridBagLayout();
		//setLayout(gridBagLayout);

		setPreferredSize(new Dimension(450,500));
		/*
		GridBagConstraints gridBagConstraintsTop = new GridBagConstraints();
		gridBagConstraintsTop.gridx = 0;
		gridBagConstraintsTop.gridy = 1;
		gridBagConstraintsTop.fill = GridBagConstraints.HORIZONTAL;

		add(gameTopView, gridBagConstraintsTop);
		
		JPanel gameMidView = new JPanel();
		gameMidView.setPreferredSize(new Dimension(450, 350));
		
		GridBagConstraints gridBagConstraintsMid = new GridBagConstraints();
		gridBagConstraintsMid.gridx = 0;
		gridBagConstraintsMid.gridy = 1;
		gridBagConstraintsMid.fill = GridBagConstraints.HORIZONTAL;

		add(gameMidView, gridBagConstraintsMid);
		
		GridBagConstraints gridBagConstraintsBottom = new GridBagConstraints();
		gridBagConstraintsBottom.gridx = 0;
		gridBagConstraintsBottom.gridy = 1;
		gridBagConstraintsBottom.fill = GridBagConstraints.HORIZONTAL;

		add(gameBottomView, gridBagConstraintsBottom);
		
		*/

		JPanel gameMidView = new JPanel();
		gameMidView.setPreferredSize(new Dimension(450, 350));
		
		add(gameTopView);
		add(gameMidView);
		add(gameBottomView);

		
	}
	
}
