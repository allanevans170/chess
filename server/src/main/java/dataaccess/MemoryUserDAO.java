package dataaccess;

import model.UserData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {

  final private HashMap<String, UserData> users = new HashMap<>();
  @Override
  public void createUser(String username, String password, String email) throws DataAccessException {
    if (users.containsKey(username)) {
      throw new DataAccessException("already taken");
    }
    UserData user = new UserData(username, password, email);
    users.put(username, user);
  }

  @Override
  public Collection<UserData> listUsers() throws DataAccessException {
    return users.values();
  }

  @Override
  public UserData getUser(String username) throws DataAccessException {
//    if (users.containsKey(username) == false) {
//      throw new DataAccessException("User does not exist");
//    }
    return users.get(username);
  }

  @Override
  public void deleteUser(String username) throws DataAccessException {
    users.remove(username);
  }

  @Override
  public void deleteAllUsers() {
    users.clear();
  }
}
