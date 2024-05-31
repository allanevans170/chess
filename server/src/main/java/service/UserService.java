package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import dataaccess.UserDAO;

public class UserService {
  private final UserDAO userDAO;
  private final AuthDAO authDAO;

  public UserService(UserDAO userDAO, AuthDAO authDAO) {
    this.userDAO = userDAO;
    this.authDAO = authDAO;
  }

  public AuthData register(UserData user) throws ServiceException {   // needs a success 200 and bad request 400
    try {
      if (user.username() == "" || user.password() == "" || user.email() == "") {
        throw new ServiceException(400, "Error: bad request");
      }
      if (userDAO.getUser(user.username()) != null) {
        throw new ServiceException(403, "Error: already taken");
      }
      userDAO.createUser(user.username(), user.password(), user.email());
      return authDAO.createAuth(user.username());
    }
    catch (DataAccessException e) {
      throw new ServiceException(500, "Error: " + e.getMessage());
    }
  }

  public AuthData login(UserData user) throws ServiceException {   // success 200
    try {
      if (userDAO.getUser(user.username()) == null) {           // username not in database
        throw new ServiceException(401, "Error: unauthorized");
      }
      if (user.password() != userDAO.getUser(user.username()).password()) {   // password verification
        throw new ServiceException(401, "Error: unauthorized");
      }
      return authDAO.createAuth(user.username());
    } catch (DataAccessException e) {
      throw new ServiceException(500, "Error: "+ e.getMessage());
    }
  }

  public void logout(AuthData auth) throws ServiceException {
    try {
      if (authDAO.getAuth(auth.authToken()) == null) {
        throw new ServiceException(401, "Error: unauthorized");
      }
      authDAO.deleteAuth(auth.authToken());
    } catch (DataAccessException e) {
      throw new ServiceException(500, "Error: description...");
    }

  }
}
