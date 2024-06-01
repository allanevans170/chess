package dataaccess;

//import chess.ChessGame;
import model.GameData;
import java.util.Collection;

public interface GameDAO {

  GameData createGame(int gameID, String gameName) throws DataAccessException;

  GameData getGame(int gameID) throws DataAccessException;
  Collection<GameData> listGames() throws DataAccessException;

//  GameData updateGame(GameData game) throws DataAccessException;

  void deleteAllGames() throws DataAccessException;
}
