package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Player {
	
	private String name;
	private Role role;
	
	public Player(String name, Role role) {
		this.name = name;
		this.role = role;
	}
	
	public Player(ResultSet data) {
		try {
			this.name = data.getString("naam");
			String role = data.getString("rol_type");
			this.role = (role.equals("player")) ? Role.Player : Role.Observer; 
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
	
}
