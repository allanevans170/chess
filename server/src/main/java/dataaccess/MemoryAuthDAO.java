package dataaccess;

import model.AuthData;

//import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {

  final private HashMap<String, AuthData> auths = new HashMap<>();   // advantage to hashmap?????
  @Override
  public void createAuth(String username) throws DataAccessException {
    AuthData auth = new AuthData(username);
    auths.put(auth.authToken(), auth);
  }

  @Override
  public Collection<AuthData> listAuths() throws DataAccessException {
    return auths.values();
  }

  @Override
  public AuthData getAuth(String authToken) throws DataAccessException {
    return auths.get(authToken);
  }

  @Override
  public void deleteAuth(String authToken) throws DataAccessException {
    auths.remove(authToken);
  }

  @Override
  public void deleteAllAuths() throws DataAccessException {
    auths.clear();
  }
}
