package dataaccess;

import model.GameData;
import java.util.Collection;

public interface GameDAO {

  void createGame(int gameID, String gameName) throws DataAccessException;  // what more is essential beyond ID?
  Collection<GameData> listGames() throws DataAccessException;

  void updateGame(GameData game) throws DataAccessException; // not sure how to update... maybe with a move or more info?

  GameData joinGame(String gameID, String username) throws DataAccessException;

  void deleteAllGames() throws DataAccessException;
}
