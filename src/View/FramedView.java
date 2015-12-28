package View;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Models.Framed;
import Models.Turn;

public class FramedView extends JPanel implements Observer{

	JPanel questionView;
	JPanel answersView;
	JPanel container;
	
	JTextArea questionTextArea;
	
	public FramedView(Framed currentRound) {
		createRoundFourPanel();
	}
	
	private void createRoundFourPanel() {
		setLayout(new BorderLayout());
		
		container = new JPanel();
		container.setLayout(new BorderLayout());
		
		add(container, BorderLayout.CENTER);
		
		JPanel questionView = new JPanel();
		questionView.setLayout(new BorderLayout());
		
		JPanel answersView = new JPanel();
		answersView.setLayout(new GridBagLayout());
		
		container.add(questionView, BorderLayout.NORTH);
		container.add(answersView, BorderLayout.CENTER);
		
		questionTextArea = new JTextArea();
		textAreaProperties(questionTextArea);
		questionTextArea.setMargin(new Insets(5,5,5,5));
		
		questionView.add(questionTextArea, BorderLayout.CENTER);
		
		for(int x = 0; x < 4; x+=2) {
			for(int y = 0; y < 5; y++) {
				
				JLabel points  = new JLabel(x + " 10");
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 0.5;
				c.gridx = x;
				c.gridy = y;
				answersView.add(points, c);
				
				JLabel answer  = new JLabel(y + " answer");
				c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 2;
				c.gridx = x + 1;
				c.gridy = y;
				answersView.add(answer, c);
			}
		}
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
		Framed framed = (Framed)arg;
		Turn currentTurn = framed.getCurrentTurn();
		System.out.println("question: " + framed.getCurrentTurn().getCurrentQuestion());
		if (currentTurn.getSkippedQuestion() != null) 
			questionTextArea.setText(currentTurn.getSkippedQuestion().getText());
		else if (framed.getCurrentTurn().getCurrentQuestion() != null) 
			questionTextArea.setText(currentTurn.getCurrentQuestion().getText());
		
	}
}
