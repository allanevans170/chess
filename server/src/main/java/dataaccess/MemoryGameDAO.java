package dataaccess;

import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {

  final private HashMap<Integer, GameData> games = new HashMap<>();
  @Override
  public void createGame(int gameID, String gameName) throws DataAccessException {
    GameData game = new GameData(gameID, gameName);
    games.put(game.gameID(), game);
  }

  @Override
  public GameData getGame(int gameID) throws DataAccessException {
    return games.get(gameID);
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
  public void deleteAllGames() throws DataAccessException {
    games.clear();
  }
}
