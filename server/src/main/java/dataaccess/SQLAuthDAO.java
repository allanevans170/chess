package dataaccess;

import com.google.gson.Gson;
import model.AuthData;
import java.util.Collection;

import java.sql.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLAuthDAO extends SQLAccess implements AuthDAO {

  public SQLAuthDAO() throws DataAccessException {
    configureDatabase();
  }

  @Override
  public AuthData createAuth(String username) throws DataAccessException {
    var statement = "INSERT INTO auth (username, authToken) VALUES (?, ?)";
    var json = new Gson().toJson(new AuthData(username));
    var authToken = executeUpdate(statement, username, json);
    return new AuthData(username);
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
