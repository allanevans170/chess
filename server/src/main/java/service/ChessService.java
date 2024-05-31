package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import model.UserData;

public class ChessService {
  private final AuthDAO authDAO;
  private final UserDAO userDAO;
  private final GameDAO gameDAO;

  private final UserService userService;
  private final GameService gameService;


  public ChessService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
    this.userDAO = userDAO;
    this.authDAO = authDAO;
    this.gameDAO = gameDAO;

    userService = new UserService(userDAO, authDAO);
    gameService = new GameService(gameDAO, authDAO);
  }

  public UserService getUserService() {
    return userService;
  }

  public GameService getGameService() {
    return gameService;
  }

  public void clear() throws ServiceException { // UserData user, AuthData auth, GameData game
    try {
      authDAO.deleteAllAuths();
      userDAO.deleteAllUsers();
      gameDAO.deleteAllGames();
    } catch (DataAccessException e) {
      throw new ServiceException(500, "Error: ");
    }
  }
}
