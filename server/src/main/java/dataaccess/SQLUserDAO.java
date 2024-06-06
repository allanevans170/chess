package dataaccess;

import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class SQLUserDAO extends SQLAccess implements UserDAO {
  public SQLUserDAO() throws DataAccessException {
  }

  @Override
  public void createUser(String username, String password, String email) throws DataAccessException {
    UserData user = new UserData(username, password, email);
    String statement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
    executeUpdate(statement, user.username(), user.password(), user.email());
  }

  @Override
  public Collection<UserData> listUsers() throws DataAccessException {
    Collection<UserData> result = new ArrayList<>();
    String statement = "SELECT username, password, email FROM users";
    try (var conn = DatabaseManager.getConnection();
         var ps = conn.prepareStatement(statement)) {
      var rs = ps.executeQuery();
      while (rs.next()) {
        result.add(readUser(rs));
      }
    } catch (Exception e) {
      throw new DataAccessException("Unable to list users: " + e.getMessage());
    }
    return result;
  }

  @Override
  public UserData getUser(String username) throws DataAccessException {
    String statement = "SELECT username, password, email FROM users WHERE username = ?";

    try (var conn = DatabaseManager.getConnection();
         var ps = conn.prepareStatement(statement)) {
      ps.setString(1, username);
      var rs = ps.executeQuery();
      if (rs.next()) {
        return readUser(rs);
      }
    } catch (SQLException e) {
      throw new DataAccessException("Unable to get user: " + e.getMessage());
    }
    return null; // if no user is found
  }

  private UserData readUser(ResultSet rs) throws SQLException {
    String username = rs.getString("username");
    String password = rs.getString("password");
    String email = rs.getString("email");

    return new UserData(username, password, email);
  }

  @Override
  public void deleteAllUsers() throws DataAccessException {
    String statement = "DELETE FROM users";
    try {
      executeUpdate(statement);
    } catch (DataAccessException e) {
      throw new DataAccessException("Unable to delete users: " + e.getMessage());
    }
  }
}
