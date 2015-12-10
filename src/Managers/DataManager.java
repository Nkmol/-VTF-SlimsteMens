package Managers;

import java.sql.*;
import java.util.ArrayList;

import Model.*;

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
			
		} catch (SQLException e) { }
		return players;
	}
	
	public boolean registerUser(String name, String password, Role role) {
		Connection c = DataManager.getInstance().getConnection();
		try {
			String sql = "insert into account (naam, wachtwoord) values (?,?)";
			PreparedStatement preparedStatement = c.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			preparedStatement.executeUpdate();
			
			sql = "insert into accountrol (account_naam, rol_type) values (?,?)";
			preparedStatement = c.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, role.getValue());
			preparedStatement.executeUpdate();
			
			c.commit();
			return true;
		} catch (SQLException e) {
			System.err.println("Error inserting a new player: " + e.getMessage());
			try { c.rollback(); } 
			catch (SQLException e1) { System.err.println("Error rolling back " + e1.getMessage()); }
			return false;
		}
	}
	
	public boolean registerChatMessage(int gameId, Timestamp timestamp, int millisec, String senderName, String message) {
		Connection c = DataManager.getInstance().getConnection();
		try {
			String sql = "insert into chatregel (spel_id, tijdstip, millisec, account_naam_zender, bericht)"
					+ " values (?,?,?,?,?)";
			PreparedStatement preparedStatement = c.prepareStatement(sql);
			preparedStatement.setInt(1, gameId);
			preparedStatement.setTimestamp(2, timestamp);
			preparedStatement.setInt(3, millisec);
			preparedStatement.setString(4, senderName);
			preparedStatement.setString(5, message);
			preparedStatement.executeUpdate();
			c.commit();
			return true;
		} catch (SQLException e) {
			System.err.println("Error inserting a new chat message: " + e.getMessage());
			try { c.rollback(); } 
			catch (SQLException e1) { System.err.println("Error rolling back " + e1.getMessage()); }
			return false;
		}
	}
	
	public ArrayList<ChatMessage> getAllChatMessages() {
		Connection c = DataManager.getInstance().getConnection();
		ArrayList<ChatMessage> chatMessages = null;
		try {
			Statement statement = c.createStatement();
			ResultSet data = statement.executeQuery("SELECT * from chatMessages");
			chatMessages = new ArrayList<>();
			while(data.next()) 
				chatMessages.add(new ChatMessage(data));
			
		} catch (SQLException e) { }
		return chatMessages;
	}
	
	public Connection getConnection() {
		try {
			System.out.println("Connecting.....");
			connection = DriverManager.getConnection(dbUrl, username, password);
			connection.setAutoCommit(false);
			System.out.println("Connected");
		} catch (SQLException e) {
			System.err.println("Connection Error: " + e.getMessage());
		}
		
		return connection;
	}
	
}