package Managers;

import java.sql.*;
import java.util.ArrayList;
import Models.*;

public class DataManager {

	private static final String dbUrl = "jdbc:mysql://localhost/slimsteMens";
	private static final String username = "root";
	private static final String password = "root";
//	private static final String dbUrl = "jdbc:mysql://databases.aii.avans.nl/spmol_db2";
//	private static final String username = "spmol";
//	private static final String password = "Ab12345";
	
	private static DataManager instance = null;
	private Connection connection;
	
	private DataManager() { 
		connection = getConnection();
	}
	
	public static DataManager getInstance() {
		if(instance == null) 
			instance = new DataManager();
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
	
	public ArrayList<Game> getAllGamesForPlayer(String name) {
		ArrayList<Game> games = null;
		try {
			String sql = "SELECT * FROM spel WHERE speler1 = ? OR speler2 = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, name);
			ResultSet data = preparedStatement.executeQuery();
			games = new ArrayList<>();
			while(data.next()) 
				games.add(new Game(data));
		} catch (SQLException e) { }
		return games;
	}
	
	public Game getGame(int gameId) {
		Game game = null;
		String sql = "SELECT * FROM spel WHERE spel_id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, gameId);
			ResultSet data = preparedStatement.executeQuery();
			if (data.next()) 
				game = new Game(data);
		} catch (SQLException e) {
			System.err.println("Error fetching game with id: " + gameId);
			System.err.println(e.getMessage());
		}
		return game;
	}
	
	public Round getRound(Game game, RoundType roundType) {
		Round round = null;
		try{
			String sql = "SELECT * FROM ronde WHERE spel_id = ? and rondenaam = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, game.getId());
			preparedStatement.setString(2, roundType.getValue());
			ResultSet data = preparedStatement.executeQuery();
			if (data.next()) {
				switch (roundType) {
				case ThreeSixNine:	
					round = new ThreeSixNine(data, game);
					break;
				case OpenDoor:
					round = new OpenDoor(data, game);
					break;
				case Puzzle:
					round = new Puzzle(data, game);
					break;
				case Framed:
					round = new Framed(data, game);
					break;
				case Final:
					round = new Final(data, game);
					break;
				default:
					break;
				}
			}
		} catch (SQLException e) { }
		return round;
	}
	
	public ArrayList<Round> getRounds(Game game) {
		ArrayList<Round> rounds = null;
		try{
			String sql = "SELECT * FROM ronde WHERE spel_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, game.getId());
			ResultSet data = preparedStatement.executeQuery();
			RoundType roundType;
			Round round = null;
			rounds = new ArrayList<>();
			while (data.next()) {
				roundType = RoundType.fromString(data.getString("rondenaam"));
				switch (roundType) {
				case ThreeSixNine:	
					round = new ThreeSixNine(data, game);
					break;
				case OpenDoor:
					round = new OpenDoor(data, game);
					break;
				case Puzzle:
					round = new Puzzle(data, game);
					break;
				case Framed:
					round = new Framed(data, game);
					break;
				case Final:
					round = new Final(data, game);
					break;
				default:
					break;
				}
				rounds.add(round);
			}
		} catch (SQLException e) { }
		return rounds;
	}
	
	public ArrayList<Question> getQuestions(Round round) {
		ArrayList<Question> questions = null;
		try {
			String sql = "SELECT * FROM vraag WHERE rondenaam = ? ORDER BY RAND()";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, round.getRoundType().getValue());
			ResultSet data = preparedStatement.executeQuery();
			questions = new ArrayList<>();
			while (data.next()) 
				questions.add(new Question(data, round));
		} catch (SQLException e) {
			System.err.println("Error fetching questions for round: " + round.getRoundType().getValue());
			System.err.println(e.getMessage());
		}
		return questions;
	}
	
	public ArrayList<Answer> getAnswers(int questionId) {
		ArrayList<Answer> answers = null;
		try {
			String sql = "SELECT * FROM sleutel WHERE vraag_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, questionId);
			ResultSet data = preparedStatement.executeQuery();
			answers = new ArrayList<>();
			while (data.next()) 
				answers.add(new Answer(data));
		} catch (SQLException e) {
			System.err.println("Error fetching answers for question id: " + questionId);
			System.err.println(e.getMessage());
		}
		return answers;
	}
	
	public ArrayList<String> getAlternatives(int questionId, String answer) {
		ArrayList<String> alternatives = null;
		try {
			String sql = "SELECT * FROM alternatief WHERE vraag_id = ? AND antwoord = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, questionId);
			preparedStatement.setString(2, answer);
			ResultSet data = preparedStatement.executeQuery();
			alternatives = new ArrayList<>();
			while (data.next()) 
				alternatives.add(data.getString("synoniem"));
		} catch (SQLException e) {
			System.err.println("Error fetching alternatives for question id: " + questionId);
			System.err.println(e.getMessage());
		}
		return alternatives;
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
			String sql = "SELECT * FROM chatregel WHERE spel_id = ?";
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