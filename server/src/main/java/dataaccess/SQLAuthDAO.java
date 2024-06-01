package dataaccess;

import model.AuthData;

import java.util.Collection;

public class SQLAuthDAO implements AuthDAO {

  @Override
  public AuthData createAuth(String username) throws DataAccessException {
    return null;
  }

  @Override
  public Collection<AuthData> listAuths() throws DataAccessException {
    return null;
  }

  @Override
  public AuthData getAuth(String authToken) throws DataAccessException {
    return null;
  }

  @Override
  public void deleteAuth(String authToken) throws DataAccessException {

  }

  @Override
  public void deleteAllAuths() throws DataAccessException {

  }
}
