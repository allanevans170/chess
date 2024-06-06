package dataaccess;

import model.GameData;
import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {

  final private HashMap<Integer, GameData> games = new HashMap<>();
  private int gameID = 1;
  @Override
  public GameData createGame(String gameName) throws DataAccessException {
    GameData game = new GameData(gameName);
    try {
      game.setGameID(gameID);
      games.put(game.getGameID(), game);
      gameID++;
    } catch (Exception e) {
      throw new DataAccessException("Game not created");
    }
    return game;
  }

  @Override
  public GameData getGame(int gameID) throws DataAccessException {
    return games.get(gameID);
  }

  @Override
  public Collection<GameData> listGames() throws DataAccessException {
    return games.values();
  }

//  @Override
//  public GameData updateGame(GameData game) throws DataAccessException {
//    if (games.get(game.getGameID()) == null) {
//      throw new DataAccessException("Game not found");
//    }
//    games.put(game.getGameID(), game);
//    return game;
//  }

  @Override
  public void deleteAllGames() throws DataAccessException {
    games.clear();
  }
}
