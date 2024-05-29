package dataaccess;

import model.AuthData;
import java.util.Collection;
public interface AuthDAO {
  void createAuth(String username) throws DataAccessException;

  Collection<AuthData> listAuths();

  AuthData getAuth(String authToken) throws DataAccessException;

  void deleteAuth(String authToken) throws DataAccessException;

  void deleteAllAuths();
}
