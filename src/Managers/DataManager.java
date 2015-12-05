package Managers;

import java.sql.*;

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
