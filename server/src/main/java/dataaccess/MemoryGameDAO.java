package dataaccess;

import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {

  final private HashMap<Integer, GameData> games = new HashMap<>();
  @Override
  public int createGame(int gameID, String gameName) throws DataAccessException {
    GameData game = new GameData(gameID, gameName);
    try {
      games.put(game.gameID(), game);
    } catch (Exception e) {
      throw new DataAccessException("Game not created");
    }
    return game.gameID();
  }

  @Override
  public GameData getGame(int gameID) throws DataAccessException {
    try {
      return games.get(gameID);
    } catch (Exception e) {
      throw new DataAccessException("Game not found");
    }
  }

  @Override
  public Collection<GameData> listGames() throws DataAccessException {
    return games.values();
  }

  @Override
  public void updateGame(GameData game) throws DataAccessException {
    // how should I be updating the game here
  }

  @Override
  public void deleteAllGames() throws DataAccessException {      // I don't think I need a DataAccessException here
    games.clear();
  }
}
