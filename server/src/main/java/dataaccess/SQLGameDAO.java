package dataaccess;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;

public class SQLGameDAO extends SQLAccess implements GameDAO {
  @Override
  public GameData createGame(String gameName) throws DataAccessException {
    GameData game = new GameData(gameName);                                     // creation of AuthData object
    String gameJson = new Gson().toJson(game.getGame());                        // converting AuthData object to JSON
    String statement = "INSERT INTO games (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";

    try {
      int gameID=executeUpdate(statement, game.getGameID(), game.getWhiteUsername(), game.getBlackUsername(), game.getGameName(), gameJson);
      game.setGameID(gameID);
    } catch (DataAccessException e) {
      throw new DataAccessException("Game not created");
    }
    return game;
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
