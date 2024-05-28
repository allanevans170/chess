package dataaccess;

import model.GameData;
import java.util.Collection;

public interface GameDAO {

  void createGame(int gameID, String gameName) throws DataAccessException;

  GameData getGame(int gameID) throws DataAccessException;
  Collection<GameData> listGames() throws DataAccessException;

  void updateGame(GameData game) throws DataAccessException;

  void deleteAllGames() throws DataAccessException;
}
