package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Controllers.ThreeSixNineController;
import Models.Question;
import Models.ThreeSixNine;
import Models.Turn;
import net.miginfocom.swing.MigLayout;

public class ThreeSixNineView extends JPanel implements Observer {

	JTextArea questionTextArea;
	
	public ThreeSixNineView(ThreeSixNine currentRound) {
		createRoundOnePanel();
	}
	
	private void createRoundOnePanel() {
		setLayout(new BorderLayout());
		questionTextArea = new JTextArea();
		textAreaProperties(questionTextArea);
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add(questionTextArea, BorderLayout.CENTER);
		add(container, BorderLayout.NORTH);
	}
	
	private JTextArea textAreaProperties(JTextArea textArea) {
	    textArea.setEditable(false);  
	    textArea.setCursor(null);  
	    textArea.setOpaque(false);  
	    textArea.setFocusable(false);
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    return textArea;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		ThreeSixNine threesixnine = (ThreeSixNine)arg;
		Turn currentTurn = threesixnine.getCurrentTurn();

		if (currentTurn.getSkippedQuestion() != null) 
			questionTextArea.setText(currentTurn.getSkippedQuestion().getText());
		else {
			questionTextArea.setText(currentTurn.getCurrentQuestion().getText());
		}
		
	}
}
