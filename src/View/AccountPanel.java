package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import Models.Player;

public class AccountPanel extends JPanel implements Observer{
	
	private JLabel lblName, lblError;
	public JPasswordField fieldOldPass, fieldNewPass, fieldNewPassRe;
	public JButton btnConfirm;
	
	public AccountPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 0, 0, 0)); //add padding
		lblName = new JLabel("Henk");
		lblName.setFont(new Font(Font.SANS_SERIF, 0, 18));
		add(lblName);
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		panel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Change Password"), new EmptyBorder(10, 10, 10, 10))); 
		
		panel.add(new JLabel("old password: "), c);
		c.gridx = 1;
		fieldOldPass = new JPasswordField();
		panel.add(fieldOldPass, c);
	
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 0, 0, 0);
		panel.add(new JLabel("new password: "), c);
		c.gridx = 1;
		fieldNewPass = new JPasswordField();
		panel.add(fieldNewPass, c);
		
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 2;
		panel.add(new JLabel("confirm password: "), c);
		c.gridx = 1;
		fieldNewPassRe = new JPasswordField();
		panel.add(fieldNewPassRe, c);
		
		c.gridy = 3;
		lblError = new JLabel(" ");
		lblError.setForeground(Color.RED);
		panel.add(lblError, c);
		
		c.gridy = 4;
		c.insets = new Insets(10, 0, 0, 0);
		btnConfirm = new JButton("Confirm");
		panel.add(btnConfirm, c);
		add(panel);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Player model = (Player)arg1;
		
		lblName.setText(model.getName());
		if(model.succesMsg == "") {
			lblError.setText(model.errorMsg);
			lblError.setForeground(Color.RED);
		}
		else {
			lblError.setText(model.succesMsg);
			lblError.setForeground(Color.GREEN);
		}
	}
}