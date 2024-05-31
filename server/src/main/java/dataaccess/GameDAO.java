package dataaccess;

//import chess.ChessGame;
import model.GameData;
import java.util.Collection;

public interface GameDAO {

  int createGame(int gameID, String gameName) throws DataAccessException;    // public???

  GameData getGame(int gameID) throws DataAccessException;
  Collection<GameData> listGames() throws DataAccessException;

  void updateGame(GameData game) throws DataAccessException;

  void deleteAllGames() throws DataAccessException;
}
