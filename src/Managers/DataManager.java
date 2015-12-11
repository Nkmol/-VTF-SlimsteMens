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
		connection = getConnection();
	}
	
	public static DataManager getInstance() {
		if(instance == null) {
			instance = new DataManager();
		}
		return instance;
	}
	
	public ArrayList<Player> getAllPlayers() {
		ArrayList<Player> players = null;
		try {
			Statement statement = connection.createStatement();
			ResultSet data = statement.executeQuery("SELECT * from account as a "
					+ "INNER JOIN accountrol AS ar ON a.naam = ar.account_naam");
			players = new ArrayList<>();
			while(data.next()) 
				players.add(new Player(data));
			
		} catch (SQLException e) { }
		return players;
	}
	
	public Game getGame(int gameId) {
		Game game = null;
		String sql = "SELECT * FROM spel WHERE spel_id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, gameId);
			ResultSet data = preparedStatement.executeQuery();
			if (data.next()) {
				Player player1 = DataManager.getInstance().getPlayer(data.getString("speler1"));
				Player player2 = DataManager.getInstance().getPlayer(data.getString("speler2"));
				GameState gameState = GameState.fromString(data.getString("toestand_type"));
				game = new Game(gameId, player1, player2, gameState);
			}
			
		} catch (SQLException e) {
			System.err.println("Error fetching game with id: " + gameId);
			System.err.println(e.getMessage());
		}
		return game;
	}
	
	public Round getRound(RoundType roundType, int gameId) {
		Round round = null;
		try{
			String sql = ""; //TODO: get round
			PreparedStatement preparedStatement = connection.prepareStatement("");
		} catch (SQLException e) { }
		return round;
	}
	
	public Player getPlayer(String playerName) {
		Player player = null;
		try {
			String sql = "SELECT * from account as a "
					+ "INNER JOIN accountrol AS ar ON a.naam = ar.account_naam "
					+ "WHERE a.naam = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, playerName);
			ResultSet data = preparedStatement.executeQuery();
			if(data.next()) 
				player = new Player(data);
		} catch (SQLException e) {
			System.err.println("Error fetching player with id: " + playerName);
			System.err.println(e.getMessage());
		}
		return player;
	}
	
	public boolean registerUser(String name, String password, Role role) {
		try {
			String sql = "insert into account (naam, wachtwoord) values (?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			preparedStatement.executeUpdate();
			
			sql = "insert into accountrol (account_naam, rol_type) values (?,?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, role.getValue());
			preparedStatement.executeUpdate();
			
			connection.commit();
			return true;
		} catch (SQLException e) {
			System.err.println("Error inserting a new player: " + e.getMessage());
			try { connection.rollback(); } 
			catch (SQLException e1) { System.err.println("Error rolling back " + e1.getMessage()); }
			return false;
		}
	}
	
	public boolean pushChatMessage(int gameId, Timestamp timestamp, int millisec, String senderName, String message) {
		try {
			String sql = "insert into chatregel (spel_id, tijdstip, millisec, account_naam_zender, bericht)"
					+ " values (?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, gameId);
			preparedStatement.setTimestamp(2, timestamp);
			preparedStatement.setInt(3, millisec);
			preparedStatement.setString(4, senderName);
			preparedStatement.setString(5, message);
			preparedStatement.executeUpdate();
			connection.commit();
			return true;
		} catch (SQLException e) {
			System.err.println("Error inserting a new chat message: " + e.getMessage());
			try { connection.rollback(); } 
			catch (SQLException e1) { System.err.println("Error rolling back " + e1.getMessage()); }
			return false;
		}
	}
	
	public ArrayList<ChatMessage> getChatMessages(int gameId) {
		ArrayList<ChatMessage> chatMessages = null;
		try {
			String sql = "SELECT * FROM chatMessages WHERE spel_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, gameId);
			ResultSet data = preparedStatement.executeQuery();
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