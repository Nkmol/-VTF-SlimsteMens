package Managers;

import java.awt.List;
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
	
	public void test() {
		Connection c = DataManager.getInstance().getConnection();
		System.err.println("test");
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
