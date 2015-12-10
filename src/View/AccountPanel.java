package View;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class AccountPanel extends JPanel{
	
	public AccountPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 0, 0, 0)); //add padding
		JLabel name = new JLabel("Henk");
		name.setFont(new Font(Font.SANS_SERIF, 0, 18));
		add(name);
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		panel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Change Password"), new EmptyBorder(10, 10, 10, 10))); 
		
		panel.add(new JLabel("old password: "), c);
		c.gridx = 1;
		panel.add(new JPasswordField(), c);
	
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 0, 0, 0);
		panel.add(new JLabel("new password: "), c);
		c.gridx = 1;
		panel.add(new JPasswordField(), c);
		
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 2;
		panel.add(new JLabel("confirm password: "), c);
		c.gridx = 1;
		panel.add(new JPasswordField(), c);
		
		c.insets = new Insets(10, 0, 0, 0);
		c.gridy = 3;
		panel.add(new JButton("Confirm"), c);
		add(panel);
	}
}