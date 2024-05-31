package dataaccess;

import model.AuthData;

//import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {

  final private HashMap<String, AuthData> auths = new HashMap<>();
  @Override
  public void createAuth(String username) throws DataAccessException {
//    try {
//      AuthData auth = new AuthData(username);
//      auths.put(auth.username(), auth);
//    } catch (Exception e) {
//      throw new DataAccessException("Error creating auth");
//    }
    AuthData auth = new AuthData(username);
    auths.put(auth.username(), auth);
  }


  @Override
  public Collection<AuthData> listAuths() throws DataAccessException {
    return auths.values();
  }

  @Override
  public AuthData getAuth(String username) throws DataAccessException { // authToken?? or username??
    try {
      return auths.get(username);
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
