package Models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.regex.Pattern;

import Managers.DataManager;

public class Player extends Observable{
	
	private String name;
	private String password;
	private Role role;
	public String errorMsg = "", succesMsg = "";
	public static final String path = "playerName.txt";
	
	public Player(String name, Role role) {
		this.name = name;
		this.role = role;
	}
	
	public Player() {
		//TODO: Do i need name and Role when i start the login screen?
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
	
	public boolean login(String name, String password) {
		boolean state = false;
		if(name.equals("") || password.equals("")) {
			errorMsg = "Vul alle velden in.";
		}
		else if(DataManager.getInstance().signIn(name, password)){
			errorMsg = "";
			state = true;
			this.name = name;
			this.password = password;
		}
		else {
			errorMsg = "Het wachtwoord en/of username bestaat niet.";
		}
		
		setChanged();
		notifyObservers(this);
		return state;
	}
	
	private String regexPattern = "^[a-zA-Z]{3,}[0-9]";
	public void register(String name, String password) {
		succesMsg = "";
		if(name.equals("") || password.equals(""))
			errorMsg = "Vul alle velden in.";
		else if(name.matches(regexPattern) && password.matches(regexPattern)) {
			errorMsg = "Uw accountgegevens moeten minimaal uit 3 letters bestaan en mag geen vreemde tekens bevatten.";
		}
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
	
	public void changePassword(String oldPass, String newPass, String newPassRe) {
		succesMsg = "";
		if(oldPass.equals(password)){
			errorMsg = "Je oude wachtwoord klopt niet";
		}
		else if(!newPass.equals(newPassRe))
			errorMsg = "Het nieuwe wachtwoord moet 2x hetzelfde zijn";
		else {
			if(DataManager.getInstance().changeUserPassword(name, newPass)) {
				errorMsg = "";
				succesMsg = "Password succesvol veranderd";
			}
			else 
				errorMsg = "Er is iets verkeerd gegaan tijdens het wachtwoord veranderen";
		}
		
		setChanged();
		notifyObservers(this);
	}
	
	public void updateView() {
		setChanged();
		notifyObservers(this);
	}
	
	public String getPassword() {
		return password;
	}

	public void saveName() {
		try {
			PrintWriter out = new PrintWriter("playerName.txt");
			out.println(name);
			out.close();
		}
		catch (FileNotFoundException e) {
			
		}
	}
	
	public void loadName() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("playerName.txt"));
			System.out.println(br.readLine());
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}