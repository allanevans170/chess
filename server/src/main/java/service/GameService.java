package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.GameData;
import dataaccess.GameDAO;

import java.util.Collection;

public class GameService {

  private final GameDAO gameDAO;        // where should I put the clearService???
  private final AuthDAO authDAO;
  private int nextID = 1;

  public GameService(GameDAO gameDAO, AuthDAO authDAO) {
    this.gameDAO = gameDAO;
    this.authDAO = authDAO;
  }

  public int createGame(String gameName) throws ServiceException {    // username???
//    try {
//      int currentID = nextID;
//      nextID++;
//
//      authDAO.getAuth(username);
//      return currentID;
//    } catch (DataAccessException e) {
//      throw new ServiceException(500, "Error: description...");
//    }
    return 0;
  }
  public GameData joinGame(int gameID) throws ServiceException {
//    try {
//      return gameDAO.getGame(gameID);
//    } catch (DataAccessException e) {
//      throw new ServiceException(500, "Error: description...");
//    }
    return null;
  }
  public Collection<GameData> listGames() throws ServiceException {
//    try {
//      return gameDAO.listGames();
//    } catch (DataAccessException e) {
//      throw new ServiceException(500, "error: description...");
//    }
    return null;
  }
//
//  public void updateGame(GameData game) throws ServiceException {
//    try {
//      gameDAO.updateGame(game);
//    }
//    gameDAO.updateGame(game);
//  }

}
