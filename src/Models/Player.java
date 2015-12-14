package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Player {
	
	private String name;
	private String password;
	private Role role;
	
	public Player(String name, Role role) {
		this.name = name;
		this.role = role;
	}
	
	public Player(ResultSet data) {
		try {
			name = data.getString("naam");
			password = data.getString("wachtwoord");
			role = Role.fromString(data.getString("rol_type"));
		} catch (SQLException e) {
			System.err.println("Error initializing player: " + e.getMessage());
		}
	}
	
	public void changeRole(Role role) {
		this.role = role;
	}
	
	public String getName() {
		return name;
	}
	
	public Role getRole() {
		return role;
	}
	
	public String getPassword() {
		return password;
	}
	
}