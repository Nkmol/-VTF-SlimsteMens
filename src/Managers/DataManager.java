package Managers;

import java.sql.*;
import java.util.ArrayList;

import Models.*;

public class DataManager {

	private static final String dbUrl = "jdbc:mysql://localhost/slimsteMens";
	private static final String username = "root";
	private static final String password = "root";
	
	private static DataManager instance = null;
	private Connection connection;
	
	private DataManager() { 
		
	}
	
	public static DataManager getInstance() {
		if(instance == null) {
			instance = new DataManager();
		}
		return instance;
	}
	
	public ArrayList<Player> getAllPlayers() {
		Connection c = DataManager.getInstance().getConnection();
		ArrayList<Player> players = null;
		try {
			Statement statement = c.createStatement();
			ResultSet data = statement.executeQuery("SELECT * from account as a "
					+ "INNER JOIN accountrol AS ar ON a.naam = ar.account_naam");
			players = new ArrayList<>();
			while(data.next()) 
				players.add(new Player(data));
			
		} catch (SQLException e) {
		}
		
		return players;
	}
	
	public boolean registerUser(String name, String password, Role role) {
		Connection c = DataManager.getInstance().getConnection();
		try {
			Statement statement = c.createStatement();
			String sql = "insert into account (naam, wachtwoord) values ('"+name+"','"+password+"')";
			statement.executeUpdate(sql);
			sql = "insert into accountrol (account_naam, rol_type) values ('"+name+"','"+role.getValue()+"')";
			statement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			System.err.println("Error inserting a new player: " + e.getMessage());
			return false;
		}
	}
	
	public Connection getConnection() {
		try {
			System.out.println("Connecting.....");
			connection = DriverManager.getConnection(dbUrl, username, password);
			System.out.println("Connected");
		} catch (SQLException e) {
			System.err.println("Connection Error: " + e.getMessage());
		}
		
		return connection;
	}
	
}
