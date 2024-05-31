package service;

import dataaccess.*;
import model.*;

import java.util.Collection;

public class GameService {

  private final GameDAO gameDAO;        // where should I put the clearService???
  private final AuthDAO authDAO;
  private int nextID = 1;

  public GameService(GameDAO gameDAO, AuthDAO authDAO) {
    this.gameDAO = gameDAO;
    this.authDAO = authDAO;
  }

  public int createGame(AuthData auth, String gameName) throws ServiceException {    // username???
    try {
      int currentID = nextID;
      nextID++;
      if (authDAO.getAuth(auth.authToken()) == null) {
        throw new ServiceException(401, "Error: unauthorized");
      }
      gameDAO.createGame(currentID, gameName);
      return currentID;
    } catch (DataAccessException e) {
      throw new ServiceException(500, "Error: "+e.getMessage());
    }
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
