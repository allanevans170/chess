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
      userDAO.createUser(user.username(), user.password(), user.email());
    } catch (DataAccessException e) {
      throw new ServiceException("User already exists");
    }
    try {
      authDAO.createAuth(user.username());
    } catch (DataAccessException e) {
      throw new ServiceException("Error creating auth token while registering user");
    }

  }
  public AuthData login(UserData user) throws ServiceException {
    try {
      return authDAO.getAuth(userDAO.getUser(user.username()).username());
    } catch (DataAccessException e) {
      throw new ServiceException("User does not exist");
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
