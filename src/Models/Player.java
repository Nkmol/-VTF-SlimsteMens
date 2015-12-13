package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

import Managers.DataManager;

public class Player extends Observable{
	
	private String name;
	private Role role;
	public String errorMsg = "", succesMsg = "";
	
	public Player(String name, Role role) {
		this.name = name;
		this.role = role;
	}
	
	public Player() {
		//TODO: Do i need name and Role when i start the login screen?
	}
	
	public Player(ResultSet data) {
		try {
			this.name = data.getString("naam");
			String role = data.getString("rol_type");
			this.role = (role.equals(Role.Player.getValue())) ? Role.Player : Role.Observer; 
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
	
	public boolean login(String name, String password) {
		boolean state = false;
		if(name.equals("") || password.equals("")) {
			errorMsg = "Vul alle velden in.";
		}
		else {
			Player player = DataManager.getInstance().getPlayer(name);
			if(player == null)
				errorMsg = "De combinatie van je naam en wachtwoord klopt niet";
			else {
				String pass = DataManager.getInstance().getPasswordForPlayer(name);
				if(pass.equals(password)) {
					errorMsg = "";
					state = true;
				}
				else
					errorMsg = "De combinatie van je naam en wachtwoord klopt niet";
			}
		}
		
		setChanged();
		notifyObservers(this);
		return state;
	}
	
	public void register(String name, String password) {
		if(name.equals("") || password.equals(""))
			errorMsg = "Vul alle velden in.";
		else 
			if(!DataManager.getInstance().registerUser(name, password, Role.Player))
				errorMsg = "Er is iets fout gegaan tijdens het registreren.";
			else {
				errorMsg = "";
				succesMsg = "U bent succesvol geregistreerd";
			}
		
		setChanged();
		notifyObservers(this);
	}
}