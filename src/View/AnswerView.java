package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Models.Answer;

public class AnswerView extends JPanel {

	private Answer answer;
	private JLabel answerLabel;
	
	public AnswerView(Answer answer) {
		this.answer = answer;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		
		setPreferredSize(new Dimension(250, 20));
		setBounds(0, 0, 250, 20);
		
		answerLabel = new JLabel(this.answer.getAnswer());
		answerLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		add(answerLabel, gridBagConstraints);
		
	}
	
	public void hideAnswer() {
		int length = answer.getAnswer().length();
		String hiddenAnswer = "";
		for (int i = 0; i < length; i++) 
			hiddenAnswer += "-";
		answerLabel.setText(hiddenAnswer);
	}
	
	public void unhideAnswer() {
		answerLabel.setText(answer.getAnswer());
	}
	
	public Answer getAnswer() {
		return answer;
	}
	
}
