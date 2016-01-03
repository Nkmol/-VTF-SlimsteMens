package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Models.Answer;

public class PuzzleQuestionView extends JPanel{
	private JPanel questionPanel;
	private JLabel questionLabel;
	private Answer question;
	
	public PuzzleQuestionView(String questionText) {
				
		questionPanel = new JPanel();
		questionPanel.setBackground(new Color(193,212,255));
		questionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		questionLabel  = new JLabel(questionText);
		questionLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		questionPanel.add(questionLabel);
				
		setLayout(new BorderLayout());
		add(questionPanel, BorderLayout.CENTER);
	}
	
	public void setQuestion(Answer question) {
		this.question = question;
		questionLabel.setText(question.getAnswer());
	}
	
	public Answer getQuestion() {
		return question;
	}

	public void revealQuestion(Color color) {
		setForeground(color);
	}
}