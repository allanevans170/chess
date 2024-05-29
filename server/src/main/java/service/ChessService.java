package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import model.UserData;

public class ChessService {
  private final AuthDAO authDAO;
  private final UserDAO userDAO;
  private final GameDAO gameDAO;

  public ChessService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
    this.userDAO = userDAO;
    this.authDAO = authDAO;
    this.gameDAO = gameDAO;

    UserService userService = new UserService(userDAO, authDAO);
    GameService gameService = new GameService(gameDAO, authDAO);
  }

  public void clear() { // UserData user, AuthData auth, GameData game
    authDAO.deleteAllAuths();
    userDAO.deleteAllUsers();
    gameDAO.deleteAllGames();
  }
}
