package dataaccess;

import com.google.gson.Gson;
import model.AuthData;
import java.util.Collection;
import java.util.ArrayList;

import java.sql.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLAuthDAO extends SQLAccess implements AuthDAO {

  public SQLAuthDAO() throws DataAccessException {
    configureDatabase();
  }

  @Override
  public AuthData createAuth(String username) throws DataAccessException {
    AuthData auth = new AuthData(username);                                     // creation of AuthData object
    String statement = "INSERT INTO auths (authToken, username) VALUES (?, ?)";     // preparing SQL statement
    executeUpdate(statement, auth.authToken(), auth.username());
    return auth;
  }

  @Override
  public Collection<AuthData> listAuths() throws DataAccessException {
    Collection<AuthData> result = new ArrayList<AuthData>();
    try (var conn = DatabaseManager.getConnection()) {
      String statement = "SELECT authToken, username FROM auth";
      try (PreparedStatement ps = conn.prepareStatement(statement)) {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
          result.add(new AuthData(rs.getString("username")));
        }
      }
    } catch (Exception e) {
      throw new DataAccessException("Unable to list auths: " + e.getMessage());
    }
    return result;
  }

  @Override
  public AuthData getAuth(String authToken) throws DataAccessException {
    return null;
  }

  private AuthData readAuth(ResultSet rs) throws SQLException {
    String authToken = rs.getString("authToken");
    String json = rs.getString("username");
    AuthData auth = new Gson().fromJson(username, authToken);
    return pet.setId(id);
  }

  @Override
  public void deleteAuth(String authToken) throws DataAccessException {

  }

  @Override
  public void deleteAllAuths() throws DataAccessException {

  }


}
