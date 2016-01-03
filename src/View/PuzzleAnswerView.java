package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Models.Answer;
import Models.Question;

public class PuzzleAnswerView extends JPanel{
	private String defaultPointsText,
				   defaultAnswerText;
	
	private JPanel pointsPanel;
	private JPanel answerPanel;
	
	private JLabel pointsLabel;
	private JLabel answerLabel;
	
	private String correctPoints;
	private Question correctAnswer;
	
	private PuzzleQuestionView[] questionViews;
	
	private Color color;
	
	public PuzzleAnswerView(String pointsText, String answerText) {
		defaultAnswerText = answerText;
		defaultPointsText = pointsText;
		
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
	
	public void setAnswer(Question answer) {
		correctAnswer = answer;
	}
	
	public void setQuestionViews(PuzzleQuestionView[] questionViews) {
		this.questionViews = questionViews;
	}
	
	public void fillQuestionViews(ArrayList<Answer> questions, Color color) {
		for(int i = 0; i < questions.size(); i++) {
			questionViews[i].setQuestion(questions.get(i));
		}
		
		this.color = color;
	}
	
	public void showQuestions() {
		for(int i = 0; i < questionViews.length; i++) {
			questionViews[i].revealQuestion(color);
		}
	}
	
	public Question getAnswer() {
		return correctAnswer;
	}
	
	public void revealAnswer() {
		pointsLabel.setText(correctPoints);
		answerLabel.setText(correctAnswer.getText());
	}
	
	public void hideAnswer() {
		pointsLabel.setText(defaultPointsText);
		answerLabel.setText(defaultAnswerText);
	}
}
