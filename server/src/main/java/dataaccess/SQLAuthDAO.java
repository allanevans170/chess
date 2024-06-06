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
  }

  @Override
  public AuthData createAuth(String username) throws DataAccessException {
    AuthData auth = new AuthData(username);                                     // creation of AuthData object
    String statement = "INSERT INTO auths (authToken, username) VALUES (?, ?)";     // preparing SQL statement
    executeUpdate(statement, auth.authToken(), auth.username());
    return auth;
  }

  @Override
  public AuthData getAuth(String authToken) throws DataAccessException {
    String statement = "SELECT authToken, username FROM auths WHERE authToken = ?";

    try (var conn = DatabaseManager.getConnection();
         var ps = conn.prepareStatement(statement)) {
      ps.setString(1, authToken);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return readAuth(rs);
      }
    } catch (SQLException e) {
      throw new DataAccessException("Unable to get auth: " + e.getMessage());
    }
    return null; // if no auth is found
  }
  @Override
  public Collection<AuthData> listAuths() throws DataAccessException {
    Collection<AuthData> result = new ArrayList<>();
    String statement = "SELECT authToken, username FROM auth";
    try (var conn = DatabaseManager.getConnection();
         PreparedStatement ps = conn.prepareStatement(statement)) {
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        result.add(readAuth(rs));
      }
    } catch (Exception e) {
      throw new DataAccessException("Unable to list auths: " + e.getMessage());
    }
    return result;
  }

  private AuthData readAuth(ResultSet rs) throws SQLException {
    String authToken = rs.getString("authToken");
    String username = rs.getString("username");

    return new AuthData(authToken, username);
  }

  @Override
  public void deleteAuth(String authToken) throws DataAccessException {

  }

  @Override
  public void deleteAllAuths() throws DataAccessException {

  }


}
