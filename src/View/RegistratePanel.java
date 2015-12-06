package View;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RegistratePanel extends JPanel {
	private final static String USERNAME 	 = "Username: ",
						 	    PASSWORD 	 = "Password: ",
						 	    BTN_REGISTR  = "Registrate",
						 	    HEADER		 = "- Registreer -";
	
	private JTextField 	txtUsername,
					   	txtPassword;
	private JButton 	btnLogin,
						btnReg;
	
	
	public RegistratePanel() {
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
		JLabel lblHeader = new JLabel(RegistratePanel.HEADER);
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeader.setFont(new Font(Font.SANS_SERIF, 0, 28));
		add(lblHeader, c);
		
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.ipady = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 0, 10, 20);
		add(new JLabel(RegistratePanel.USERNAME), c);
		
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
		add(new JLabel(RegistratePanel.PASSWORD), c);
		
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
		c.weightx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 0, 0, 0);
		btnReg = new JButton(RegistratePanel.BTN_REGISTR);
		add(btnReg, c);
	}
}