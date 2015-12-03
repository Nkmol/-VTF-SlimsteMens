package View;
import javax.swing.*;

public class LoginPanel extends JPanel {
	
	public LoginPanel() {
		createLoginPanel();
	}
	
	private void createLoginPanel() {
		this.add(new JLabel("User"));
		this.add(new JTextField());
		this.add(new JLabel("Password"));
		this.add(new JPasswordField());
	}
}
