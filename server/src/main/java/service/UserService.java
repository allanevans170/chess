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

  public AuthData register(UserData user) throws ServiceException {   // success response: 200??
    try {
      if (userDAO.getUser(user.username()) != null) {
        throw new ServiceException(403, "Error: already taken");
      }
      userDAO.createUser(user.username(), user.password(), user.email());
      authDAO.createAuth(user.username());

      return authDAO.getAuth(user.username());
    }
    catch (DataAccessException e) {
      throw new ServiceException(500, "Error: ");
    }
  }

  public AuthData login(UserData user) throws ServiceException {   // data access vs service exceptions ????????????????????????
    try {
      if (userDAO.getUser(user.username()) == null) {
        throw new ServiceException(401, "Error: unauthorized");
      }
      // need to verify passwrod here?
      authDAO.createAuth(user.username());
      return authDAO.getAuth(user.username());
    } catch (DataAccessException e) {
      throw new ServiceException(500, "Error: description..."); // include message from data access exception??
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
