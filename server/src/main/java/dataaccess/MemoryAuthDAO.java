package dataaccess;

import model.AuthData;

//import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {

  final private HashMap<String, AuthData> auths = new HashMap<>();
  @Override
  public void createAuth(String username) throws DataAccessException {
    try {
      AuthData auth = new AuthData(username);
      auths.put(auth.authToken(), auth);
    } catch (Exception e) {
      throw new DataAccessException("Error creating auth");
    }
  }

  @Override
  public Collection<AuthData> listAuths() throws DataAccessException {
    return auths.values();
  }

  @Override
  public AuthData getAuth(String authToken) throws DataAccessException {
    try {
      return auths.get(authToken);
    } catch (Exception e) {
      throw new DataAccessException("Auth not found");
    }
  }

  @Override
  public void deleteAuth(String authToken) throws DataAccessException {
    try {
      auths.remove(authToken);
    } catch (Exception e) {
      throw new DataAccessException("Auth not deleted");
    }
  }

  @Override
  public void deleteAllAuths() throws DataAccessException {
    auths.clear();
  }
}
