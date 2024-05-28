package dataaccess;

import model.UserData;
import java.util.Collection;
import java.util.HashMap;

public interface UserDAO {

  public UserData createUser(String username, String password, String email) throws DataAccessException;

  Collection<UserData> listUsers() throws DataAccessException;

  UserData getUser(String username) throws DataAccessException;

  void logoutUser(String username) throws DataAccessException; // ????

  void deleteAllUsers() throws DataAccessException;

}

