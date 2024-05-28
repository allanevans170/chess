package dataaccess;

public interface AuthDAO {
  void createAuth(String username) throws DataAccessException;

  Collection<AuthData> listAuths() throws DataAccessException;

  AuthData getAuth(String username) throws DataAccessException;

  void deleteAllAuths() throws DataAccessException;
}
