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

  public AuthData register(UserData user) throws ServiceException {   // where should I be handling the exceptions???
    try {
      if (userDAO.getUser(user.username()) != null) {
        throw new ServiceException("User already exists");
      }
      authDAO.createAuth(user.username());
      return authDAO.getAuth(user.username());
    } catch (DataAccessException e) {
      throw new ServiceException("Error registering user");
    }
  }
  public AuthData login(UserData user) throws ServiceException {
    try {
      authDAO.getAuth(userDAO.getUser(user.username()).username());
    } catch (DataAccessException e) {
      throw new ServiceException("User already exist");
    }

  }
  public void logout(UserData user) throws ServiceException {
    try {
      userDAO.logoutUser(user.username());
    } catch (DataAccessException e) {
      throw new ServiceException("User not able to log out ");
    }
  }
}
