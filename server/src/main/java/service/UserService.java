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

  public AuthData register(UserData user) throws DataAccessException {   // where should I be handling the exceptions???
//    try {
//      if (userDAO.getUser(user.username()) != null) {
//        throw new ServiceException("User already exists");
//      }
//      authDAO.createAuth(user.username());
//      return authDAO.getAuth(user.username());
//    } catch (DataAccessException e) {
//      throw new ServiceException("Error registering user");
//    }
    if (userDAO.getUser(user.username()) != null) {
      throw new DataAccessException("User already exists");
    }
    userDAO.createUser(user.username(), user.password(), user.email());
    authDAO.createAuth(user.username());
    return authDAO.getAuth(user.username());
  }
  public AuthData login(UserData user) throws DataAccessException {
//    try {
//      authDAO.getAuth(userDAO.getUser(user.username()).username());
//    } catch (DataAccessException e) {
//      throw new ServiceException("User already exist");
//    }
    if (userDAO.getUser(user.username()) == null) {
      throw new DataAccessException("User doesn't exist");
    }
    if (authDAO.getAuth(user.username()) == null) {
      throw new DataAccessException("User doesn't exist");
    }

  }
  public void logout(UserData user) throws DataAccessException {

//    try {
//      userDAO.logoutUser(user.username());
//    } catch (DataAccessException e) {
//      throw new ServiceException("User not able to log out ");
//    }
  }
}
