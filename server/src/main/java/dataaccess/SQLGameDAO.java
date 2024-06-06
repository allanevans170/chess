package dataaccess;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class SQLGameDAO extends SQLAccess implements GameDAO {
  public SQLGameDAO() throws DataAccessException {
  }

  @Override
  public GameData createGame(String gameName) throws DataAccessException {
    GameData game = new GameData(gameName);                                     // creation of AuthData object
    String gameJson = new Gson().toJson(game.getGame());                        // converting AuthData object to JSON
    String statement = "INSERT INTO games (gameID, gameName, game) VALUES (?, ?, ?)";
    // usernames null by default
    try {
      int gameID=executeUpdate(statement, game.getGameID(), game.getGameName(), gameJson);
      game.setGameID(gameID);
    } catch (DataAccessException e) {
      throw new DataAccessException("Game not created");
    }
    return game;
  }

  @Override
  public GameData getGame(int gameID) throws DataAccessException {
    String statement = "SELECT gameID, gameName, game FROM games WHERE gameID = ?";
    try (var conn = DatabaseManager.getConnection();
         var ps = conn.prepareStatement(statement)) {
      ps.setInt(1, gameID);
      var rs = ps.executeQuery();
      if (rs.next()) {
        return readGame(rs);
      }
    } catch (Exception e) {
      throw new DataAccessException("Unable to get game: " + e.getMessage());
    }
    return null; // if no game is found
  }

  @Override
  public Collection<GameData> listGames() throws DataAccessException {
    Collection<GameData> result = new ArrayList<>();
    String statement = "SELECT gameID, whiteUsername, blackUsername, gameName FROM games";

    try (var conn = DatabaseManager.getConnection();
         PreparedStatement ps = conn.prepareStatement(statement)) {
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        result.add(readGame(rs));
      }
    } catch (Exception e) {
      throw new DataAccessException("Unable to list games: " + e.getMessage());
    }
    return result;
  }

  private GameData readGame(ResultSet rs) throws SQLException {
    int gameID = rs.getInt("gameID");
    String whiteUsername = rs.getString("whiteUsername");
    String blackUsername = rs.getString("blackUsername");
    String gameName = rs.getString("gameName");

    GameData game = new GameData(gameName);
    game.setGameID(gameID);
    game.setWhiteUsername(whiteUsername);
    game.setBlackUsername(blackUsername);
    //game.setGame(new Gson().fromJson(rs.getString("game"), GameData.class));
    return game;
  }

  public void updateGame(GameData game) throws DataAccessException {
    String statement = "UPDATE games SET whiteUsername = ?, blackUsername = ?, gameName = ?, game = ? WHERE gameID = ?";
    String gameJson = new Gson().toJson(game.getGame());
    executeUpdate(statement, game.getWhiteUsername(), game.getBlackUsername(), game.getGameName(), gameJson, game.getGameID());
  }

  @Override
  public void deleteAllGames() throws DataAccessException {
    String statement = "DELETE FROM games";
    try {
      executeUpdate(statement);
    } catch (DataAccessException e) {
      throw new DataAccessException("Unable to delete games: " + e.getMessage());
    }
  }
}
