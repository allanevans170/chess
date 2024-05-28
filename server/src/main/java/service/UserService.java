package service;

import model.AuthData;
import model.UserData;
import dataaccess.UserDAO;

public class UserService {

  private final UserDAO userDAO;

  public UserService(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  public AuthData register(UserData user) {   // where should I be handling the exceptions???
    return userDAO.createUser(user.username(), user.password(), user.email());
  }
  public AuthData login(UserData user) {
    return userDAO.getUser(user.username());
  }
  public void logout(UserData user) {
    userDAO.logoutUser(user.username());
  }
}
