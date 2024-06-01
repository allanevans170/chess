package dataaccess;

import model.UserData;

import java.util.Collection;

public class SQLUserDAO implements UserDAO {
  @Override
  public void createUser(String username, String password, String email) throws DataAccessException {

  }

  @Override
  public Collection<UserData> listUsers() throws DataAccessException {
    return null;
  }

  @Override
  public UserData getUser(String username) throws DataAccessException {
    return null;
  }

  @Override
  public void deleteAllUsers() throws DataAccessException {

  }
}
