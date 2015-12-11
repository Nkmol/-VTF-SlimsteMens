package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AbstractGameBottomView extends JPanel {
	
	private JTextField inputField;
	private JButton submitButton;

	public AbstractGameBottomView() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);

		setPreferredSize(new Dimension(450,50));

		inputField = new JTextField();
		submitButton = new JButton();

		submitButton.addActionListener(new ActionListener() { //TODO LAMBDA EXPRESSION

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				inputField.setText("");
			}
		});


		inputField.setFont(new Font("Serif", Font.ITALIC, 15));
		submitButton.setFont(new Font("Serif", Font.ITALIC, 12));
		
		GridBagConstraints gridBagConstraintsLabel = new GridBagConstraints();
		gridBagConstraintsLabel.gridx = 0;
		gridBagConstraintsLabel.weightx = 19;
		gridBagConstraintsLabel.gridy = 0;
		gridBagConstraintsLabel.insets = new Insets(5,5,5,5);
		gridBagConstraintsLabel.fill = GridBagConstraints.BOTH;

		add(inputField, gridBagConstraintsLabel);

		submitButton.setText("Submit");

		GridBagConstraints gridBagConstraintsButton = new GridBagConstraints();
		gridBagConstraintsButton.gridx = 1;
		gridBagConstraintsButton.gridwidth = 50;
		gridBagConstraintsButton.weightx = 1;
		gridBagConstraintsButton.gridy = 0;
		gridBagConstraintsButton.insets = new Insets(5,5,5,5);
		gridBagConstraintsButton.fill = GridBagConstraints.HORIZONTAL;

		add(submitButton, gridBagConstraintsButton);

	}
	
	@Override
	  protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	    
	    Image img = null;
	    
		try {
			img = ImageIO.read(new File("Assets/GameBottom.png"));
		} catch (IOException ex) {
		}
		 
	     g.drawImage(img, 0, 0, null);
	}
	
}
