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
      userDAO.createUser(user.username(), user.password(), user.email());
      return authDAO.createAuth(user.username());
    }
    catch (DataAccessException e) {
      throw new ServiceException(500, "Error: " + e.getMessage());
    }
  }

  public AuthData login(UserData user) throws ServiceException {   // success 200
    try {
      if (userDAO.getUser(user.username()) == null) {
        throw new ServiceException(401, "Error: unauthorized");
      }
      // need to verify password here?!?!?!?!?!?!?!?!
      return authDAO.createAuth(user.username());
    } catch (DataAccessException e) {
      throw new ServiceException(500, "Error: "+ e.getMessage()); // include message from data access exception??
    }
  }

  public void logout(UserData user) throws ServiceException {
    try {
      if (authDAO.getAuth(user.username()) == null) {
        throw new ServiceException(401, "Error: unauthorized");
      }
      authDAO.deleteAuth(user.username());
    } catch (DataAccessException e) {
      throw new ServiceException(500, "Error: description...");
    }

  }
}
