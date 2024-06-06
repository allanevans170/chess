package dataaccess;

import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ChessService;

import static org.junit.jupiter.api.Assertions.*;

public class DataAccessTests {
  private SQLAuthDAO sqlAuthDAO;
  private SQLGameDAO sqlGameDAO;
  private SQLUserDAO sqlUserDAO;

  @BeforeEach
  public void setUp() {
    try {
      sqlAuthDAO=new SQLAuthDAO();
      sqlGameDAO=new SQLGameDAO();
      sqlUserDAO=new SQLUserDAO();
    } catch (Exception e) {
      System.out.println("Error: "+e.getMessage());
    }}

  @Test
  public void positiveClear() {
    try {
      sqlUserDAO.createUser("goku", "kamehameha", "supersaiyan@gmail.com");
      sqlAuthDAO.createAuth("goku");
      sqlGameDAO.createGame("gokuVsVegeta");

      sqlAuthDAO.deleteAllAuths();
      sqlUserDAO.deleteAllUsers();
      sqlAuthDAO.deleteAllAuths();

      assertTrue(sqlUserDAO.listUsers().isEmpty(), "Users table should be empty after clear operation");
      assertTrue(sqlGameDAO.listGames().isEmpty(), "Games table should be empty after clear operation");
      assertTrue(sqlAuthDAO.listAuths().isEmpty(), "Auths table should be empty after clear operation");
    } catch (Exception e) {
      fail("Exception thrown during positveClear test: " + e.getMessage());
    }
  }

}
