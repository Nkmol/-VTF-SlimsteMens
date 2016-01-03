package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Models.Answer;

public class FramedAnswerView extends JPanel{
	private JPanel pointsPanel;
	private JPanel answerPanel;
	
	private JLabel pointsLabel;
	private JLabel answerLabel;
	
	private String correctPoints;
	private Answer correctAnswer;
	
	public FramedAnswerView(String pointsText, String answerText) {
		
		pointsPanel = new JPanel();
		pointsPanel.setBackground(new Color(135,171,255));
		pointsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		pointsLabel  = new JLabel(pointsText);
		pointsLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		pointsPanel.add(pointsLabel);		
				
		answerPanel = new JPanel();
		answerPanel.setBackground(new Color(193,212,255));
		answerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		answerLabel  = new JLabel(answerText);
		answerLabel.setFont(new Font("Serif", Font.ITALIC, 15));
		answerPanel.add(answerLabel);
				
		setLayout(new BorderLayout());
		add(pointsPanel, BorderLayout.WEST);
		add(answerPanel, BorderLayout.CENTER);
	}
	
	public void setPoints(String pointsText) {
		correctPoints = pointsText;
	}
	
	public void setAnswer(Answer answer) {
		correctAnswer = answer;
	}
	
	public Answer getAnswer() {
		return correctAnswer;
	}
	
	public void revealAnswer() {
		pointsLabel.setText(correctPoints);
		answerLabel.setText(correctAnswer.getAnswer());
	}
}
