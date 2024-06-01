package dataaccess;

import model.GameData;

import java.util.Collection;

public class SQLGameDAO implements GameDAO {
  @Override
  public GameData createGame(int gameID, String gameName) throws DataAccessException {
    return null;
  }

  @Override
  public GameData getGame(int gameID) throws DataAccessException {
    return null;
  }

  @Override
  public Collection<GameData> listGames() throws DataAccessException {
    return null;
  }

  @Override
  public GameData updateGame(GameData game) throws DataAccessException {
    return null;
  }

  @Override
  public void deleteAllGames() throws DataAccessException {

  }
}
