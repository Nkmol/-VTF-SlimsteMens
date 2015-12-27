package Managers;

import java.sql.*;
import java.util.ArrayList;

import org.omg.CORBA.FloatSeqHelper;

import Models.*;

public class DataManager {

//	private static final String dbUrl = "jdbc:mysql://localhost/slimsteMens";
//	private static final String username = "root";
//	private static final String password = "root";
	private static final String dbUrl = "jdbc:mysql://databases.aii.avans.nl:3306/spmol_db2";
	private static final String username = "spmol";
	private static final String password = "Ab12345";
	
	private static DataManager instance = null;
//	private Connection connection;
	private Player user;
	
	private DataManager() { 
//		connection = getConnection();
	}
	
	public static DataManager getInstance() {
		if(instance == null) 
			instance = new DataManager();
		return instance;
	}
	
	public boolean signIn(String name, String password) {
		boolean loggedIn = false;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM account AS a "
					+ "INNER JOIN accountrol AS ar on a.naam = ar.account_naam "
					+ "WHERE a.naam = ? AND a.wachtwoord = ? LIMIT 1";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			data = preparedStatement.executeQuery();
			if (data.next()) {
				user = new Player(data);
				loggedIn = true;
			}
		} catch (SQLException e) {
			System.err.println("Error fetching password for user: " + name);
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
			
		}
		return loggedIn;
	}
	
	public boolean changeUserPassword(String name, String newPassword) {
		boolean updated = false;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String sql = "UPDATE account set wachtwoord = ? WHERE naam = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, newPassword);
			preparedStatement.setString(2, name);
			if (preparedStatement.executeUpdate() > 0) {
				connection.commit();
				updated = true;
			}
		} catch (SQLException e) {
			System.err.println("Error changing password for user: " + name);
			System.err.println(e.getMessage());
		} finally {
			try {
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return updated;
	}
	
	public Player getCurrentUser() {
		return user;
	}
	
	public void resetCurrectUser() {
		user = null;
	}
	
	public ArrayList<Player> getAllPlayers() {
		ArrayList<Player> players = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * from account as a "
					+ "INNER JOIN accountrol AS ar ON a.naam = ar.account_naam";
			preparedStatement = connection.prepareStatement(sql);
			data = preparedStatement.executeQuery();
			players = new ArrayList<>();
			while(data.next()) 
				players.add(new Player(data));
		} catch (SQLException e) { }
		finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return players;
	}
	  
	public ArrayList<CompetitionRankItem> getCompetitionRank() {
		ArrayList<CompetitionRankItem> competitionRank = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM competitiestand ORDER BY (aantal_gewonnen_spellen / aantal_verloren_spellen)";
			preparedStatement = connection.prepareStatement(sql);
			data = preparedStatement.executeQuery();
			competitionRank = new ArrayList<>();
			while (data.next())
				competitionRank.add(new CompetitionRankItem(data));
		} catch (SQLException e) {
			System.err.println("Error fetching competition rank");
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return competitionRank;
	}
	 
	public CompetitionRankItem getCompetitionRankForPlayer(String playerName) {
		CompetitionRankItem competitionRank = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM competitiestand WHERE speler = ? ORDER BY (aantal_gewonnen_spellen / aantal_verloren_spellen)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, playerName);
			data = preparedStatement.executeQuery();
			while (data.next())
				competitionRank = new CompetitionRankItem(data);
		} catch (SQLException e) {
			System.err.println("Error fetching competition rank");
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return competitionRank;
	}
	
	public boolean pushNewGame(Player player1, Player player2) {
		boolean pushed = false;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String sql = "INSERT INTO spel (speler1, speler2, toestand_type) VALUES (?,?,?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, player1.getName());
			preparedStatement.setString(2, player2.getName());
			preparedStatement.setString(3, GameState.Invited.getValue());
			if (preparedStatement.executeUpdate() > 0) {
				connection.commit();
				pushed = true;
			}
		} catch (SQLException e) {
			System.err.println("Error inserting new game");
			System.err.println(e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {	}
		} finally {
			try {
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return pushed;
	}
	
	public boolean updateGameState(GameState newGameState, int gameId) {
		boolean updated = false;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String sql = "UPDATE spel SET toestand_type = ? WHERE spel_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, newGameState.getValue());
			preparedStatement.setInt(2, gameId);
			if (preparedStatement.executeUpdate() > 0) {
				connection.commit();
				updated = true;
			}
		} catch (SQLException e) {
			System.err.println("Error updating game with id: " + gameId);
			System.err.println(e.getMessage());
		} finally {
			try {
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return updated;
	}
	
	public boolean gameExistsBetween(String player1, String player2, GameState gameState) {
		boolean gameExists = false;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM spel "
					+ "WHERE speler1 = ? AND speler2 = ? AND toestand_type = ? LIMIT 1";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, player1);
			preparedStatement.setString(2, player2);
			preparedStatement.setString(3, gameState.getValue());
			data = preparedStatement.executeQuery();
			if (data.next())
				gameExists = true;
		} catch (SQLException e) {
			System.err.println("Error checking game");
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return gameExists;
	}
	
	public ArrayList<Game> getAllGamesForPlayer(String name) {
		ArrayList<Game> games = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM spel WHERE speler1 = ? OR speler2 = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, name);
			data = preparedStatement.executeQuery();
			games = new ArrayList<>();
			while(data.next()) 
				games.add(new Game(data));
		} catch (SQLException e) { }
		finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return games;
	}
	
	public ArrayList<GameInfo> getAllGameInfosForPlayer(String name) {
		ArrayList<GameInfo> gameInfos = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM spel WHERE speler1 = ? OR speler2 = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, name);
			data = preparedStatement.executeQuery();
			gameInfos = new ArrayList<>();
			while(data.next()) 
				gameInfos.add(new GameInfo(data));
		} catch (SQLException e) { }
		finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return gameInfos;
	}
	
	public ArrayList<GameScore> getGameScoresForPlayer(String playerName) {
		ArrayList<GameScore> gameScores = null;
		Connection connection = getConnection();
		try {
			String sql = "SELECT * FROM score WHERE speler1 = ? OR speler2 = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, playerName);
			preparedStatement.setString(2, playerName);
			ResultSet data = preparedStatement.executeQuery();
			gameScores = new ArrayList<>();
			while (data.next()) 
				gameScores.add(new GameScore(data));
		} catch (SQLException e) {
			System.err.println("Erroring fetching games score for player: " + playerName);
			System.err.println(e.getMessage());
		} finally {
			if (connection != null){
				try {
					connection.close();
				} catch (SQLException e) {}
			}
		}
		return gameScores;
	}
	
	public GameScore getGameScore(int gameId) {
		GameScore gameScore = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM score WHERE spel_id = ? LIMIT 1";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, gameId);
			data = preparedStatement.executeQuery();
			if (data.next())
				gameScore = new GameScore(data);
		} catch (SQLException e) {
			System.err.println("Error fetching game score for game id: " + gameId);
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return gameScore;
	}
	
	public Game getGame(int gameId) {
		Game game = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM spel WHERE spel_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, gameId);
			data = preparedStatement.executeQuery();
			if (data.next()) 
				game = new Game(data);
		} catch (SQLException e) {
			System.err.println("Error fetching game with id: " + gameId);
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return game;
	}
	
	public Round getRound(Game game, RoundType roundType) {
		Round round = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try{
			String sql = "SELECT * FROM ronde WHERE spel_id = ? and rondenaam = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, game.getId());
			preparedStatement.setString(2, roundType.getValue());
			data = preparedStatement.executeQuery();
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
		finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return round;
	}
	
	public boolean pushRound(Round round) {
		boolean pushed = false;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String sql = "INSERT INTO ronde VALUES (?, ?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, round.getGame().getId());
			preparedStatement.setString(2, round.getRoundType().getValue());
			if (preparedStatement.executeUpdate() > 0) {
				connection.commit();
				pushed = true;
			}
		} catch (SQLException e) {
			System.err.println("Error pushing new round of type: " + round.getRoundType().getValue() + " & game id: " + round.getGame().getId());
			System.err.println(e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) { }
		} finally {
			try {
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return pushed;
	}
	
	public ArrayList<Round> getRounds(Game game) {
		ArrayList<Round> rounds = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try{
			String sql = "SELECT * FROM ronde WHERE spel_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, game.getId());
			data = preparedStatement.executeQuery();
			RoundType roundType;
			Round round = null;
			rounds = new ArrayList<>();
			while (data.next()) {
				roundType = RoundType.fromString(data.getString("rondenaam"));
				round = getRoundForRoundType(roundType, data, game);
				rounds.add(round);
			}
		} catch (SQLException e) { }
		finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return rounds;
	}
	
	public Round getLastRoundForGame(Game game) {
		Round round = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM ronde AS r "
					+ "WHERE rondenaam = "
					+ "(SELECT rn.type FROM rondenaam AS rn WHERE volgnr = "
					+ "(SELECT COUNT(spel_id) FROM ronde WHERE spel_id = r.spel_id)) "
					+ "AND spel_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, game.getId());
			data = preparedStatement.executeQuery();
			if (data.next()) {
				RoundType roundType = RoundType.fromString(data.getString("rondenaam"));
				round = getRoundForRoundType(roundType, data, game);
			}
		} catch (SQLException e) {
			System.err.println("Error fetching last round for game id: " + game.getId());
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return round;
	}
	
	private Round getRoundForRoundType(RoundType roundType, ResultSet data, Game game) {
		Round round = null;
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
		return round;
	}
	
	public ArrayList<Turn> getTurns(Round round) {
		ArrayList<Turn> turns = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM beurt WHERE spel_id = ? AND rondenaam = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, round.getGame().getId());
			preparedStatement.setString(2, round.getRoundType().getValue());
			data = preparedStatement.executeQuery();
			turns = new ArrayList<>();
			while (data.next())
				turns.add(new Turn(data, round));
		} catch (SQLException e) {
			System.err.println("Error fetching turns for game id: " + round.getGame().getId());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return turns;
	}
	
	// TODO Only use this -> Conflicting with Game.isCurrentPlayerTurn(int)
	public Turn getLastTurnForGame(Round round) {
		Turn turn = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT b.* FROM ronde AS r"
					+ " INNER JOIN rondenaam AS rn ON"
					+ " (SELECT COUNT(spel_id) FROM ronde WHERE spel_id = r.spel_id AND r.rondenaam = rn.type) = rn.volgnr"
					+ " INNER JOIN beurt AS b ON b.spel_id = r.spel_id AND r.rondenaam = b.rondenaam"
					+ " WHERE r.spel_id = ? ORDER BY b.beurt_id DESC LIMIT 1";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, round.getGame().getId());
			data = preparedStatement.executeQuery();
			if (data.next())
				turn = new Turn(data, round);
		} catch (SQLException e) {
			System.err.println("Error fetching last turn for game id: " + round.getGame().getId());
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return turn;
	}
	
	public Turn getLastTurnForGame(int gameId) {
		Turn turn = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT b.* FROM ronde AS r"
					+ " INNER JOIN rondenaam AS rn ON"
					+ " (SELECT COUNT(spel_id) FROM ronde WHERE spel_id = r.spel_id AND r.rondenaam = rn.type) = rn.volgnr"
					+ " INNER JOIN beurt AS b ON b.spel_id = r.spel_id AND r.rondenaam = b.rondenaam"
					+ " WHERE r.spel_id = ? ORDER BY b.beurt_id DESC LIMIT 1";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, gameId);
			data = preparedStatement.executeQuery();
			if (data.next())
				turn = new Turn(data);
		} catch (SQLException e) {
			System.err.println("Error fetching last turn for game id: " + gameId);
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return turn;
	}
	
	public boolean pushTurn(Turn turn) {
		boolean turnPushed = false;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String sql = "INSERT INTO beurt "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);
			RoundType roundType = turn.getRoundType();
			preparedStatement.setInt(1, turn.getGameId());
			preparedStatement.setString(2, roundType.getValue());
			preparedStatement.setInt(3, turn.getTurnId());
			if (roundType == RoundType.ThreeSixNine || roundType == RoundType.Puzzle) 
				preparedStatement.setNull(4, Types.INTEGER);
			else 
				preparedStatement.setInt(4, turn.getCurrentQuestion().getId());
			preparedStatement.setString(5, turn.getPlayer().getName());
			preparedStatement.setString(6, turn.getTurnState().getValue());
			preparedStatement.setInt(7, turn.getSecondsEarned());
			if (roundType != RoundType.Final) 
				preparedStatement.setNull(8, Types.INTEGER);
			else 
				preparedStatement.setInt(8, turn.getSecondsFinalLost());
			if (preparedStatement.executeUpdate() > 0) {
				if (roundType == RoundType.ThreeSixNine || roundType == RoundType.Puzzle) {
					pushSharedQuestion(turn);
				} else if (roundType != RoundType.ThreeSixNine) {
					pushPlayerAnswer(turn); //TODO delete from here 
				}
				connection.commit();
			}
			
		} catch (SQLException e) {
			System.err.println("Error pushing turn");
			System.err.println(e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {}
		} finally {
			try {
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		
		return turnPushed;
	}
	
	public boolean updateTurn(Turn turn) {
		boolean updated = false;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String sql = "update beurt set "
					+ "beurtstatus = ?, sec_verdiend = ?, sec_finale_af = ? "
					+ "where spel_id = ? and rondenaam = ? and beurt_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, turn.getTurnState().getValue());
			preparedStatement.setInt(2, turn.getSecondsEarned());
			if (turn.getSecondsFinalLost() != null) 
				preparedStatement.setInt(3, turn.getSecondsFinalLost());
			else 
				preparedStatement.setNull(3, Types.INTEGER);
			preparedStatement.setInt(4, turn.getGameId());
			preparedStatement.setString(5, turn.getRoundType().getValue());
			preparedStatement.setInt(6, turn.getTurnId());
			if (preparedStatement.executeUpdate() > 0) {
				updated = true;
				connection.commit();
			}
			
		} catch (SQLException e) {
			System.err.println("Error updating turn with id : " + turn.getTurnId());
			System.err.println(e.getMessage());
		} finally {
			try {
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		
		return updated;
	}
	
	public boolean pushSharedQuestion(Turn turn) throws SQLException {
		boolean pushed = false;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			if (turn.getSharedQuestions() != null) {
				for (SharedQuestion sharedQuestion : turn.getSharedQuestions()) {
					String sql = "INSERT INTO deelvraag "
							+ "VALUES (?, ?, ?, ?, ?, ?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, sharedQuestion.getGameId());
					preparedStatement.setString(2, sharedQuestion.getRoundType().getValue());
					preparedStatement.setInt(3, sharedQuestion.getTurnId());
					preparedStatement.setInt(4, sharedQuestion.getIndexNumber());
					preparedStatement.setInt(5, sharedQuestion.getQuestionId());
					preparedStatement.setString(6, sharedQuestion.getAnswer());
					if (preparedStatement.executeUpdate() > 0) {
						pushed = true;
						connection.commit();
					}
				}
			}
		}catch (SQLException e) {
			// TODO: handle exception
		} finally {
			try {
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return pushed;
	}
	
	public boolean pushPlayerAnswer(Turn turn) throws SQLException {
		boolean pushed = false;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			if (turn.getPlayerAnswers() != null) {
				for (PlayerAnswer playerAnswer : turn.getPlayerAnswers()) {
					String sql = "INSERT INTO spelerantwoord VALUES (?, ?, ?, ?, ?, ?)";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, playerAnswer.getGameId());
					preparedStatement.setString(2, playerAnswer.getRoundType().getValue());
					preparedStatement.setInt(3, playerAnswer.getTurnId());
					preparedStatement.setInt(4, playerAnswer.getAnswerId());
					preparedStatement.setString(5, playerAnswer.getAnswer());
					preparedStatement.setInt(6, playerAnswer.getMoment());
					if (preparedStatement.executeUpdate() > 0) {
						pushed = true;
						connection.commit();
					}
				}
			}
		}
		catch (SQLException e) { }
		finally {
			try {
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return pushed;
	}
	
	public ArrayList<SharedQuestion> getSharedQuestions(Turn turn) {
		ArrayList<SharedQuestion> sharedQuestions = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM deelvraag WHERE spel_id = ? AND rondenaam = ? AND beurt_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, turn.getGameId());
			preparedStatement.setString(2, turn.getRoundType().getValue());
			preparedStatement.setInt(3, turn.getTurnId());
			data = preparedStatement.executeQuery();
			sharedQuestions = new ArrayList<>();
			while (data.next())
				sharedQuestions.add(new SharedQuestion(data));
		} catch (SQLException e) {
			System.err.println("Error fetching shared questions");
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return sharedQuestions;
	}
	
	public ArrayList<PlayerAnswer> getPlayerAnswers(int gameid, RoundType roundType, int turnid) {
		ArrayList<PlayerAnswer> playerAnswers = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM spelerantwoord WHERE spel_id = ? AND rondenaam = ? AND beurt_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, gameid);
			preparedStatement.setString(2, roundType.getValue());
			preparedStatement.setInt(3, turnid);
			data = preparedStatement.executeQuery();
			playerAnswers = new ArrayList<>();
			while (data.next())
				playerAnswers.add(new PlayerAnswer(data));
		} catch (SQLException e) {
			System.err.println("Error fetching player answers");
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return playerAnswers;
	}
	
	public ArrayList<Question> getQuestions(Round round) {
		ArrayList<Question> questions = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM vraag WHERE rondenaam = ? ORDER BY RAND()";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, round.getRoundType().getValue());
			data = preparedStatement.executeQuery();
			questions = new ArrayList<>();
			while (data.next()) 
				questions.add(new Question(data, round)); //TODO: remove round from the parameters 
		} catch (SQLException e) {
			System.err.println("Error fetching questions for round: " + round.getRoundType().getValue());
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return questions;
	}
	
	public Question getQuestionForId(int id) {
		Question question = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM vraag WHERE vraag_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			data = preparedStatement.executeQuery();
			if (data.next()) 
				question = new Question(data);
		} catch(SQLException e) {
			System.err.println("Error while fetching the question with id: " + id);
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return question;
	}
	
	public Integer getTotalSecondsEarnedInAGame(int gameId, String playerName) {
		Integer totalSecEarned = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT SUM(sec_verdiend) AS totaal_sec_verdiend FROM beurt WHERE spel_id = ? AND speler = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, gameId);
			preparedStatement.setString(2, playerName);
			data = preparedStatement.executeQuery();
			if (data.next())
				totalSecEarned = data.getInt("totaal_sec_verdiend");
		} catch(SQLException e) {
			System.err.println("Error fetching total seconds earned in a game with id: " + gameId + " and player name: " + playerName);
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return totalSecEarned;
	}
	
	public int numberOfTimesQuestionAskedTo(String playerName, int questionId) {
		int amountOfTimes = 0;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT COUNT(speler) AS aantal "
					+ "FROM speler_vraag_aantal WHERE speler = ? AND vraag_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, playerName);
			preparedStatement.setInt(2, questionId);
			data = preparedStatement.executeQuery();
			if (data.next())
				amountOfTimes = data.getInt("aantal");
		} catch (SQLException e) {
			System.err.println("Error checking amount of times the question asked to a player");
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return amountOfTimes;
	}
	
	public ArrayList<Answer> getAnswers(int questionId) {
		ArrayList<Answer> answers = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM sleutel WHERE vraag_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, questionId);
			data = preparedStatement.executeQuery();
			answers = new ArrayList<>();
			while (data.next()) 
				answers.add(new Answer(data));
		} catch (SQLException e) {
			System.err.println("Error fetching answers for question id: " + questionId);
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return answers;
	}
	
	public ArrayList<String> getAlternatives(int questionId, String answer) {
		ArrayList<String> alternatives = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM alternatief WHERE vraag_id = ? AND antwoord = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, questionId);
			preparedStatement.setString(2, answer);
			data = preparedStatement.executeQuery();
			alternatives = new ArrayList<>();
			while (data.next()) 
				alternatives.add(data.getString("synoniem"));
		} catch (SQLException e) {
			System.err.println("Error fetching alternatives for question id: " + questionId);
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return alternatives;
	}
	
	public Player getPlayer(String playerName) {
		Player player = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * from account as a "
					+ "INNER JOIN accountrol AS ar ON a.naam = ar.account_naam "
					+ "WHERE a.naam = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, playerName);
			data = preparedStatement.executeQuery();
			if(data.next()) 
				player = new Player(data);
		} catch (SQLException e) {
			System.err.println("Error fetching player with id: " + playerName);
			System.err.println(e.getMessage());
		} finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return player;
	}
	
	public boolean registerUser(String name, String password, Role role) {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String sql = "insert into account (naam, wachtwoord) values (?,?)";
			preparedStatement = connection.prepareStatement(sql);
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
		} finally {
			try {
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
	}
	
	public boolean pushChatMessage(ChatMessage chatMessage) {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			String sql = "insert into chatregel (spel_id, tijdstip, millisec, account_naam_zender, bericht)"
					+ " values (?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, chatMessage.getGameId());
			preparedStatement.setTimestamp(2, chatMessage.getTimestamp());
			preparedStatement.setInt(3, chatMessage.getMillisec());
			preparedStatement.setString(4, chatMessage.getSenderName());
			preparedStatement.setString(5, chatMessage.getMessage());
			preparedStatement.executeUpdate();
			connection.commit();
			return true;
		} catch (SQLException e) {
			System.err.println("Error inserting a new chat message: " + e.getMessage());
			try { connection.rollback(); } 
			catch (SQLException e1) { System.err.println("Error rolling back " + e1.getMessage()); }
			return false;
		} finally {
			try {
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
	}
	
	public ArrayList<ChatMessage> getChatMessages(int gameId) {
		getConnection();
		ArrayList<ChatMessage> chatMessages = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet data = null;
		try {
			String sql = "SELECT * FROM chatregel WHERE spel_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, gameId);
			data = preparedStatement.executeQuery();
			chatMessages = new ArrayList<>();
			while(data.next()) 
				chatMessages.add(new ChatMessage(data));
		} catch (SQLException e) { }
		finally {
			try {
				if (data != null)
					data.close();
				if (preparedStatement != null) 
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch(SQLException ex) {} 
		}
		return chatMessages;
	}
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			/*if (connection != null)
				connection.close();*/
			//System.out.println("Connecting.....");
				connection = DriverManager.getConnection(dbUrl, username, password);
				connection.setAutoCommit(false);
			
			//System.out.println("Connected");
		} catch (SQLException e) {
			System.err.println("Connection Error: " + e.getMessage());
		}
		return connection;
	}
	
}
