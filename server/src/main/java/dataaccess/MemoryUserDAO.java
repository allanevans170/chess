package dataaccess;

import model.UserData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {

  final private HashMap<String, UserData> users = new HashMap<>();
  @Override
  public UserData createUser(String username, String password, String email) throws DataAccessException {
    UserData user = new UserData(username, password, email);
    users.put(username, user);
    return user;
  }

  @Override
  public Collection<UserData> listUsers() throws DataAccessException {
    return users.values();
  }

  @Override
  public UserData getUser(String username) throws DataAccessException {
    return users.get(username);
  }

  @Override
  public void logoutUser(String username) throws DataAccessException {
    users.remove(username);
  }

  @Override
  public void deleteAllUsers() throws DataAccessException {
    users.clear();
  }
}
