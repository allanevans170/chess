package service;

import dataaccess.AuthDAO;
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

  public int createGame(String gameName, String authToken) throws ServiceException {
    int currentID = nextID;
    nextID++;
//    try {
//      authDAO.getAuth(authToken);
//    } catch (ServiceException e) {
//      throw new ServiceException("Invalid Auth Token");
//    }
    authDAO.getAuth(authToken);
    return currentID;
  }
  public GameData joinGame(int gameID) throws ServiceException {
    return gameDAO.getGame(gameID);
  }
  public Collection<GameData> listGames() throws ServiceException {
    return gameDAO.listGames();
  }

  public void updateGame(GameData game) throws ServiceException {
    gameDAO.updateGame(game);
  }

}
