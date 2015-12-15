package View;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Models.Player;

public class RegisterPanel extends JPanel implements Observer {
	private final static String USERNAME 	 = "Username: ",
						 	    PASSWORD 	 = "Password: ",
						 	    BTN_REGISTR  = "Registrate",
						 	    BTN_LOGIN	 = "Login",
						 	    HEADER		 = "- Registreer -";
	
	public JTextField 	txtUsername,
					   	txtPassword;
	public JButton 		btnLogin,
						btnReg;
	private JLabel		lblError;
	
	
	public RegisterPanel() {
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
		JLabel lblHeader = new JLabel(RegisterPanel.HEADER);
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeader.setFont(new Font(Font.SANS_SERIF, 0, 28));
		add(lblHeader, c);
		
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.ipady = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 0, 10, 20);
		add(new JLabel(RegisterPanel.USERNAME), c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.weightx = 1;
		c.ipadx = 350;
		c.insets = new Insets(0, 0, 10, 0);
		txtUsername = new JTextField();
		add(txtUsername, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.ipadx = 0;
		c.insets = new Insets(0, 0, 0, 0);
		add(new JLabel(RegisterPanel.PASSWORD), c);
		
		c.gridx = 1;
		c.weightx = 1;
		txtPassword = new JPasswordField();
		add(txtPassword, c);
		
		c.gridy = 3;
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		add(lblError, c);
		
		c.gridy = 4;
		c.gridx = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new  Insets(10, 0, 0, 10);
		btnLogin = new JButton(RegisterPanel.BTN_LOGIN);
		add(btnLogin, c);
		
		c.gridx = 1;
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 0, 0, 0);
		btnReg = new JButton(RegisterPanel.BTN_REGISTR);
		add(btnReg, c);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Player model = (Player)arg;
		System.out.println(model.succesMsg);
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
