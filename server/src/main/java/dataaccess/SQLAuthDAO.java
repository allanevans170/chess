package dataaccess;

import com.google.gson.Gson;
import model.AuthData;
import java.util.Collection;

import java.sql.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLAuthDAO implements AuthDAO {

  public SQLAuthDAO() throws DataAccessException {
    configureDatabase();
  }

  @Override
  public AuthData createAuth(String username) throws DataAccessException {
    var statement = "INSERT INTO auth (username, authToken) VALUES (?, ?)";
    var json = new Gson().toJson(new AuthData(username));
    var authToken = executeUpdate()
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

  private final String[] createStatements = {
          """
            CREATE TABLE IF NOT EXISTS  auth (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`authToken`),
              
              INDEX(type),
              INDEX(name)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
  };        // probably needs some work...
  private void configureDatabase() throws DataAccessException {
    DatabaseManager.createDatabase();
    try (var conn = DatabaseManager.getConnection()) {
      for (var statement : createStatements) {
        try (var preparedStatement = conn.prepareStatement(statement)) {
          preparedStatement.executeUpdate();
        }
      }
    } catch (SQLException ex) {
      throw new DataAccessException(500, String.format("Unable to configure database: %s", ex.getMessage()));
    }
  }
}
