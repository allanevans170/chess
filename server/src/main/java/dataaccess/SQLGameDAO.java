package dataaccess;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;

import java.util.Collection;

public class SQLGameDAO extends SQLAccess implements GameDAO {
  @Override
  public GameData createGame(int gameID, String gameName) throws DataAccessException {
    GameData game = new GameData(gameID, gameName);                                     // creation of AuthData object
    String statement = "INSERT INTO games (authToken, username) VALUES (?, ?)";     // preparing SQL statement
    String json = new Gson().toJson(new AuthData(username));
    executeUpdate(statement, auth.authToken(), auth.username(), json);
    return new AuthData(username);
  }

  @Override
  public GameData getGame(int gameID) throws DataAccessException {
    return null;
  }

  @Override
  public Collection<GameData> listGames() throws DataAccessException {
    return null;
  }

//  @Override
//  public GameData updateGame(GameData game) throws DataAccessException {
//    return null;
//  }

  @Override
  public void deleteAllGames() throws DataAccessException {

  }
}
