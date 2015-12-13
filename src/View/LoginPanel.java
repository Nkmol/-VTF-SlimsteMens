package View;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginPanel extends JPanel {
	private final static String USERNAME 	 = "Username: ",
						 	    PASSWORD 	 = "Password: ",
						 	    BTN_LOGIN    = "Login",
						 	    BTN_REGISTR  = "Registrate",
						 	    HEADER		 = "- Login -";
	
	private JTextField 	txtUsername,
					   	txtPassword;
	public JButton 		btnLogin,
						btnReg;
	
	
	public LoginPanel() {
		setLayout(new GridBagLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10)); //add padding
		createLoginPanel();
		
	}
	
	private void createLoginPanel() {
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.gridwidth = 2;
		c.ipady = 50;
		JLabel lblHeader = new JLabel(LoginPanel.HEADER);
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeader.setFont(new Font(Font.SANS_SERIF, 0, 28));
		add(lblHeader, c);
		
		c.gridwidth = 1;
		c.ipady = 0;
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 1;
		c.insets = new  Insets(0, 0, 10, 20);
		add(new JLabel(LoginPanel.USERNAME), c);
		
		c.gridx = 1;
		c.weightx = 1;
		c.insets = new  Insets(0, 0, 10, 0);
		c.ipadx = 350;
		txtUsername = new JTextField();
		add(txtUsername, c);
		
		c.ipadx = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.insets = new  Insets(0, 0, 0, 0);
		add(new JLabel(LoginPanel.PASSWORD), c);
		
		c.gridx = 1;
		c.weightx = 1;
		txtPassword = new JPasswordField();
		add(txtPassword, c);
		
		c.gridy = 3;
		JLabel error = new JLabel("Een voorbeeld error");
		error.setForeground(Color.RED);
		add(error, c);
		
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.insets = new  Insets(10, 0, 0, 0);
		btnLogin = new JButton(LoginPanel.BTN_LOGIN);
		add(btnLogin, c);
		
		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new  Insets(10, 0, 0, 10);
		btnReg = new JButton(LoginPanel.BTN_REGISTR);
		add(btnReg, c);
	}
}
